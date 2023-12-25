package restoran.service.Impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restoran.config.jwt.JwtService;
import restoran.dto.user.*;
import restoran.entity.Restaurant;
import restoran.entity.User;
import restoran.enums.Role;
import restoran.exceptions.AlreadyExistsException;
import restoran.exceptions.ConstraintsViolationException;
import restoran.exceptions.NotFoundException;
import restoran.repo.RestaurantRepo;
import restoran.repo.UserRepo;
import restoran.service.UserService;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final RestaurantRepo restaurantRepo;

    @Override
    public AuthenticationResponse singIn(SignInRequest request) {

        User user = userRepository.getUserByEmail(request.email()).orElseThrow(() -> new NotFoundException(
                "user with email: " + request.email() + " not fount"));
        if (request.email().isBlank()) {
            throw new BadCredentialsException("email is blank");
        }
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken)
                .email(user.getEmail())
                .build();
    }

    @Override
    public SimpleResponse saveWaiter(SignUpRequest request) throws ConstraintsViolationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email: " + request.getEmail() + " already exists!");
        }

        Optional<Restaurant> restaurant = Optional.ofNullable(restaurantRepo.getByName(request.getRestaurantName())
                .orElseThrow(() -> new NotFoundException("Restaurant with Name:" + request.getRestaurantName() + " not found")));

        if (restaurant.isPresent()) {
            checkWaiterConstraints(request.getDateOfBirth(), request.getExperience());

            if (restaurant.get().getNumberOfEmployees() >= 14) {
                throw new ConstraintsViolationException("The maximum number of employees in the restaurant has been exceeded");
            }

            restaurantRepo.save(restaurant.get());

            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .dateOfBirth(request.getDateOfBirth())
                    .role(Role.WAITER)
                    .phoneNumber(request.getPhoneNumber())
                    .experience(request.getExperience())
                    .uRestaurant(restaurant.get())
                    .build();

            userRepository.save(user);

            int updatedNumberOfEmployees = incrementNumberOfEmployees(restaurant.get().getNumberOfEmployees());
            restaurant.get().setNumberOfEmployees(updatedNumberOfEmployees);

            restaurantRepo.save(restaurant.get());

            return new SimpleResponse("Your application has been successfully submitted", HttpStatus.OK);
        } else {
            return new SimpleResponse("Restaurant not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SimpleResponse saveChef(SignUpRequest request) throws ConstraintsViolationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email: " + request.getEmail() + " already exists!");
        }

        Optional<Restaurant> restaurant = Optional.ofNullable(restaurantRepo.getByName(request.getRestaurantName()).orElseThrow(() -> new NotFoundException("Restaurant with Name:" + request.getRestaurantName() + " not found")));

        if (restaurant.isPresent()) {

            checkChefConstraints(request.getDateOfBirth(), request.getExperience());

            if (restaurant.get().getNumberOfEmployees() >= 14) {
                throw new ConstraintsViolationException("The maximum number of employees in the restaurant has been exceeded");
            }
            restaurantRepo.save(restaurant.get());
            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .dateOfBirth(request.getDateOfBirth())
                    .role(Role.CHEF)
                    .phoneNumber(request.getPhoneNumber())
                    .experience(request.getExperience())
                    .uRestaurant(restaurant.get())
                    .build();

            userRepository.save(user);
            int updatedNumberOfEmployees = incrementNumberOfEmployees(restaurant.get().getNumberOfEmployees());
            restaurant.get().setNumberOfEmployees(updatedNumberOfEmployees);
            restaurantRepo.save(restaurant.get());
            return new SimpleResponse("Your application has been successfully submitted", HttpStatus.OK);
        } else {
            return new SimpleResponse("Restaurant not found", HttpStatus.BAD_REQUEST);
        }
    }

    public int incrementNumberOfEmployees(int currentCount) {
        return currentCount + 1;
    }

    public int incrementMinusNumberOfEmployees(int currentCount) {
        return currentCount - 1;
    }

    private void checkWaiterConstraints(LocalDate dateOfBirth, int experience) throws ConstraintsViolationException {
        int age = calculateAge(dateOfBirth);
        if (age < 18 || age > 30 || experience <= 0) {
            throw new ConstraintsViolationException("Waiter must be between 18 and 30 years old and have more than 1 year of experience");
        }
    }

    private void checkChefConstraints(LocalDate dateOfBirth, int experience) throws ConstraintsViolationException {
        int age = calculateAge(dateOfBirth);
        if (age < 25 || age > 45 || experience <= 1) {
            throw new ConstraintsViolationException("Chef must be between 25 and 45 years old and have more than 2 years of experience");
        }
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public SimpleResponse deleteUser(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(
                () -> new NotFoundException(
                        "user with email: " + email + " not found"));
        if (email.isBlank()) {
            throw new BadCredentialsException("email is blank");
        }

        Restaurant restaurant = user.getURestaurant();
        if (restaurant != null) {
            int updatedNumberOfEmployees = incrementMinusNumberOfEmployees(restaurant.getNumberOfEmployees());
            restaurant.setNumberOfEmployees(updatedNumberOfEmployees);
            restaurantRepo.save(restaurant);
        }

        userRepository.delete(user);
        return new SimpleResponse("Пользователь успешно удален!", HttpStatus.OK);
    }


    @Override
    public SimpleResponse update(SignUpRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id: " + userId + " not found"));
        log.info("User find by id: {}", userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
        log.info("Saving");
        return new SimpleResponse("User successfully updated !", HttpStatus.OK);
    }
    @PostConstruct
    public void initAdmin() {
        User user = new User();
        user.setFirstName("erkin");
        user.setLastName("toigonbaev");
        user.setDateOfBirth(LocalDate.of(2003,3,13));
        user.setEmail("erkin@gmail.com");
        user.setPassword(encoder.encode("erkin"));
        user.setRole(Role.ADMIN);
        user.setPhoneNumber("+996999999999");
        user.setExperience(5);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
}
