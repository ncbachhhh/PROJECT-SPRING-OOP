package DO_AN.OOP.repository.DISHES;

import DO_AN.OOP.model.DISHES.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {

}
