package restoran.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.service.Impl.CategoryServiceImpl;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryApi {
    private final CategoryServiceImpl categoryService;

    @PostMapping("/add")
    public SimpleResponse addCategory(@RequestBody CategoryRequest request) {
        return categoryService.add(request);
    }

    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteCategory(@PathVariable Long id) {
        return categoryService.delete(id);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @GetMapping("/get/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/getAll")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAll();
    }
}

