package DO_AN.OOP.repository.INGREDIENT;


import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    List<InventoryItem> findByIngredientId(String ingredientId);
}
