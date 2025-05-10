package DO_AN.OOP.repository.INGREDIENT;


import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    List<InventoryItem> findByIngredientId(String ingredientId);

    // Tìm các lô hàng của một nguyên liệu có số lượng > 0 và chưa hết hạn, sắp xếp theo ngày hết hạn
    List<InventoryItem> findByIngredientIdAndQuantityGreaterThanAndExpirationDateAfterOrderByExpirationDateAsc(
            String ingredientId,
            Float quantity,
            LocalDate expirationDate);
}
