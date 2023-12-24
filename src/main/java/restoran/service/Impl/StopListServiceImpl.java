package restoran.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import restoran.dto.stopList.StopListRequest;
import restoran.dto.stopList.StopListResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Menuitem;
import restoran.entity.StopList;
import restoran.exceptions.NotFoundException;
import restoran.repo.MenuitemRepo;
import restoran.repo.StopListRepo;
import restoran.service.StopListService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepo stopListRepo;
    @Override
    public SimpleResponse add(StopListRequest request) {
        StopList stopList = StopList.builder()
                .reason(request.reason())
                .date(LocalDate.now())
                .build();
        stopListRepo.save(stopList);
        return new SimpleResponse("StopList successfully added", HttpStatus.OK);
    }

    @Override
    public SimpleResponse delete(long id) {
        Optional<StopList> optionalStopList = stopListRepo.findById(id);

        if (optionalStopList.isPresent()) {
            StopList stopList = optionalStopList.get();
            stopListRepo.delete(stopList);
            return new SimpleResponse("StopList with ID " + id + " successfully deleted", HttpStatus.OK);
        } else {
            throw new NotFoundException("StopList with ID: " + id + " not found");
        }
    }


    @Override
    public StopListResponse update(Long id, StopListRequest request) {
        Optional<StopList> optionalStopList = stopListRepo.findById(id);
        if (optionalStopList.isPresent()) {
            StopList stopList = StopList.builder()
                    .reason(request.reason())
                    .date(LocalDate.now())
                    .build();
            stopListRepo.save(stopList);
            return  new StopListResponse(
                    stopList.getId(),
                    stopList.getReason(),
                    stopList.getDate()
            );
        } else {
            throw new NotFoundException("StopList with ID: " + id + " not found");
        }
    }

    @Override
    public StopListResponse getById(Long id) {
        Optional<StopList> optionalStopList = stopListRepo.findById(id);

        if (optionalStopList.isPresent()) {
            StopList stopList = optionalStopList.get();
            return  new StopListResponse(
                    stopList.getId(),
                    stopList.getReason(),
                    stopList.getDate()
            );        } else {
            throw new NotFoundException("StopList with ID: " + id + " not found");
        }
    }


    @Override
    public List<StopListResponse> getAll() {
        return stopListRepo.getAll();
    }

}
