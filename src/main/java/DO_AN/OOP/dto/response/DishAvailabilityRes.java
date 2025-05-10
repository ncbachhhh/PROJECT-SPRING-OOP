package DO_AN.OOP.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishAvailabilityRes {
    private String dishId;
    private String dishName;
    private int maxServings;
}