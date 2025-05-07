package DO_AN.OOP.dto.request.ORDER;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRemoveDishReq {
    private String dishId; // Món cần xóa khỏi đơn
}