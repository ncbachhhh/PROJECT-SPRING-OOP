package DO_AN.OOP.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderReduceDishQuantityReq {
    private String dishId;
    private int amount; // số lượng muốn giảm
}
