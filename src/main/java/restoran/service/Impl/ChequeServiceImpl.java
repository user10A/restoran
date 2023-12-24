package restoran.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
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
import java.util.ArrayList;
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
    }  public double getGrantTotal(List<MenuitemResponse> menus,int service) {
        double totalSum = menus.stream()
                .mapToDouble(MenuitemResponse::getPrice) // Получаем поток цен меню
                .sum(); // Суммируем цены

        if (menus.size() > 0) {
            return totalSum * service; // Вычисляем сумму чека
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
//        Cheque cheque =chequeRepo.findById(id).orElseThrow(()-> new NotFoundException(""));
//        cheque.setPriceAverage(request.getPriceAverage());
//        cheque.setCreatedAt(ZonedDateTime.now());
//        chequeRepo.save(cheque);
//        return new ChequeResponse(
//                cheque.getId(),
//                cheque.getPriceAverage(),
//                cheque.getCreatedAt()
//        );
        return null;
    }

    @Override
    public ChequeResponse getById(Long id) {
        Optional<Cheque> optionalCheque = chequeRepo.findById(id);

        if (optionalCheque.isPresent()) {
            Cheque cheque = optionalCheque.get();
//            return new ChequeResponse(cheque.getId(), cheque.getPriceAverage(), cheque.getCreatedAt());
            return null;
        } else {
            throw new NotFoundException("Cheque with ID: " + id + " not found");
        }
    }

    @Override
    public List<ChequeResponse> getAll() {
//        List<Cheque> cheques = chequeRepo.findAll();
        return null;
    }

}
