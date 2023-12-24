package restoran.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import restoran.entity.User;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtService {

    @Value("@{spring.secret_key}")
    private String SECRET_KEY;

public String generateToken(UserDetails userDetails) {
    User user = (User) userDetails;
    return JWT.create()
            .withClaim("username", user.getUsername())
            .withClaim("userId", user.getId())  // Добавьте userId в токен
            .withIssuedAt(new Date())
            .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(60).toInstant()))
            .sign(Algorithm.HMAC256(SECRET_KEY));
}

    public DecodedJWT validateToken(String token) {
        JWTVerifier jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        return jwt.verify(token);  // Возвращайте DecodedJWT, чтобы получить доступ ко всем утверждениям
    }
}
