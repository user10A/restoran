package restoran.dto.cheque;
import lombok.*;
import restoran.dto.menutems.MenuitemResponse;
import restoran.entity.Menuitem;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ChequeResponse {
    private Long id;
    private String WaiterFullName;
    private List<MenuitemResponse>items;
    private double priceAverage;
    private ZonedDateTime createdAt;
    private int service ;
    private double grandTotal;

}
