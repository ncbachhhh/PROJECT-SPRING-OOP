package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.DISHES.RecipeCreationReq;
import DO_AN.OOP.dto.request.DISHES.RecipeUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.DISHES.Recipe;
import DO_AN.OOP.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // Thêm công thức món ăn
    @PostMapping("/create")
    public ApiResponse<Recipe> createRecipe(@RequestBody RecipeCreationReq req) {
        ApiResponse<Recipe> response = new ApiResponse<>();
        try {
            Recipe recipe = recipeService.createRecipe(req);
            response.setMessage("Thêm công thức thành công");
            response.setResult(recipe);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Thêm công thức thất bại: " + e.getMessage());
        }
        return response;
    }

    // Sửa công thức món ăn
    @PostMapping("/update/{recipeId}")
    public ApiResponse<Recipe> updateRecipe(@PathVariable("recipeId") String recipeId,
                                            @RequestBody RecipeUpdateReq req) {
        ApiResponse<Recipe> response = new ApiResponse<>();
        try {
            Recipe recipe = recipeService.updateRecipe(recipeId, req);
            response.setMessage("Cập nhật công thức thành công");
            response.setResult(recipe);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật công thức thất bại: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/get/all")
    public ApiResponse<List<Recipe>> getAllRecipes() {
        ApiResponse<List<Recipe>> response = new ApiResponse<>();
        try {
            List<Recipe> recipes = recipeService.getAllRecipes();
            response.setMessage("Lấy danh sách công thức thành công");
            response.setResult(recipes);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy danh sách công thức thất bại: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/get/{recipeId}")
    public ApiResponse<Recipe> getRecipeById(@PathVariable("recipeId") String recipeId) {
        ApiResponse<Recipe> response = new ApiResponse<>();
        try {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            response.setMessage("Lấy công thức thành công");
            response.setResult(recipe);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy công thức thất bại: " + e.getMessage());
        }
        return response;
    }

}
