package restoran.dto.user;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import restoran.enums.Role;

import java.time.LocalDate;

@Builder
public record UpdateRequest(
        @NotNull(message = "Write your old email")
        String oldEmail,
        @NotNull(message = "Write your old password")
        String oldPassword,
        @NotNull(message = "FirstName")
        String firstName,
        @NotNull(message = "LastName")
        String lastName,
        @NotNull(message = "Date of Birth")
        LocalDate dateOfBirth,
        @NotNull(message = "Email")
        String email,
        @NotNull(message = "Come up with a password")
        String password,
        @NotNull(message = "Phone number")
        String phoneNumber,
        @NotNull(message = "Write down your position")
        Role role,
        @NotNull(message = "Experience")
        int experience
) {
}
