package restoran.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.menutems.AssignToReason;
import restoran.dto.menutems.MenuitemRequest;
import restoran.dto.menutems.MenuitemResponse;
import restoran.dto.menutems.PaginationResponse;
import restoran.dto.user.SimpleResponse;
import restoran.entity.Menuitem;
import restoran.service.MenuitemService;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuitemApi {
    private final MenuitemService menuitemService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<SimpleResponse> addMenuitem(@RequestBody MenuitemRequest request) {
        SimpleResponse response = menuitemService.add(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public ResponseEntity<SimpleResponse> deleteMenuitem(@PathVariable Long id) {
        SimpleResponse response = menuitemService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    public MenuitemResponse updateMenuitem(@PathVariable Long id, @RequestBody MenuitemRequest request) {
       return  menuitemService.update(id, request);
    }
    @PutMapping("/assignReason")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF','WAITER')")
    public SimpleResponse simpleResponse(@RequestBody AssignToReason request) {
       return  menuitemService.assignToReason(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER','CHEF')")
    public ResponseEntity<MenuitemResponse> getMenuitemById(@PathVariable Long id) {
        MenuitemResponse response = menuitemService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    public ResponseEntity<List<MenuitemResponse>> getAllMenuitems() {
        List<MenuitemResponse> response = menuitemService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/pagination")
    public PaginationResponse pagination(@RequestParam int page, @RequestParam int size){
        return menuitemService.paginationGetAll(page, size);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping
    public ResponseEntity<List<Menuitem>> getAll(@RequestParam String search){
        List<Menuitem> menuItems=menuitemService.getAllS(search);
        return ResponseEntity.ok(menuItems);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/sort")
    public ResponseEntity<List<Menuitem>> sortByPrice(@RequestParam String ascOrDesc){
        List<Menuitem> menuItems=menuitemService.sortByPrice(ascOrDesc);
        return ResponseEntity.ok(menuItems);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/filter")
    public ResponseEntity<List<Menuitem>> filter(@RequestParam Boolean filter){
        List<Menuitem> menuItems=menuitemService.filterByIsVegetarian(filter);
        return ResponseEntity.ok(menuItems);
    }
}

