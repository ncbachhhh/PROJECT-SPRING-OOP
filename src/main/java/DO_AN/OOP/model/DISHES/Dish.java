package DO_AN.OOP.model.DISHES;

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
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private Float price;

    @Enumerated(EnumType.STRING)
    private DishStatus status;

    @Enumerated(EnumType.STRING)
    private DishType type;

    private int popularity;
    private String estimatedTime;

    private String recipeId;
    private String userId;
}