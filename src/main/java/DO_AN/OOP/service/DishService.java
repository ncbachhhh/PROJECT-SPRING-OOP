package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.DISHES.DishCreationReq;
import DO_AN.OOP.dto.request.DISHES.DishUpdateReq;
import DO_AN.OOP.dto.response.DishAvailabilityRes;
import DO_AN.OOP.model.DISHES.*;
import DO_AN.OOP.model.INGREDIENT.Ingredient;
import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import DO_AN.OOP.repository.DISHES.DishRepository;
import DO_AN.OOP.repository.DISHES.RecipeRepository;
import DO_AN.OOP.repository.INGREDIENT.IngredientRepository;
import DO_AN.OOP.repository.INGREDIENT.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private InventoryItemService inventoryItemService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    //    Tạo mới món ăn
    public Dish createDish(DishCreationReq req) {
        if (dishRepository.existsByNameIgnoreCase(req.getName())) {
            throw new RuntimeException("Tên món ăn đã tồn tại");
        }

        Dish dish = Dish.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .status(req.getStatus())
                .type(req.getType())
                .popularity(req.getPopularity())
                .estimatedTime(req.getEstimatedTime())
                .recipeId(req.getRecipeId())
                .userId(req.getUserId())
                .build();

        return dishRepository.save(dish);
    }

    //    Sua món ăn
    public Dish updateDish(String id, DishUpdateReq req) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Món ăn không tồn tại"));

        dish.setName(req.getName());
        dish.setDescription(req.getDescription());
        dish.setPrice(req.getPrice());
        dish.setStatus(req.getStatus());
        dish.setType(req.getType());
        dish.setPopularity(req.getPopularity());
        dish.setEstimatedTime(req.getEstimatedTime());

        return dishRepository.save(dish);
    }

    // Tìm theo trạng thái
    public List<Dish> getDishesByStatus(DishStatus status) {
        return dishRepository.findByStatus(status);
    }

    // Tìm theo loại món ăn
    public List<Dish> getDishesByType(DishType type, DishStatus status) {
        return dishRepository.findByTypeAndStatus(type, status);
    }

    // Lấy các món ăn có độ phổ biến >= mức chỉ định (từ 0 → 5)
    public List<Dish> getPopularDishes(int minPopularity) {
        if (minPopularity < 0 || minPopularity > 5) {
            throw new IllegalArgumentException("Độ phổ biến phải nằm trong khoảng từ 0 đến 5");
        }
        return dishRepository.findByPopularityGreaterThanEqual(minPopularity);
    }

    // Tìm món ăn theo tên gần đúng (không phân biệt hoa thường)
    public List<Dish> searchDishesByName(String keyword) {
        return dishRepository.findByNameContainingIgnoreCase(keyword);
    }

    //    Lấy danh sách món ăn có thể chế biến
    public List<DishAvailabilityRes> calculateDishAvailabilityBasedOnInventory() {
        List<Dish> dishes = dishRepository.findAll();
        List<DishAvailabilityRes> resultList = new ArrayList<>();

        for (Dish dish : dishes) {
            Recipe recipe = recipeRepository.findById(dish.getRecipeId()).orElse(null);
            if (recipe == null || recipe.getIngredients() == null) continue;

            int maxServings = Integer.MAX_VALUE;

            for (RecipeIngredient ri : recipe.getIngredients()) {
                String ingredientId = ri.getIngredientId();
                Float requiredPerServing = ri.getQuantity(); // số gram cần cho 1 phần

                Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
                if (ingredient == null) {
                    maxServings = 0;
                    break;
                }

                // Tính tổng khối lượng tồn kho thực tế: sum(inventoryItem.quantity * unitWeight)
                float totalAvailable = 0f;
                List<InventoryItem> inventoryList = inventoryItemRepository.findByIngredientId(ingredientId);
                for (InventoryItem item : inventoryList) {
                    if (item.getExpirationDate() == null || item.getExpirationDate().isBefore(LocalDate.now())) {
                        continue;
                    }
                    totalAvailable += item.getQuantity() * ingredient.getUnitWeight();
                }

                System.out.println("→ Món: " + dish.getName());
                System.out.println("   Nguyên liệu: " + ingredient.getName());
                System.out.println("   Tồn kho thực tế: " + totalAvailable);
                System.out.println("   Cần / phần: " + requiredPerServing);
                System.out.println("   Nấu được: " + (int) (totalAvailable / requiredPerServing) + " phần");


                if (totalAvailable <= 0) {
                    maxServings = 0;
                    break;
                }

                int possibleFromThisIngredient = (int) (totalAvailable / requiredPerServing);
                maxServings = Math.min(maxServings, possibleFromThisIngredient);
            }

            resultList.add(DishAvailabilityRes.builder()
                    .dishId(dish.getId())
                    .dishName(dish.getName())
                    .maxServings(maxServings)
                    .build());
        }

        return resultList;
    }

    //Lấy thông tin của 1 món
    public Dish getDishById(String id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Món ăn không tồn tại"));
    }

}
