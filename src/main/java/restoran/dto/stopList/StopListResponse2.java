package restoran.dto.stopList;

import lombok.Builder;
import lombok.Data;
import restoran.entity.Menuitem;

import java.time.LocalDate;

@Builder
@Data
public class StopListResponse2 {
    private Long id;
    private String reason;
    private LocalDate date;

    public StopListResponse2(Long id, String reason, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
