package restoran.service.Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restoran.dto.restaurant.RestaurantRequest;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.restaurant.UpdateRestaurantRequest;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Restaurant;
import restoran.exceptions.AlreadyExistsException;
import restoran.exceptions.NotFoundException;
import restoran.repo.RestaurantRepo;
import restoran.service.RestaurantService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepo restaurantRepo;
    @Override
    public SimpleResponse add(RestaurantRequest request) {
        if (restaurantRepo.existsByName(request.name())){
            throw new AlreadyExistsException("Restaurant with name: " + request.name()+" already exists!");
        }
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .restType(request.restType())
                .service(request.service())
                .build();
        restaurantRepo.save(restaurant);
        return new SimpleResponse("Restaurant successfully added", HttpStatus.OK);
    }

    @Override
    public SimpleResponse delete(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurantRepo.delete(restaurant);
            return new SimpleResponse("Restaurant " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("Restaurant with name: " + id + " not found");
        }
    }


    @Override
    public RestaurantResponse update(UpdateRestaurantRequest request) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.getByName(request.oldName());

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setName(request.name());
            restaurant.setLocation(request.location());
            restaurant.setRestType(request.restType());
            restaurant.setService(request.service());

            restaurantRepo.save(restaurant);

            return new RestaurantResponse(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getLocation(),
                    restaurant.getRestType(),
                    restaurant.getNumberOfEmployees(),
                    restaurant.getService()
            );
        } else {
            throw new NotFoundException("Restaurant with Name: " + request.oldName() + " not found");
        }
    }


    @Override
    public RestaurantResponse getById(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            return new RestaurantResponse(
                    restaurant.getId(),
                    restaurant.getName(),
                    restaurant.getLocation(),
                    restaurant.getRestType(),
                    restaurant.getNumberOfEmployees(),
                    restaurant.getService()
            );
        } else {
            throw new NotFoundException("Restaurant with ID: " + id + " not found");
        }
    }


    @Override
    public List<RestaurantResponse> getAll() {
        return restaurantRepo.getAll();
    }
}
