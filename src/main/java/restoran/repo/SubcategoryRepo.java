package restoran.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restoran.dto.stopList.StopListResponse;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.entity.Subcategory;
import restoran.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepo extends JpaRepository<Subcategory,Long> {
    @Query("select s from Subcategory s join s.sCategory c where LOWER(c.name) like LOWER(CONCAT('%', :categoryName, '%'))")
    List<Subcategory> getAllCategoryName(String categoryName);

    @Query("SELECT s FROM Subcategory s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Subcategory> getBySearch(String search);
    @Query("select new restoran.dto.subCategory.SubCategoryResponse(s.id,s.name,s.sCategory.name) from Subcategory s order by s.name asc")
    List<SubCategoryResponse> getAll();
    boolean existsByName(String name);
    @Query("select u from Subcategory u where u.name =:name")
    Subcategory getByName(String name);
}
