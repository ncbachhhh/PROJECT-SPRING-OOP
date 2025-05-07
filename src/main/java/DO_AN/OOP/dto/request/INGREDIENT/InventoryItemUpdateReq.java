package DO_AN.OOP.dto.request.INGREDIENT;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemUpdateReq {
    private Float quantity;
    private LocalDate expirationDate;
}
