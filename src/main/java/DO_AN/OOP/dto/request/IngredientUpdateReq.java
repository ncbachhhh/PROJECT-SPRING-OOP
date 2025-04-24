package DO_AN.OOP.dto.request;

import DO_AN.OOP.modal.INGREDIENT.TypeIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientUpdateReq {
    private String name;
    private Float unitWeight;
    private TypeIngredient type;
    private Float minimumAmount;
}
