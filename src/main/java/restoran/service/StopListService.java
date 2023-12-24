package restoran.service;

import restoran.dto.cheque.ChequeRequest;
import restoran.dto.cheque.ChequeResponse;
import restoran.dto.stopList.StopListRequest;
import restoran.dto.stopList.StopListResponse;
import restoran.dto.user.SimpleResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse add(StopListRequest request);
    SimpleResponse delete (long id);
    StopListResponse update (Long id, StopListRequest request);
    StopListResponse getById (Long id);
    List<StopListResponse> getAll ();
}
