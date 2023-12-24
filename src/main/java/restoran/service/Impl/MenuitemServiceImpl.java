package restoran.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restoran.dto.menutems.AssignToReason;
import restoran.dto.menutems.MenuitemRequest;
import restoran.dto.menutems.MenuitemResponse;
import restoran.dto.menutems.PaginationResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Menuitem;
import restoran.entity.Restaurant;
import restoran.entity.StopList;
import restoran.entity.Subcategory;
import restoran.exceptions.AlreadyExistsException;
import restoran.exceptions.BadCredentialsException;
import restoran.exceptions.NotFoundException;
import restoran.repo.MenuitemRepo;
import restoran.repo.RestaurantRepo;
import restoran.repo.StopListRepo;
import restoran.repo.SubcategoryRepo;
import restoran.service.MenuitemService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuitemServiceImpl implements MenuitemService {
    private final MenuitemRepo menuitemRepo;
    private final RestaurantRepo restaurantRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final StopListRepo stopListRepo;
    @Override
    public SimpleResponse add(MenuitemRequest request) {
            if (menuitemRepo.existsByName(request.name())) {
                throw new AlreadyExistsException(
                        "Menuitem with email: " + request.name() + " already exists!"
                );}
                Restaurant restaurant = restaurantRepo.getByName(request.RestaurantName()).orElseThrow(()-> new NotFoundException("Restaurant with name: " + request.RestaurantName() + " not found"));
                Subcategory subcategory = subcategoryRepo.getByName(request.subcategoryName());
                Menuitem menuitem = Menuitem.builder()
                        .name(request.name())
                        .image(request.image())
                        .description(request.description())
                        .price(request.price())
                        .isVegetarian(request.isVegetarian())
                        .mRestaurant(restaurant)
                        .mSubcategory(subcategory)
                        .build();
                menuitemRepo.save(menuitem);

        return new SimpleResponse("Menuitem successfully added", HttpStatus.OK);
    }

    @Override
    public SimpleResponse delete(Long id) {
        Optional<Menuitem> optionalMenuitem = menuitemRepo.findById(id);

        if (optionalMenuitem.isPresent()) {
            Menuitem menuitem = optionalMenuitem.get();
            menuitemRepo.delete(menuitem);
            return new SimpleResponse("Menuitem with ID " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("Menuitem with ID: " + id + " not found");
        }
    }


    @Override
    public MenuitemResponse update(Long id, MenuitemRequest request) {
        Optional<Menuitem> optionalMenuitem = menuitemRepo.findById(id);

        if (optionalMenuitem.isPresent()) {
            Menuitem menuitem = optionalMenuitem.get();
            menuitem.setName(request.name());
            menuitem.setImage(request.image());
            menuitem.setDescription(request.description());
            menuitem.setPrice(request.price());
            menuitem.setVegetarian(request.isVegetarian());
            Restaurant restaurant =restaurantRepo.getByName(request.RestaurantName()).orElseThrow(()-> new NotFoundException("Restaurant with by name:"+request.RestaurantName()+"not found"));
            menuitem.setMRestaurant(restaurant);
            menuitem.setMSubcategory(subcategoryRepo.getByName(request.subcategoryName()));
            menuitemRepo.save(menuitem);

            return new MenuitemResponse(
                    menuitem.getId(),
                    menuitem.getName(),
                    menuitem.getImage(),
                    menuitem.getPrice(),
                    menuitem.getDescription(),
                    menuitem.isVegetarian()

            );

        } else {
            throw new NotFoundException("Menuitem with ID: " + id + " not found");
        }
    }

    @Override
    public SimpleResponse assignToReason(AssignToReason request) {
        Menuitem menuitem = menuitemRepo.findById(request.menuId()).orElseThrow(()-> new NotFoundException("Menu with id:"+request.menuId()+"not found"));
        StopList stopList= stopListRepo.getByName(request.reason());
        menuitem.setMStopList(stopList);
        menuitemRepo.save(menuitem);
        return new SimpleResponse("Stop List successfully added",HttpStatus.OK);
    }

    @Override
    public MenuitemResponse getById(Long id) {
        Optional<Menuitem> optionalMenuitem = menuitemRepo.findById(id);

        if (optionalMenuitem.isPresent()) {
            Menuitem menuitem = optionalMenuitem.get();
            return new MenuitemResponse(
                    menuitem.getId(),
                    menuitem.getName(),
                    menuitem.getImage(),
                    menuitem.getPrice(),
                    menuitem.getDescription(),
                    menuitem.isVegetarian());
        } else {
            throw new NotFoundException("Menuitem with ID: " + id + " not found");
        }
    }


    @Override
    public List<MenuitemResponse> getAll() {
        return menuitemRepo.getAll();
    }

    @Override
    public PaginationResponse paginationGetAll(int page, int size) {
        Pageable pageable= PageRequest.of(page-1, size);
        Page<Menuitem> menu=menuitemRepo.getMenus(pageable);
        return PaginationResponse.builder()
                .menuItems(menu.getContent())
                .page(menu.getNumber()+1)
                .size(menu.getSize())
                .build();
    }

    @Override
    public List<Menuitem> getAllS(String search) {
        return menuitemRepo.getAllSearch(search);
    }

    @Override
    public List<Menuitem> sortByPrice(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("asc")){
            return menuitemRepo.sortByPriceAsc();
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            return menuitemRepo.sortByPriceDesc();
        }
        throw new BadCredentialsException("Write correct input!");    }

    @Override
    public List<Menuitem> filterByIsVegetarian(Boolean filter) {
        return menuitemRepo.filter(filter);
    }
}
