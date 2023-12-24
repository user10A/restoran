package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.entity.Restaurant;
import restoran.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email =:email")
    Optional<User> getUserByEmail(String email);
    @Query("select u.uRestaurant from User u where u.Id =:id")
    Restaurant getByIdRestaurantByUserId(long id);
    boolean existsByEmail(String email);
}
