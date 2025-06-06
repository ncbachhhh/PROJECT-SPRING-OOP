package DO_AN.OOP.dto.request.INGREDIENT;

import DO_AN.OOP.model.INGREDIENT.TypeIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCreationReq {
    private String name;
    private Float unitWeight;
    private TypeIngredient type;
    private Float minimumAmount;
}
