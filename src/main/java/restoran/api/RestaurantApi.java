package restoran.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.restaurant.RestaurantRequest;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;
import restoran.service.Impl.RestaurantServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantApi {
    private final RestaurantServiceImpl restaurantService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public SimpleResponse addRestaurant(@RequestBody RestaurantRequest request) {
        return restaurantService.add(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteRestaurant(@PathVariable Long id) {
        return restaurantService.delete(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public RestaurantResponse updateRestaurant(@RequestBody UpdateRestaurantRequest request) {
        return restaurantService.update(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantService.getAll();
    }
}

