package restoran.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @ManyToOne(cascade = {})
    @JsonIgnore
    private Restaurant mRestaurant;
    @ManyToMany(mappedBy = "cMenus")
    @JsonIgnore
    private List<Cheque>mCheques;
    @OneToOne(cascade = {CascadeType.DETACH})
    @JsonIgnore
    private StopList mStopList;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH})
    @JsonIgnore
    private Subcategory mSubcategory;
    public Menuitem() {
    }

    public Menuitem(long id, String name, String image, double price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
