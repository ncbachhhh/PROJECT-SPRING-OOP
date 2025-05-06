package DO_AN.OOP.dto.request;

import DO_AN.OOP.model.DISHES.DishStatus;
import DO_AN.OOP.model.DISHES.DishType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishUpdateReq {
    private String name;
    private String description;
    private Float price;
    private DishStatus status;
    private DishType type;
    private int popularity;
    private String estimatedTime;
}