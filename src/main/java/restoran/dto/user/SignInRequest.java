package restoran.dto.user;

import lombok.Builder;

@Builder
public record SignInRequest(
        String email,
        String password) {
}
