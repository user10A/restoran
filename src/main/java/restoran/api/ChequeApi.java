package restoran.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.cheque.ChequeRequest;
import restoran.dto.cheque.ChequeResponse;
import restoran.dto.user.SimpleResponse;
import restoran.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/cheques")
@RequiredArgsConstructor
public class ChequeApi {
    private final ChequeService chequeService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ChequeResponse addCheque(@RequestBody ChequeRequest request) {
        return chequeService.add(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ResponseEntity<SimpleResponse> deleteCheque(@PathVariable Long id) {
        SimpleResponse response = chequeService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ResponseEntity<ChequeResponse> updateCheque(@PathVariable Long id, @RequestBody ChequeRequest request) {
        ChequeResponse response = chequeService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ResponseEntity<ChequeResponse> getChequeById(@PathVariable Long id) {
        ChequeResponse response = chequeService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ResponseEntity<List<ChequeResponse>> getAllCheques() {
        List<ChequeResponse> response = chequeService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

