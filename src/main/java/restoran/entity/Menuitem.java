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
public class Menuitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String image;
    private double price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne()
    private Restaurant mRestaurant;
    @ManyToMany(mappedBy = "cMenus")
    @JsonIgnore
    private List<Cheque>mCheques;
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private StopList mStopList;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {})
    @JsonIgnore
    private Subcategory mSubcategory;
    public Menuitem() {

    }
}
