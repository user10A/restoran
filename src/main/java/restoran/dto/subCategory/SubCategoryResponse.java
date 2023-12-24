package restoran.dto.subCategory;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
import restoran.entity.Category;
import restoran.entity.Menuitem;

import java.util.List;
@Builder
@Data
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String nameCategory;

    public SubCategoryResponse(Long id, String name, String nameCategory) {
        this.id = id;
        this.name = name;
        this.nameCategory = nameCategory;
    }
}
