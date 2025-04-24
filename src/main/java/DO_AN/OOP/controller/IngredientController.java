package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.IngredientCreationReq;
import DO_AN.OOP.dto.request.IngredientUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.modal.INGREDIENT.Ingredient;
import DO_AN.OOP.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    //    Thêm nguyên liệu
    @PostMapping("/create")
    ApiResponse<Ingredient> createIngredient(@RequestBody IngredientCreationReq req) {
        ApiResponse<Ingredient> response = new ApiResponse<>();
        Ingredient ingredient = ingredientService.createIngredient(req);

        if (ingredient != null) {
            response.setMessage("Thêm nguyên liệu thành công");
            response.setResult(ingredient);
        } else {
            response.setCode(400);
            response.setMessage("Thêm nguyên liệu thất bại");
        }

        return response;
    }

    //    Cập nhật nguyên liệu
    @PostMapping("/update/{ingredientId}")
    ApiResponse<Ingredient> updateIngredient(@PathVariable("ingredientId") String ingredientId, @RequestBody IngredientUpdateReq req) {
        ApiResponse<Ingredient> response = new ApiResponse<>();
        Ingredient ingredient = ingredientService.updateIngredient(ingredientId, req);

        if (ingredient != null) {
            response.setMessage("Cập nhật nguyên liệu thành công");
            response.setResult(ingredient);
        } else {
            response.setCode(400);
            response.setMessage("Cập nhật nguyên liệu thất bại");
        }

        return response;
    }

    //    Lấy danh sách nguyên liệu
    @GetMapping("/get/ingredients")
    ApiResponse<List<Ingredient>> getIngredients() {
        ApiResponse<List<Ingredient>> response = new ApiResponse<>();
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        if (ingredients != null) {
            response.setMessage("Lấy danh sách nguyên liệu thành công");
            response.setResult(ingredients);
        } else {
            response.setCode(400);
            response.setMessage("Lấy danh sách nguyên liệu thất bại");
        }

        return response;
    }

    //   Lấy danh sách nguyên liệu có tồn dư dưới 5
    @GetMapping("/get/low-stock")
    ApiResponse<List<Ingredient>> getLowStockIngredients() {
        ApiResponse<List<Ingredient>> response = new ApiResponse<>();
        List<Ingredient> ingredients = ingredientService.getLowStockIngredients();

        if (ingredients != null) {
            response.setMessage("Lấy danh sách nguyên liệu có tồn dư dưới 5 thành công");
            response.setResult(ingredients);
        } else {
            response.setCode(400);
            response.setMessage("Lấy danh sách nguyên liệu có tồn dư dưới 5 thất bại");
        }

        return response;
    }
}
