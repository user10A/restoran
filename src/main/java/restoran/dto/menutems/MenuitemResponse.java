package restoran.dto.menutems;

import lombok.Builder;
import lombok.Data;
import restoran.dto.restaurant.RestaurantResponse;
import restoran.dto.stopList.StopListResponse;
import restoran.dto.stopList.StopListResponse2;
import restoran.dto.subCategory.SubCategoryResponse;
import restoran.entity.*;

import java.util.List;
@Builder
@Data
public class MenuitemResponse {
    private Long id;
    private String name;
    private String image;
    private double price;
    private String description;
    private boolean isVegetarian;


    public MenuitemResponse(Long id, String name, String image, double price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
    public static MenuitemResponse fromMenuitem(Menuitem menuitem) {
        return MenuitemResponse.builder()
                .id(menuitem.getId())
                .name(menuitem.getName())
                .image(menuitem.getImage())
                .price(menuitem.getPrice())
                .description(menuitem.getDescription())
                .isVegetarian(menuitem.isVegetarian())
                .build();
    }
}
