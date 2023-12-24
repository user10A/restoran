package restoran.service;

import restoran.dto.restaurant.RestaurantRequest;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse add(RestaurantRequest request);
    SimpleResponse delete (Long id);
    RestaurantResponse update (UpdateRestaurantRequest request);
    RestaurantResponse getById (Long id);
    List<RestaurantResponse> getAll ();


}
