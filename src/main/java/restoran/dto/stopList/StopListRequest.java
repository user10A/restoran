package restoran.dto.stopList;

import lombok.Builder;

@Builder
public record StopListRequest(
        String reason

) {
}
