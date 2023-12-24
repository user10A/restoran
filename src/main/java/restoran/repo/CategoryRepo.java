package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.category.CategoryResponse;
import restoran.entity.Category;
import restoran.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    @Query("select u from Category u where u.name =:name")
    Optional<Category> getByName(String name);
    @Query("select new restoran.dto.category.CategoryResponse(u.id, u.name) from Category u")
    List<CategoryResponse> getALL();
    boolean existsByName(String name);

}
