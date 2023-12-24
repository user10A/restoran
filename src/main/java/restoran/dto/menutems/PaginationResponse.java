package restoran.dto.menutems;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restoran.entity.Menuitem;

import java.util.List;

@Builder
@Getter
@Setter
public class PaginationResponse {
        private List<Menuitem> menuItems;
        private int page;
        private int size;


    public PaginationResponse(List<Menuitem>menuItems, int page, int size) {
        this.menuItems = menuItems;
        this.page = page;
        this.size = size;
    }
}
