package restoran.api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.subCategory.SubCategoryRequest;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Subcategory;
import restoran.service.SubcategoryService;

import java.util.List;

@RestController
@RequestMapping("/subcategory")
@RequiredArgsConstructor
public class SubcategoryApi {
    private final SubcategoryService subcategoryService;
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PostMapping("/add")
    public ResponseEntity<SimpleResponse> addSubcategory(@RequestBody SubCategoryRequest request) {
        return ResponseEntity.ok(subcategoryService.add(request));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SimpleResponse> deleteSubcategory(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoryService.delete(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<SubCategoryResponse> updateSubcategory(
            @PathVariable Long id,
            @RequestBody SubCategoryRequest request
    ) {
        return ResponseEntity.ok(subcategoryService.update(id, request));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> getSubcategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(subcategoryService.getById(id));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/all")
    public List<SubCategoryResponse> getAllSubcategories() {
        return subcategoryService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Subcategory>> getAllByGrouping(@RequestParam String categoryName) {
        List<Subcategory> subCategories = subcategoryService.getAllS(categoryName);
        return ResponseEntity.ok(subCategories);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/search")
    public ResponseEntity<List<Subcategory>> getAllBySearch(@RequestParam String search){
        List<Subcategory> subCategories = subcategoryService.getBySearch(search);
        return ResponseEntity.ok(subCategories);
    }
}

