package DO_AN.OOP.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {
    private String dishName;
    private int quantity;
}
