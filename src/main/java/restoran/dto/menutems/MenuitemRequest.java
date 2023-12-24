package restoran.dto.menutems;

import lombok.Builder;

@Builder
public record MenuitemRequest (
     String name,
     String image,
     double price,
     String description,
     boolean isVegetarian,
     String RestaurantName,
     String subcategoryName
){

}
