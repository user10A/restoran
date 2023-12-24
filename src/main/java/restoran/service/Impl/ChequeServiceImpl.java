package restoran.service.Impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import restoran.dto.cheque.ChequeRequest;
import restoran.dto.cheque.ChequeResponse;
import restoran.dto.menutems.MenuitemResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Cheque;
import restoran.entity.Menuitem;
import restoran.entity.Restaurant;
import restoran.entity.User;
import restoran.exceptions.NotFoundException;
import restoran.repo.ChequeRepo;
import restoran.repo.MenuitemRepo;
import restoran.repo.UserRepo;
import restoran.service.ChequeService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepo chequeRepo;
    private final MenuitemRepo menuitemRepo;
    private final UserRepo userRepo;

    @Override
    public ChequeResponse add(ChequeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            Long userId = ((User) authentication.getPrincipal()).getId();

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("user find by id {}", userId);

            Restaurant restaurant = user.getURestaurant();

            String userFullName = (user.getFirstName()+" "+user.getLastName());

            // Получите имена меню из запроса
            List<String> namesMenus = request.getNamesMenus();

            // Фильтруйте элементы из menuitemRepo.getAll() по именам меню
            List<MenuitemResponse> menus = menuitemRepo.getAll().stream()
                    .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                    .collect(Collectors.toList());
            List<Menuitem> menuItem = menuitemRepo.getAllMenus().stream()
                    .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                    .collect(Collectors.toList());

            double priceAverage = getPriceAverage(menus);
            double grantTotal=getGrantTotal(menus,restaurant.getService());
            Cheque cheque = Cheque.builder()
                    .priceAverage(priceAverage)
                    .grandTotal(grantTotal)
                    .service(restaurant.getService())
                    .cMenus(menuItem)
                    .cUser(user)
                    .createdAt(ZonedDateTime.now())
                    .build();
            chequeRepo.save(cheque);

            return new ChequeResponse(
                    cheque.getId(),
                    userFullName,
                    menus,
                    cheque.getPriceAverage(),
                    cheque.getCreatedAt(),
                    restaurant.getService(),
                    grantTotal
            );
        }else {
            return null;
        }
    }


    public double getPriceAverage(List<MenuitemResponse> menus) {
        double totalSum = menus.stream()
                .mapToDouble(MenuitemResponse::getPrice) // Получаем поток цен меню
                .sum(); // Суммируем цены

        if (menus.size() > 0) {
            return totalSum / menus.size(); // Вычисляем среднее значение, если список меню не пуст
        } else {
            return 0.0; // Возвращаем 0, если список меню пуст
        }
    }
    public double getGrantTotal(List<MenuitemResponse> menus, int service) {
        double totalSum = menus.stream()
                .mapToDouble(MenuitemResponse::getPrice) // Получаем поток цен меню
                .sum(); // Суммируем цены

        if (menus.size() > 0) {
            return totalSum + (totalSum * ((double) service / 100)); // Вычисляем сумму чека с учетом обслуживания
        } else {
            return 0.0; // Возвращаем 0, если список меню пуст
        }
    }



    @Override
    public SimpleResponse delete(long id) {
        Optional<Cheque> optionalCheque = chequeRepo.findById(id);

        if (optionalCheque.isPresent()) {
            Cheque cheque = optionalCheque.get();
            chequeRepo.delete(cheque);
            return new SimpleResponse("Cheque with ID " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("Cheque with ID: " + id + " not found");
        }
    }


    @Override
    public ChequeResponse update(Long id, ChequeRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            Long userId = ((User) authentication.getPrincipal()).getId();

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("user find by id {}", userId);

            Restaurant restaurant = user.getURestaurant();

            String userFullName = (user.getFirstName()+" "+user.getLastName());

            // Получите имена меню из запроса
            List<String> namesMenus = request.getNamesMenus();

            // Фильтруйте элементы из menuitemRepo.getAll() по именам меню
            List<MenuitemResponse> menus = menuitemRepo.getAll().stream()
                    .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                    .collect(Collectors.toList());
            List<Menuitem> menuItem = menuitemRepo.getAllMenus().stream()
                    .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                    .collect(Collectors.toList());

            double priceAverage = getPriceAverage(menus);
            double grantTotal=getGrantTotal(menus,restaurant.getService());
            Cheque cheque =chequeRepo.findById(id).orElseThrow(()-> new NotFoundException(""));
            cheque.setPriceAverage(priceAverage);
            cheque.setGrandTotal(grantTotal);
            cheque.setService(restaurant.getService());
            cheque.setCMenus(menuItem);
            cheque.setCUser(user);
            cheque.setCreatedAt(ZonedDateTime.now());
            chequeRepo.save(cheque);

            return new ChequeResponse(
                    cheque.getId(),
                    userFullName,
                    menus,
                    cheque.getPriceAverage(),
                    cheque.getCreatedAt(),
                    cheque.getService(),
                    cheque.getGrandTotal()
            );
        }else {
            return null;
        }
    }

    @Override
    public ChequeResponse getById(Long id) {
        Optional<Cheque> optionalCheque = chequeRepo.findById(id);
        if (optionalCheque.isPresent()) {
            Cheque cheque = optionalCheque.get();
            String fullName = cheque.getCUser().getFirstName() + " " + cheque.getCUser().getLastName();

            // Получаем имена меню из объекта Cheque
            List<String> namesMenus = cheque.getCMenus().stream()
                    .map(Menuitem::getName)
                    .collect(Collectors.toList());

            // Получаем объекты MenuitemResponse по именам меню
            List<MenuitemResponse> menus = menuitemRepo.getAll().stream()
                    .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                    .collect(Collectors.toList());

            return new ChequeResponse(
                    cheque.getId(),
                    fullName,
                    menus,
                    cheque.getPriceAverage(),
                    cheque.getCreatedAt(),
                    cheque.getService(),
                    cheque.getGrandTotal()
            );
        } else {
            throw new NotFoundException("Cheque with ID: " + id + " not found");
        }
    }


    @Override
    public List<ChequeResponse> getAll() {
        List<Cheque> allCheques = chequeRepo.findAll();
        return allCheques.stream()
                .map(this::mapToChequeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cheque> getChequeByWaiter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            Long userId = ((User) authentication.getPrincipal()).getId();

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            log.info("user find by id {}", userId);
            return chequeRepo.getChequeByWaiterEmail(user.getEmail());
        }
        return null;
    }

    private ChequeResponse mapToChequeResponse(Cheque cheque) {
        String fullName = cheque.getCUser().getFirstName() + " " + cheque.getCUser().getLastName();
        List<String> namesMenus = cheque.getCMenus().stream()
                .map(Menuitem::getName)
                .collect(Collectors.toList());

        List<MenuitemResponse> menus = menuitemRepo.getAll().stream()
                .filter(menuitem -> namesMenus.contains(menuitem.getName()))
                .collect(Collectors.toList());

        return new ChequeResponse(
                cheque.getId(),
                fullName,
                menus,
                cheque.getPriceAverage(),
                cheque.getCreatedAt(),
                cheque.getService(),
                cheque.getGrandTotal()
        );
    }
}
