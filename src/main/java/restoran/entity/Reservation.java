package restoran.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private User user;  // Сотрудник, отправляющий заявку

    @ManyToOne
    private Restaurant restaurant;  // Ресторан, на который отправляется заявка

    private ZonedDateTime createdAt;  // Дата и время создания заявки

    private boolean approved;  // Флаг, указывающий, одобрена ли заявка

    public Reservation() {
    }
}

