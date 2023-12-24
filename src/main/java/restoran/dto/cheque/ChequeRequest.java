package restoran.dto.cheque;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeRequest {
    private List<String>namesMenus;
}
