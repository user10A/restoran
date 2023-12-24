package restoran.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Category;
import restoran.exceptions.AlreadyExistsException;
import restoran.exceptions.NotFoundException;
import restoran.repo.CategoryRepo;
import restoran.service.CategoryService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public SimpleResponse add(CategoryRequest request) {
        if (categoryRepo.existsByName(request.name())) {
            throw new AlreadyExistsException(
                    "Restaurant with email: " + request.name() + " already exists!"
            );}
        Category category = Category.builder()
                .name(request.name())
                .build();
        categoryRepo.save(category);
        return new SimpleResponse("Category successfully added", HttpStatus.OK);
    }

    @Override
    public SimpleResponse delete(long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            categoryRepo.delete(category);
            return new SimpleResponse("Category with ID " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("Category with ID: " + id + " not found");
        }
    }


    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(request.name());

            categoryRepo.save(category);

            return new CategoryResponse(
                    category.getId(),
                    category.getName()
            );
        } else {
            throw new NotFoundException("Category with ID: " + id + " not found");
        }
    }


    @Override
    public CategoryResponse getById(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            return new CategoryResponse(
                    category.getId(),
                    category.getName()
            );
        } else {
            throw new NotFoundException("Category with ID: " + id + " not found");
        }
    }


    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepo.getALL();
    }
}
