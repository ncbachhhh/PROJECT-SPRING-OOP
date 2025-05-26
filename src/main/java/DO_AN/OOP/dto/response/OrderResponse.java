package DO_AN.OOP.dto.response;

import DO_AN.OOP.dto.DishDTO;
import DO_AN.OOP.model.ORDER.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private OrderStatus status;
    private Float totalPrice;
    private LocalDateTime date;
    private String note;
    private List<DishDTO> dishes;
    private String userId;
    private String staffId;
}
