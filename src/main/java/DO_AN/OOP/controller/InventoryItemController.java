package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.InventoryItemCreationReq;
import DO_AN.OOP.dto.request.InventoryItemUpdateReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import DO_AN.OOP.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-item")
public class InventoryItemController {

    @Autowired
    private InventoryItemService inventoryItemService;

    // Thêm nguyên liệu vào kho
    @PostMapping("/create")
    public ApiResponse<InventoryItem> createInventoryItem(@RequestBody InventoryItemCreationReq req) {
        ApiResponse<InventoryItem> response = new ApiResponse<>();
        try {
            InventoryItem inventoryItem = inventoryItemService.createInventoryItem(req);
            response.setMessage("Thêm nguyên liệu thành công");
            response.setResult(inventoryItem);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Thêm nguyên liệu thất bại: " + e.getMessage());
        }
        return response;
    }

    // Cập nhật nguyên liệu trong kho
    @PostMapping("/update/{inventoryItemId}")
    public ApiResponse<InventoryItem> updateInventoryItem(
            @PathVariable("inventoryItemId") String inventoryItemId,
            @RequestBody InventoryItemUpdateReq req) {

        ApiResponse<InventoryItem> response = new ApiResponse<>();
        try {
            InventoryItem inventoryItem = inventoryItemService.updateInventoryItem(inventoryItemId, req);
            response.setMessage("Cập nhật nguyên liệu thành công");
            response.setResult(inventoryItem);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật nguyên liệu thất bại: " + e.getMessage());
        }
        return response;
    }

    // Danh sách nguyên liệu hết hạn trong 7 ngày tới
    @GetMapping("/expiring-soon")
    public ApiResponse<List<InventoryItem>> getExpiringItems() {
        ApiResponse<List<InventoryItem>> response = new ApiResponse<>();
        try {
            List<InventoryItem> items = inventoryItemService.getExpiringWithin7Days();
            response.setMessage("Danh sách nguyên liệu sắp hết hạn");
            response.setResult(items);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Không thể lấy danh sách nguyên liệu sắp hết hạn: " + e.getMessage());
        }
        return response;
    }

    // Số lượng còn lại của một nguyên liệu
    @GetMapping("/remaining-quantity/{ingredientId}")
    public ApiResponse<Float> getRemainingQuantity(@PathVariable("ingredientId") String ingredientId) {
        ApiResponse<Float> response = new ApiResponse<>();
        try {
            Float remainingQuantity = inventoryItemService.getRemainingQuantityByIngredientId(ingredientId);
            response.setMessage("Số lượng còn lại của nguyên liệu");
            response.setResult(remainingQuantity);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Không thể lấy số lượng còn lại: " + e.getMessage());
        }
        return response;
    }
}
