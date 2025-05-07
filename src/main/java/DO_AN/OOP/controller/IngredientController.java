package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.INGREDIENT.IngredientCreationReq;
import DO_AN.OOP.dto.request.INGREDIENT.IngredientUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.INGREDIENT.Ingredient;
import DO_AN.OOP.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    // Thêm nguyên liệu
    @PostMapping("/create")
    public ApiResponse<Ingredient> createIngredient(@RequestBody IngredientCreationReq req) {
        ApiResponse<Ingredient> response = new ApiResponse<>();
        try {
            Ingredient ingredient = ingredientService.createIngredient(req);
            response.setMessage("Thêm nguyên liệu thành công");
            response.setResult(ingredient);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Thêm nguyên liệu thất bại: " + e.getMessage());
        }
        return response;
    }

    // Cập nhật nguyên liệu
    @PostMapping("/update/{ingredientId}")
    public ApiResponse<Ingredient> updateIngredient(
            @PathVariable("ingredientId") String ingredientId,
            @RequestBody IngredientUpdateReq req) {

        ApiResponse<Ingredient> response = new ApiResponse<>();
        try {
            Ingredient ingredient = ingredientService.updateIngredient(ingredientId, req);
            response.setMessage("Cập nhật nguyên liệu thành công");
            response.setResult(ingredient);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật nguyên liệu thất bại: " + e.getMessage());
        }
        return response;
    }

    // Lấy danh sách nguyên liệu
    @GetMapping("/get/ingredients")
    public ApiResponse<List<Ingredient>> getIngredients() {
        ApiResponse<List<Ingredient>> response = new ApiResponse<>();
        try {
            List<Ingredient> ingredients = ingredientService.getAllIngredients();
            response.setMessage("Lấy danh sách nguyên liệu thành công");
            response.setResult(ingredients);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy danh sách nguyên liệu thất bại: " + e.getMessage());
        }
        return response;
    }

    // Lấy danh sách nguyên liệu tồn dư dưới minimumAmount
    @GetMapping("/get/low-stock")
    public ApiResponse<List<Ingredient>> getLowStockIngredients() {
        ApiResponse<List<Ingredient>> response = new ApiResponse<>();
        try {
            List<Ingredient> ingredients = ingredientService.getLowStockIngredients();
            response.setMessage("Lấy danh sách nguyên liệu tồn dư thấp thành công");
            response.setResult(ingredients);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy danh sách nguyên liệu tồn dư thấp thất bại: " + e.getMessage());
        }
        return response;
    }
}
