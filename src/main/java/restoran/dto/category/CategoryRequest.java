package restoran.dto.category;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name
) {
}
