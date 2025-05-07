package DO_AN.OOP.dto.request.DISHES;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCreationReq {
    private String name;
    private String description;
    private String userId;
    private List<RecipeIngredientReq> ingredients;
}
