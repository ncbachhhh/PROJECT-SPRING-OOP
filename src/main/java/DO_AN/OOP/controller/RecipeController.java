package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.RecipeCreationReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.modal.DISHES.Recipe;
import DO_AN.OOP.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    //    Thêm công thức
    @PostMapping("/create")
    ApiResponse<Recipe> createRecipe(@RequestBody RecipeCreationReq req) {
        ApiResponse<Recipe> response = new ApiResponse<>();
        Recipe recipe = recipeService.createRecipe(req);

        if (recipe != null) {
            response.setMessage("Thêm công thức thành công");
            response.setResult(recipe);
        } else {
            response.setCode(400);
            response.setMessage("Thêm công thức thất bại");
        }

        return response;
    }
}
