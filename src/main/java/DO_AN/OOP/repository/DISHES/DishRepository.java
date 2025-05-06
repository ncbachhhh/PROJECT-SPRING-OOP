package DO_AN.OOP.repository.DISHES;

import DO_AN.OOP.model.DISHES.Dish;
import DO_AN.OOP.model.DISHES.DishStatus;
import DO_AN.OOP.model.DISHES.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, String> {
    // Tìm theo trạng thái
    List<Dish> findByStatus(DishStatus status);

    // Tìm theo loại món ăn
    List<Dish> findByType(DishType type);

    // Tìm món ăn phổ biến (trên một mức độ phổ biến nào đó)
    List<Dish> findByPopularityGreaterThanEqual(int popularity);

    // Tìm món ăn theo tên (tìm gần đúng)
    List<Dish> findByNameContainingIgnoreCase(String keyword);

    boolean existsByNameIgnoreCase(String name);
}
