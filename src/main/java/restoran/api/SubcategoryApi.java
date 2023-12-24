package restoran.api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restoran.dto.subCategory.SubCategoryRequest;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.service.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryApi {
    private final SubcategoryService subcategoryService;

    @PostMapping("/add")
    public ResponseEntity<SimpleResponse> addSubcategory(@RequestBody SubCategoryRequest request) {
        return ResponseEntity.ok(subcategoryService.add(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SimpleResponse> deleteSubcategory(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoryService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubCategoryResponse> updateSubcategory(
            @PathVariable Long id,
            @RequestBody SubCategoryRequest request
    ) {
        return ResponseEntity.ok(subcategoryService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> getSubcategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoryService.getById(id));
    }

    @GetMapping("/all")
    public List<SubCategoryResponse> getAllSubcategories() {
        return subcategoryService.getAll();
    }
}

