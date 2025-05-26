package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.DISHES.RecipeCreationReq;
import DO_AN.OOP.dto.request.DISHES.RecipeUpdateReq;
import DO_AN.OOP.model.DISHES.Recipe;
import DO_AN.OOP.model.DISHES.RecipeIngredient;
import DO_AN.OOP.repository.DISHES.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(RecipeCreationReq req) {
        List<RecipeIngredient> ingredients = req.getIngredients().stream()
                .map(i -> RecipeIngredient.builder()
                        .ingredientId(i.getId())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        Recipe recipe = Recipe.builder()
                .name(req.getName())
                .description(req.getDescription())
                .userId(req.getUserId())
                .ingredients(ingredients)
                .build();

        return recipeRepository.save(recipe);
    }

    // sửa công thức món ăn
    public Recipe updateRecipe(String recipeId, RecipeUpdateReq req) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Công thức không tồn tại"));

        List<RecipeIngredient> ingredients = req.getIngredients().stream()
                .map(i -> RecipeIngredient.builder()
                        .ingredientId(i.getId())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        recipe.setIngredients(ingredients);
        recipe.setDescription(req.getDescription());
        recipe.setName(req.getName());

        return recipeRepository.save(recipe);
    }

    // Lấy các công thức
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Lấy công thức theo ID
    public Recipe getRecipeById(String recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Công thức không tồn tại"));
    }
}
