package restoran.dto.subCategory;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restoran.dto.category.CategoryRequest;
import restoran.entity.Category;
import restoran.entity.Menuitem;

import java.util.List;
@Builder
@Getter
@Setter
public class SubCategoryRequest {
         String name;
         String CategoryName;
}
