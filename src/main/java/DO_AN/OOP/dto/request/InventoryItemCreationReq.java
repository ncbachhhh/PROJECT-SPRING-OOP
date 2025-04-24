package DO_AN.OOP.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemCreationReq {
    private String ingredientId;
    private Float quantity;
    private LocalDate expirationDate;
    private String userId;
}
