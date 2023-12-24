package restoran.dto.category;

import lombok.Builder;
import lombok.Data;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {
}
