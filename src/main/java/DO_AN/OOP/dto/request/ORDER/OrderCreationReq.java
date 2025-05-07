package DO_AN.OOP.dto.request.ORDER;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreationReq {

    private String note;                     // Ghi chú đơn hàng (có thể để trống)

    private List<OrderItemReq> dishes;       // Danh sách món và số lượng

    private String userId;                   // ID người đặt hàng
}