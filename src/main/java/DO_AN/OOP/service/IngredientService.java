package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.INGREDIENT.IngredientCreationReq;
import DO_AN.OOP.dto.request.INGREDIENT.IngredientUpdateReq;
import DO_AN.OOP.model.INGREDIENT.Ingredient;
import DO_AN.OOP.repository.INGREDIENT.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private InventoryItemService inventoryItemService;

    //    Tạo mới nguyên liệu
    public Ingredient createIngredient(IngredientCreationReq req) {
        if (ingredientRepository.existsByName(req.getName())) {
            throw new RuntimeException("Nguyên liệu đã tồn tại");
        }

        Ingredient ingredient = Ingredient.builder()
                .name(req.getName())
                .unitWeight(req.getUnitWeight())
                .type(req.getType())
                .minimumAmount(req.getMinimumAmount())
                .build();

        return ingredientRepository.save(ingredient);
    }

    //    Cập nhật nguyên liệu
    public Ingredient updateIngredient(String id, IngredientUpdateReq req) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nguyên liệu không tồn tại"));

        ingredient.setName(req.getName());
        ingredient.setUnitWeight(req.getUnitWeight());
        ingredient.setType(req.getType());
        ingredient.setMinimumAmount(req.getMinimumAmount());

        return ingredientRepository.save(ingredient);
    }

    //    Hiển thị danh sách nguyên liệu
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        if (ingredients.isEmpty()) {
            throw new RuntimeException("Không có nguyên liệu nào");
        }

        return ingredients;
    }

    //    Tìm các nguyên liệu có tồn dư thấp hơn mức minimumAmount của từng nguyên liệu
    public List<Ingredient> getLowStockIngredients() {
        List<Ingredient> allIngredients = ingredientRepository.findAll();

        return allIngredients.stream()
                .filter(ingredient -> {
                    Float remaining = inventoryItemService.getRemainingQuantityByIngredientId(ingredient.getId());
                    return remaining != null && remaining < ingredient.getMinimumAmount();
                })
                .collect(Collectors.toList());
    }
}
