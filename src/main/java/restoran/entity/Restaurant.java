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
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "mRestaurant",cascade = {CascadeType.REMOVE})
    private List<Menuitem>rMenus;
    @OneToMany(mappedBy = "uRestaurant", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    List<User>rUsers;
    public Restaurant() {
    }
}
