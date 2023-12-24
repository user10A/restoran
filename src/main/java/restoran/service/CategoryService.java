package restoran.service;

import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
import restoran.dto.restaurant.RestaurantRequest;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;

import java.util.List;

public interface CategoryService {
    SimpleResponse add(CategoryRequest request);
    SimpleResponse delete (long id);
    CategoryResponse update (Long id, CategoryRequest request);
    CategoryResponse getById (Long id);
    List<CategoryResponse> getAll ();
}
