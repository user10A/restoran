package restoran.dto.cheque;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import restoran.entity.Menuitem;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ChequeResponse2 {
    private List<Menuitem>items;

}
