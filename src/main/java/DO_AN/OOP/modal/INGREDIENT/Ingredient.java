package DO_AN.OOP.modal.INGREDIENT;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Float unitWeight;
    @Enumerated(EnumType.STRING)
    private TypeIngredient type;
    private Float minimumAmount;
}
