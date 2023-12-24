package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.menutems.MenuitemResponse;
import restoran.entity.Menuitem;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuitemRepo extends JpaRepository<Menuitem,Long> {
    @Query("select u from Menuitem u where u.name =:name")
    Optional<Menuitem> getByName(String name);
    @Query("select new restoran.dto.menutems.MenuitemResponse(u.id, u.name,u.image,u.price, u.description , u.isVegetarian) from Menuitem u")
    List<MenuitemResponse> getAll();
    @Query("select u from Menuitem u")
    List<Menuitem> getAllMenus();
    boolean existsByName(String name);
}
