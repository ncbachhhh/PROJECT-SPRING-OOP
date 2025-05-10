package DO_AN.OOP.controller;

import DO_AN.OOP.repository.ACCOUNT.AccountRepository;
import DO_AN.OOP.repository.DISHES.DishRepository;
import DO_AN.OOP.repository.DISHES.RecipeRepository;
import DO_AN.OOP.repository.INGREDIENT.IngredientRepository;
import DO_AN.OOP.repository.INGREDIENT.InventoryItemRepository;
import DO_AN.OOP.repository.INVOICE.InvoiceRepository;
import DO_AN.OOP.repository.ORDER.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderRepository orderRepository;

    @DeleteMapping("/reset-db")
    public String resetDatabase() {
        //  Xóa tất cả các bản ghi trong bảng tài khoản
        accountRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng món ăn
        dishRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng công thức
        recipeRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng nguyên liệu
        ingredientRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng nguyên liệu tồn kho
        inventoryItemRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng hóa đơn
        invoiceRepository.deleteAll();
        // Xóa tất cả các bản ghi trong bảng đơn hàng
        orderRepository.deleteAll();

        return "Database reset successfully";
    }
}
