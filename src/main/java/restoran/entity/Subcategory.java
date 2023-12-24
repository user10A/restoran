package restoran.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "mSubcategory",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Menuitem>subMenus;
    @ManyToOne()
    @JsonIgnore
    private Category sCategory;
    public Subcategory() {
    }
}
