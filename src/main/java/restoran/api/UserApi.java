package restoran.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restoran.dto.user.*;
import restoran.service.Impl.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApi {
    private final UserServiceImpl userService;

    @PermitAll
    @PostMapping("/signIn")
    public AuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return userService.singIn(request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/update")
    public SimpleResponse update(@RequestBody SignUpRequest request) {
        return userService.update(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public SimpleResponse delete(@RequestParam String email) {
        return userService.deleteUser(email);
    }
    @PermitAll
    @PostMapping("/register/waiter")
    public SimpleResponse registerWaiter(@RequestBody SignUpRequest request) {
        return userService.saveWaiter(request);
    }
    @PermitAll
    @PostMapping("/register/chef")
    public SimpleResponse registerChef(@RequestBody SignUpRequest request) {
        return userService.saveChef(request);
    }
    @PermitAll
    @PostMapping("/register/admin")
    public SimpleResponse registerChef(@RequestBody SignUpAdminRequest request) {
        return userService.saveAdmin(request);
    }
}

