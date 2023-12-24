package restoran.service;

import restoran.dto.cheque.ChequeRequest;
import restoran.dto.cheque.ChequeResponse;
import restoran.dto.subCategory.SubCategoryRequest;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<Subcategory> getAllS(String categoryName);
    List<Subcategory> getBySearch(String search);
    SimpleResponse add(SubCategoryRequest request);
    SimpleResponse delete (long id);
    SubCategoryResponse update (Long id, SubCategoryRequest request);
    SubCategoryResponse getById (Long id);
    List<SubCategoryResponse> getAll ();
}
