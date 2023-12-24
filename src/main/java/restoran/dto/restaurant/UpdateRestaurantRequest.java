package restoran.dto.restaurant;

import lombok.Builder;

@Builder
public record UpdateRestaurantRequest(
         String oldName,
         String name,
         String location,
         String restType,
         int service
) {
}
