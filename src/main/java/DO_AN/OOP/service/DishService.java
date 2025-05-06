package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.DishCreationReq;
import DO_AN.OOP.dto.request.DishUpdateReq;
import DO_AN.OOP.model.DISHES.Dish;
import DO_AN.OOP.model.DISHES.DishStatus;
import DO_AN.OOP.model.DISHES.DishType;
import DO_AN.OOP.repository.DISHES.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

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
    public List<Dish> getDishesByType(DishType type) {
        return dishRepository.findByType(type);
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
}
