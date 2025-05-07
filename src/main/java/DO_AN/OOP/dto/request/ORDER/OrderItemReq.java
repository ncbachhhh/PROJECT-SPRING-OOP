package DO_AN.OOP.dto.request.ORDER;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemReq {

    private String dishId;   // ID món ăn
    private int quantity;    // Số lượng món
}