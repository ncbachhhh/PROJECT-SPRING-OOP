package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.INGREDIENT.InventoryItemCreationReq;
import DO_AN.OOP.dto.request.INGREDIENT.InventoryItemUpdateReq;
import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import DO_AN.OOP.repository.INGREDIENT.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryItemService {
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    //    Tạo mới nguyên liệu
    public InventoryItem createInventoryItem(InventoryItemCreationReq req) {
        InventoryItem inventoryItem = InventoryItem.builder()
                .ingredientId(req.getIngredientId())
                .quantity(req.getQuantity())
                .entryDate(LocalDate.now())
                .expirationDate(req.getExpirationDate())
                .userId(req.getUserId())
                .build();
        return inventoryItemRepository.save(inventoryItem);
    }

    //    Cập nhật nguyên liệu
    public InventoryItem updateInventoryItem(String id, InventoryItemUpdateReq req) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nguyên liệu không tồn tại"));

        if (req.getExpirationDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngày hết hạn không được nhỏ hơn ngày hiện tại");
        }

        inventoryItem.setQuantity(req.getQuantity());
        inventoryItem.setExpirationDate(req.getExpirationDate());

        return inventoryItemRepository.save(inventoryItem);

    }

    // Tính toán xem sản phẩm nào hết hạn sau 7 ngày
    public List<InventoryItem> getExpiringWithin7Days() {
        LocalDate today = LocalDate.now();
        LocalDate next7Days = today.plusDays(7);

        return inventoryItemRepository.findAll().stream()
                .filter(item -> item.getExpirationDate() != null)
                .filter(item -> !item.getExpirationDate().isBefore(today))       // expirationDate >= today
                .filter(item -> !item.getExpirationDate().isAfter(next7Days))   // expirationDate <= today + 7
                .collect(Collectors.toList());
    }

    // Tính toán số lượng còn dư theo nguyên liệu
    public Float getRemainingQuantityByIngredientId(String ingredientId) {
        Float totalQuantity = 0f;
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByIngredientId(ingredientId);
        for (InventoryItem item : inventoryItems) {
            if (item.getExpirationDate() != null && item.getExpirationDate().isBefore(LocalDate.now())) {
                continue; // Skip expired items
            }
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}
