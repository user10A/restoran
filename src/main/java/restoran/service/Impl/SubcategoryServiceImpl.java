package restoran.service.Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restoran.dto.subCategory.SubCategoryRequest;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Category;
import restoran.entity.Subcategory;
import restoran.exceptions.AlreadyExistsException;
import restoran.exceptions.NotFoundException;
import restoran.repo.CategoryRepo;
import restoran.repo.SubcategoryRepo;
import restoran.service.CategoryService;
import restoran.service.SubcategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;
    private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;
    @Override
    public SimpleResponse add(SubCategoryRequest request) {
        if (subcategoryRepo.existsByName(request.getName())) {
            throw new AlreadyExistsException(
                    "Subcategory with name: " + request.getName() + " already exists in category: ");
        }
        Category category = categoryRepo.getByName(request.getCategoryName()).orElseThrow(()->new NotFoundException("Category with by Name:"+request.getCategoryName()+"not found"));
        // Создаем и сохраняем подкатегорию
        Subcategory subcategory = Subcategory.builder()
                .name(request.getName())
                .sCategory(category)
                .build();
        subcategoryRepo.save(subcategory);

        return new SimpleResponse("Subcategory successfully added", HttpStatus.OK);
    }


    @Override
    public SimpleResponse delete(long id) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepo.findById(id);

        if (optionalSubcategory.isPresent()) {
            Subcategory subcategory = optionalSubcategory.get();
            subcategoryRepo.delete(subcategory);
            return new SimpleResponse("Subcategory with ID " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("Subcategory with ID: " + id + " not found");
        }
    }


    @Override
    public SubCategoryResponse update(Long id, SubCategoryRequest request) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepo.findById(id);

        if (optionalSubcategory.isPresent()) {
            Subcategory subcategory = optionalSubcategory.get();
            subcategory.setName(request.getName());
            // Возможно, вам нужно обновить связанные данные, например, категорию, в этом методе
            subcategoryRepo.save(subcategory);

            return new SubCategoryResponse(
                    subcategory.getId(),
                    subcategory.getName(),
                    subcategory.getSCategory().getName()
            );
        } else {
            throw new NotFoundException("Subcategory with ID: " + id + " not found");
        }
    }


    @Override
    public SubCategoryResponse getById(Long id) {
        Optional<Subcategory> optionalSubcategory = subcategoryRepo.findById(id);

        if (optionalSubcategory.isPresent()) {
            Subcategory subcategory = optionalSubcategory.get();
            return new SubCategoryResponse(
                    subcategory.getId(),
                    subcategory.getName(),
                    subcategory.getSCategory().getName()
            );
        } else {
            throw new NotFoundException("Subcategory with ID: " + id + " not found");
        }
    }


    @Override
    public List<SubCategoryResponse> getAll() {
        return subcategoryRepo.getAll();
    }
}
