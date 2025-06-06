package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.DISHES.DishCreationReq;
import DO_AN.OOP.dto.request.DISHES.DishUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.dto.response.DishAvailabilityRes;
import DO_AN.OOP.model.DISHES.Dish;
import DO_AN.OOP.model.DISHES.DishStatus;
import DO_AN.OOP.model.DISHES.DishType;
import DO_AN.OOP.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    // Tạo món ăn
    @PostMapping("/create")
    public ApiResponse<Dish> createDish(@RequestBody DishCreationReq req) {
        ApiResponse<Dish> response = new ApiResponse<>();

        try {
            Dish dish = dishService.createDish(req);
            response.setMessage("Thêm món ăn thành công");
            response.setResult(dish);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Thêm món ăn thất bại: " + e.getMessage());
        }

        return response;
    }

    // Sửa món ăn
    @PostMapping("/update/{dishId}")
    public ApiResponse<Dish> updateDish(@PathVariable("dishId") String dishId, @RequestBody DishUpdateReq req) {
        ApiResponse<Dish> response = new ApiResponse<>();

        try {
            Dish dish = dishService.updateDish(dishId, req);
            response.setMessage("Cập nhật món ăn thành công");
            response.setResult(dish);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật món ăn thất bại: " + e.getMessage());
        }

        return response;
    }

    // Tìm theo trạng thái
    @GetMapping("/filter/status")
    public ApiResponse<List<Dish>> filterByStatus(@RequestParam DishStatus status) {
        ApiResponse<List<Dish>> response = new ApiResponse<>();

        try {
            List<Dish> result = dishService.getDishesByStatus(status);
            response.setMessage("Lọc món theo trạng thái thành công");
            response.setResult(result);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lọc theo trạng thái thất bại: " + e.getMessage());
        }

        return response;
    }

    // Tìm theo loại món ăn
    @GetMapping("/filter/type")
    public ApiResponse<List<Dish>> filterByType(@RequestParam DishType type,
                                                @RequestParam DishStatus status) {
        ApiResponse<List<Dish>> response = new ApiResponse<>();

        try {
            List<Dish> result = dishService.getDishesByType(type, status);
            response.setMessage("Lọc món theo loại thành công");
            response.setResult(result);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lọc theo loại thất bại: " + e.getMessage());
        }

        return response;
    }

    // Tìm món phổ biến
    @GetMapping("/filter/popular")
    public ApiResponse<List<Dish>> getPopularDishes(@RequestParam(defaultValue = "3") int minPopularity) {
        ApiResponse<List<Dish>> response = new ApiResponse<>();

        try {
            List<Dish> result = dishService.getPopularDishes(minPopularity);
            response.setMessage("Danh sách món ăn phổ biến");
            response.setResult(result);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy món phổ biến thất bại: " + e.getMessage());
        }

        return response;
    }

    // Tìm món theo tên gần đúng
    @GetMapping("/search")
    public ApiResponse<List<Dish>> searchByName(@RequestParam String keyword) {
        ApiResponse<List<Dish>> response = new ApiResponse<>();

        try {
            List<Dish> result = dishService.searchDishesByName(keyword);
            response.setMessage("Tìm thấy món ăn phù hợp");
            response.setResult(result);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Tìm kiếm thất bại: " + e.getMessage());
        }

        return response;
    }

    // Lấy số món ăn có thể chế biến dựa trên nguyên liệu
    @GetMapping("/available-dishes")
    public ApiResponse<List<DishAvailabilityRes>> getAvailableDishes() {
        ApiResponse<List<DishAvailabilityRes>> response = new ApiResponse<>();
        try {
            List<DishAvailabilityRes> dishes = dishService.calculateDishAvailabilityBasedOnInventory();
            response.setMessage("Tính toán số phần món ăn có thể chế biến thành công");
            response.setResult(dishes);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage("Lỗi khi tính số phần món ăn: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/{dishId}")
    public ApiResponse<Dish> getDishById(@PathVariable("dishId") String dishId) {
        ApiResponse<Dish> response = new ApiResponse<>();
        try {
            Dish dish = dishService.getDishById(dishId);
            response.setMessage("Lấy món ăn thành công");
            response.setResult(dish);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy món ăn thất bại: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/all")
    public ApiResponse<List<Dish>> getAllDishes() {
        ApiResponse<List<Dish>> response = new ApiResponse<>();
        try {
            List<Dish> dishes = dishService.getAllDishes();
            response.setMessage("Lấy tất cả món ăn thành công");
            response.setResult(dishes);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy tất cả món ăn thất bại: " + e.getMessage());
        }
        return response;
    }
}
