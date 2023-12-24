package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.stopList.StopListResponse;
import restoran.entity.Restaurant;
import restoran.entity.StopList;
import restoran.entity.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopListRepo extends JpaRepository<StopList,Long> {

    @Query("select new restoran.dto.stopList.StopListResponse(s.id,s.reason,s.date) from StopList s")
    List<StopListResponse> getAll();
    @Query("select u from StopList u where u.reason =:reason")
    StopList getByName(String reason);
}
