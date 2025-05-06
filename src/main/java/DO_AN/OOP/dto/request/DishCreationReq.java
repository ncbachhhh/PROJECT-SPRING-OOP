package DO_AN.OOP.dto.request;

import DO_AN.OOP.model.DISHES.DishStatus;
import DO_AN.OOP.model.DISHES.DishType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishCreationReq {
    private String name;
    private String description;
    private Float price;
    private DishStatus status;              // DANG_BAN, TAM_HET, ...
    private DishType type;                  // MON_CHINH, ...
    private int popularity;
    private String estimatedTime;
    private String recipeId;
    private String userId;
}
