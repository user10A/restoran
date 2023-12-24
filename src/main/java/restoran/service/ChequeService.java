package restoran.service;

import restoran.dto.category.CategoryRequest;
import restoran.dto.category.CategoryResponse;
import restoran.dto.cheque.ChequeRequest;
import restoran.dto.cheque.ChequeResponse;
import restoran.dto.user.SimpleResponse;

import java.util.List;

public interface ChequeService {
    ChequeResponse add(ChequeRequest request);
    SimpleResponse delete (long id);
    ChequeResponse update (Long id, ChequeRequest request);
    ChequeResponse getById (Long id);
    List<ChequeResponse> getAll ();
}
