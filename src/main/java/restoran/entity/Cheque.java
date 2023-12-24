package restoran.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private double grandTotal;
    private int service;
    @ManyToMany(cascade = {CascadeType.DETACH})
    @JsonIgnore
    private List<Menuitem>cMenus;
    @OneToOne(cascade = {CascadeType.DETACH})
    @JsonIgnore
    private User cUser;
    public Cheque() {

    }
}
