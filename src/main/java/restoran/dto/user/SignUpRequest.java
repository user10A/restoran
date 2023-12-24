package restoran.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import restoran.enums.Role;
import restoran.valid.validation.EmailValidation;
import restoran.valid.validation.PasswordValidation;
import restoran.valid.validation.PhoneNumberValidation;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class SignUpRequest {
        @NotNull(message = "FirstName")
        private String firstName;

        @NotNull(message = "LastName")
        private String lastName;

        @NotNull(message = "Date of Birth")
        private LocalDate dateOfBirth;

        @EmailValidation
        String email;

        @PasswordValidation
        String password;

        @PhoneNumberValidation
        String phoneNumber;

        @NotNull(message = "Experience")
        private int experience;

        @NotNull(message = "Please write down how many years you have worked")
        private String restaurantName;

        public SignUpRequest(String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String phoneNumber, int experience, String restaurantName) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.dateOfBirth = dateOfBirth;
                this.email = email;
                this.password = password;
                this.phoneNumber = phoneNumber;
                this.experience = experience;
                this.restaurantName = restaurantName;
        }
}
