package restoran.service;

import restoran.dto.user.*;

public interface UserService {
    AuthenticationResponse singIn(SignInRequest request);
    SimpleResponse saveWaiter(SignUpRequest request);
    SimpleResponse saveChef(SignUpRequest request);
    SimpleResponse deleteUser (String email);
    SimpleResponse update (SignUpRequest request);
}
