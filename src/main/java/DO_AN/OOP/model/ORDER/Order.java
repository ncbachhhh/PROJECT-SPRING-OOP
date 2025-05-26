package DO_AN.OOP.model.ORDER;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Float totalPrice;

    private LocalDateTime date;

    private String note;

    @ElementCollection
    private List<OrderItem> dishes;

    private String userId;

    private String staffId;
}