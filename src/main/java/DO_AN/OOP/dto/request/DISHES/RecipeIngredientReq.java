package DO_AN.OOP.dto.request.DISHES;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientReq {
    private String id;
    private Float quantity;
}
