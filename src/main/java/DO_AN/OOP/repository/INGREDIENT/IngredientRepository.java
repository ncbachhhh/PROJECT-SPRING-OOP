package DO_AN.OOP.repository.INGREDIENT;

import DO_AN.OOP.modal.INGREDIENT.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    boolean existsByName(String name);

    Optional<Ingredient> findByName(String name);
}
