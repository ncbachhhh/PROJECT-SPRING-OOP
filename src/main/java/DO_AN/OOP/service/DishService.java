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
        // Lấy toàn bộ danh sách các món ăn
        List<Dish> dishes = dishRepository.findAll();
        List<DishAvailabilityRes> resultList = new ArrayList<>();

        // Duyệt từng món ăn
        for (Dish dish : dishes) {
            // Lấy công thức (Recipe) của món ăn đó
            Recipe recipe = recipeRepository.findById(dish.getRecipeId()).orElse(null);
            // Nếu không có công thức hoặc công thức không có nguyên liệu thì bỏ qua món đó
            if (recipe == null || recipe.getIngredients() == null) continue;

            // Biến lưu số phần tối đa có thể nấu, ban đầu cho là rất lớn (vô cực)
            int maxServings = Integer.MAX_VALUE;

            // Duyệt từng nguyên liệu trong công thức
            for (RecipeIngredient ri : recipe.getIngredients()) {
                String ingredientId = ri.getIngredientId();
                Float requiredPerServing = ri.getQuantity(); // lượng nguyên liệu cần cho 1 phần

                // Lấy thông tin nguyên liệu theo id
                Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
                if (ingredient == null) {
                    // Nếu không tìm thấy nguyên liệu thì món ăn không thể nấu được
                    maxServings = 0;
                    break;
                }

                // Tính tổng lượng nguyên liệu có sẵn trong kho (cân nhắc ngày hết hạn)
                float totalAvailable = 0f;
                List<InventoryItem> inventoryList = inventoryItemRepository.findByIngredientId(ingredientId);

                // Duyệt qua từng lô hàng tồn kho của nguyên liệu này
                for (InventoryItem item : inventoryList) {
                    // Bỏ qua nguyên liệu đã hết hạn hoặc không có ngày hết hạn
                    if (item.getExpirationDate() == null || item.getExpirationDate().isBefore(LocalDate.now())) {
                        continue;
                    }
                    // Cộng dồn lượng thực tế tồn kho (số lượng * trọng lượng mỗi đơn vị)
                    totalAvailable += item.getQuantity() * ingredient.getUnitWeight();
                }

                System.out.println("→ Món: " + dish.getName());
                System.out.println("   Nguyên liệu: " + ingredient.getName());
                System.out.println("   Tồn kho thực tế: " + totalAvailable);
                System.out.println("   Cần / phần: " + requiredPerServing);
                System.out.println("   Nấu được: " + (int) (totalAvailable / requiredPerServing) + " phần");

                // Nếu không còn nguyên liệu nào thì món này không thể nấu được nữa
                if (totalAvailable <= 0) {
                    maxServings = 0;
                    break;
                }

                // Tính số phần có thể nấu từ nguyên liệu này
                int possibleFromThisIngredient = (int) (totalAvailable / requiredPerServing);
                // Lấy số phần nhỏ nhất giữa tất cả nguyên liệu (vì món cần đủ tất cả nguyên liệu)
                maxServings = Math.min(maxServings, possibleFromThisIngredient);
            }

            // Thêm kết quả cho món ăn này vào danh sách trả về
            resultList.add(DishAvailabilityRes.builder()
                    .dishId(dish.getId())
                    .dishName(dish.getName())
                    .maxServings(maxServings)
                    .build());
        }

        // Trả về danh sách món ăn và số phần tối đa có thể nấu được
        return resultList;
    }

    //Lấy thông tin của 1 món
    public Dish getDishById(String id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Món ăn không tồn tại"));
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        if (dishes.isEmpty()) {
            throw new RuntimeException("Không có món ăn nào trong hệ thống");
        }
        return dishes;
    }

}
