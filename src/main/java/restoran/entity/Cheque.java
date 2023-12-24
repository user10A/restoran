package restoran.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double priceAverage;
    private ZonedDateTime createdAt;
    @ManyToMany(cascade = {CascadeType.DETACH})
    private List<Menuitem>cMenus;
    @OneToOne(cascade = {CascadeType.DETACH})
    private User cUser;
    public Cheque() {

    }
}
