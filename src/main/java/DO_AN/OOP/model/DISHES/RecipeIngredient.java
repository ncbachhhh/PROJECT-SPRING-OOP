package DO_AN.OOP.model.DISHES;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RecipeIngredient {
    private String ingredientId;
    private Float quantity;
}
