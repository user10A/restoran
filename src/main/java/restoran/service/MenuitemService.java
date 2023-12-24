package restoran.service;

import restoran.dto.menutems.AssignToReason;
import restoran.dto.menutems.MenuitemRequest;
import restoran.dto.menutems.MenuitemResponse;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;

import java.util.List;

public interface MenuitemService {
    SimpleResponse add(MenuitemRequest request);
    SimpleResponse delete (Long id);
    MenuitemResponse update (Long id, MenuitemRequest request);
    SimpleResponse assignToReason(AssignToReason request);
    MenuitemResponse getById (Long id);
    List<MenuitemResponse> getAll ();

}
