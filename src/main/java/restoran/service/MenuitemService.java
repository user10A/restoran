package restoran.service;

import restoran.dto.menutems.AssignToReason;
import restoran.dto.menutems.MenuitemRequest;
import restoran.dto.menutems.MenuitemResponse;
import restoran.dto.menutems.PaginationResponse;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Menuitem;

import java.awt.*;
import java.util.List;

public interface MenuitemService {
    SimpleResponse add(MenuitemRequest request);
    SimpleResponse delete (Long id);
    MenuitemResponse update (Long id, MenuitemRequest request);
    SimpleResponse assignToReason(AssignToReason request);
    MenuitemResponse getById (Long id);
    List<MenuitemResponse> getAll();
    PaginationResponse paginationGetAll(int page, int size);
    List<Menuitem> getAllS(String search);

    List<Menuitem> sortByPrice(String ascOrDesc);

    List<Menuitem> filterByIsVegetarian(Boolean filter);


}
