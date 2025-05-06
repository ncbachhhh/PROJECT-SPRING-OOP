package DO_AN.OOP.dto.request;

import DO_AN.OOP.model.ORDER.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateStatusReq {
    private OrderStatus status;
}