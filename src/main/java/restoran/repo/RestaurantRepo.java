package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.entity.Restaurant;
import restoran.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Long> {
    @Query("select u from Restaurant u where u.name =:name")
    Optional<Restaurant> getByName(String name);



    @Query("select new restoran.dto.restaurant.RestaurantResponse(r.id, r.name,r.location,r.restType,r.numberOfEmployees,r.service) from Restaurant r")
    List<RestaurantResponse>getAll();
    boolean existsByName(String name);
}
