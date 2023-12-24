package restoran.dto.restaurant;

import lombok.Builder;

@Builder
public record RestaurantRequest(
         String name,
         String location,
         String restType,
         int service
) {
}
