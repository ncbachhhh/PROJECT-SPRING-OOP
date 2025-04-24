package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.InventoryItemCreationReq;
import DO_AN.OOP.dto.request.InventoryItemUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.modal.INGREDIENT.InventoryItem;
import DO_AN.OOP.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-item")
public class InventoryItemController {
    @Autowired
    private InventoryItemService inventoryItemService;

    //    Thêm nguyên liệu
    @PostMapping("/create")
    ApiResponse<InventoryItem> createInventoryItem(@RequestBody InventoryItemCreationReq req) {
        ApiResponse<InventoryItem> response = new ApiResponse<>();
        InventoryItem inventoryItem = inventoryItemService.createInventoryItem(req);

        if (inventoryItem != null) {
            response.setMessage("Thêm nguyên liệu thành công");
            response.setResult(inventoryItem);
        } else {
            response.setCode(400);
            response.setMessage("Thêm nguyên liệu thất bại");
        }

        return response;
    }

    //    Cập nhật nguyên liệu
    @PostMapping("/update/{inventoryItemId}")
    ApiResponse<InventoryItem> updateInventoryItem(@PathVariable("inventoryItemId") String inventoryItemId, @RequestBody InventoryItemUpdateReq req) {
        ApiResponse<InventoryItem> response = new ApiResponse<>();
        InventoryItem inventoryItem = inventoryItemService.updateInventoryItem(inventoryItemId, req);

        if (inventoryItem != null) {
            response.setMessage("Cập nhật nguyên liệu thành công");
            response.setResult(inventoryItem);
        } else {
            response.setCode(400);
            response.setMessage("Cập nhật nguyên liệu thất bại");
        }

        return response;
    }

    // Tính toán xem sản phẩm nào hết hạn sau 7 ngày
    @GetMapping("/expiring-soon")
    public ApiResponse<List<InventoryItem>> getExpiringItems() {
        List<InventoryItem> items = inventoryItemService.getExpiringWithin7Days();
        if (items.isEmpty()) {
            return new ApiResponse<>(200, "Không có nguyên liệu nào hết hạn trong 7 ngày tới", items);
        } else {
            return new ApiResponse<>(200, "Danh sách nguyên liệu sắp hết hạn", items);
        }
    }

    // Tính toán số lượng còn dư theo nguyên liệu
    @GetMapping("/remaining-quantity/{ingredientId}")
    public ApiResponse<Float> getRemainingQuantity(@PathVariable("ingredientId") String ingredientId) {
        Float remainingQuantity = inventoryItemService.getRemainingQuantityByIngredientId(ingredientId);
        if (remainingQuantity > 0) {
            return new ApiResponse<>(200, "Số lượng còn lại của nguyên liệu " + ingredientId, remainingQuantity);
        } else {
            return new ApiResponse<>(400, "Nguyên liệu không tồn tại hoặc đã hết hạn", null);
        }
    }
}
