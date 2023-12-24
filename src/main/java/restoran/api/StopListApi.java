package restoran.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.stopList.StopListRequest;
import restoran.dto.stopList.StopListResponse;
import restoran.dto.user.SimpleResponse;
import restoran.service.StopListService;
import java.util.List;

@RestController
@RequestMapping("/stopList")
@RequiredArgsConstructor
public class StopListApi {
    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/add")
    public ResponseEntity<SimpleResponse> addStopList(@RequestBody StopListRequest request) {
        return ResponseEntity.ok(stopListService.add(request));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SimpleResponse> deleteStopList(@PathVariable Long id) {
        return ResponseEntity.ok(stopListService.delete(id));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/update/{id}")
    public ResponseEntity<StopListResponse> updateStopList(
            @PathVariable Long id,
            @RequestBody StopListRequest request
    ) {
        return ResponseEntity.ok(stopListService.update(id, request));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @GetMapping("/{id}")
    public ResponseEntity<StopListResponse> getStopListById(@PathVariable Long id) {
        return ResponseEntity.ok(stopListService.getById(id));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @GetMapping("/all")
    public ResponseEntity<List<StopListResponse>> getAllStopLists() {
        return ResponseEntity.ok(stopListService.getAll());
    }
}

