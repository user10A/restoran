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
    @PreAuthorize("hasAnyAuthority('CHEF', 'WAITER')")
    @PatchMapping("/update")
    public SimpleResponse update(@RequestBody SignUpRequest request) {
        return userService.update(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public SimpleResponse delete(@RequestParam String email) {
        return userService.deleteUser(email);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register/waiter")
    public SimpleResponse registerWaiter(@RequestBody SignUpRequest request){
        return userService.saveWaiter(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register/chef")
    public SimpleResponse registerChef(@RequestBody SignUpRequest request) {
        return userService.saveChef(request);
    }
}

