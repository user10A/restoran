package restoran.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SimpleResponse {
    private HttpStatus status;
    private String message;

    public SimpleResponse() {
    }

    public SimpleResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
