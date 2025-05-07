package DO_AN.OOP.dto.request.DISHES;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUpdateReq {
    private String name;
    private String description;
    private List<RecipeIngredientReq> ingredients;
}
