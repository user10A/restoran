package restoran.repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restoran.dto.menutems.MenuitemResponse;
import restoran.entity.Menuitem;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuitemRepo extends JpaRepository<Menuitem,Long> {
    @Query("select m from Menuitem m where m.name =:name")
    Optional<Menuitem> getByName(String name);
    @Query("select new restoran.dto.menutems.MenuitemResponse(u.id, u.name,u.image,u.price, u.description , u.isVegetarian) from Menuitem u")
    List<MenuitemResponse> getAll();
    @Query("SELECT m FROM Menuitem m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Menuitem> getAllSearch(String search);
    @Query("select m from Menuitem m order by m.price asc")
    List<Menuitem> sortByPriceAsc();

    @Query("select m from Menuitem m order by m.price desc ")
    List<Menuitem> sortByPriceDesc();
    @Query("select m from Menuitem m where m.isVegetarian = :isVegetarian")
    List<Menuitem> filter(@Param("isVegetarian") boolean isVegetarian);
    @Query("select m from Menuitem m")
    List<Menuitem> getAllMenus();
    boolean existsByName(String name);
    @Query("select m from Menuitem m")
    Page<Menuitem> getMenus(Pageable pageable);
}
