package DO_AN.OOP.service;

import DO_AN.OOP.dto.DishDTO;
import DO_AN.OOP.dto.request.ORDER.*;
import DO_AN.OOP.dto.response.OrderResponse;
import DO_AN.OOP.model.DISHES.Dish;
import DO_AN.OOP.model.INGREDIENT.Ingredient;
import DO_AN.OOP.model.INGREDIENT.InventoryItem;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.model.ORDER.OrderItem;
import DO_AN.OOP.model.ORDER.OrderStatus;
import DO_AN.OOP.repository.DISHES.DishRepository;
import DO_AN.OOP.repository.DISHES.RecipeRepository;
import DO_AN.OOP.repository.INGREDIENT.IngredientRepository;
import DO_AN.OOP.repository.INGREDIENT.InventoryItemRepository;
import DO_AN.OOP.repository.ORDER.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    // Tạo mới đơn hàng
    public Order createOrder(OrderCreationReq req) {
        // Map OrderItemReq -> OrderItem
        List<OrderItem> orderItems = req.getDishes().stream().map(itemReq -> {
            return OrderItem.builder()
                    .dishId(itemReq.getDishId())
                    .quantity(itemReq.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        // Tính tổng giá tiền
        float totalPrice = 0f;

        for (OrderItem item : orderItems) {
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với ID: " + item.getDishId()));

            totalPrice += dish.getPrice() * item.getQuantity();
        }

        // Tạo order
        Order order = Order.builder()
                .status(OrderStatus.CHO_XAC_NHAN)
                .totalPrice(totalPrice)
                .date(LocalDateTime.now())
                .note(req.getNote())
                .userId(req.getUserId())
                .dishes(orderItems)
                .build();

        return orderRepository.save(order);
    }

    //    Câp nhật trạng thái đơn hàng
    public Order updateOrderStatus(String orderId, OrderUpdateStatusReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        order.setStatus(req.getStatus());

        // Nếu đơn hàng chuyển sang trạng thái hoàn thành, trừ nguyên liệu
        if (req.getStatus() == OrderStatus.DA_HOAN_THANH) {
            deductIngredientsFromInventory(order);
        }

        return orderRepository.save(order);
    }

    // Phương thức trừ nguyên liệu khi đơn hàng hoàn thành
    private void deductIngredientsFromInventory(Order order) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        // Lấy danh sách món ăn trong đơn hàng
        List<OrderItem> orderItems = order.getDishes();

        for (OrderItem orderItem : orderItems) {
            // Lấy thông tin món ăn
            Dish dish = dishRepository.findById(orderItem.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn: " + orderItem.getDishId()));

            // Lấy công thức của món ăn
            recipeRepository.findById(dish.getRecipeId())
                    .ifPresent(recipe -> {
                        // Duyệt qua từng nguyên liệu trong công thức
                        recipe.getIngredients().forEach(recipeIngredient -> {
                            // Tính tổng lượng nguyên liệu cần trừ
                            float totalNeeded = recipeIngredient.getQuantity() * orderItem.getQuantity();

                            // Lấy danh sách các lô nguyên liệu trong kho (sắp xếp theo hạn dùng)
                            List<InventoryItem> inventoryItems = inventoryItemRepository
                                    .findByIngredientIdAndQuantityGreaterThanAndExpirationDateAfterOrderByExpirationDateAsc(
                                            recipeIngredient.getIngredientId(),
                                            0f,
                                            localDate);

                            List<InventoryItem> updatedItems = new ArrayList<>();

                            // Trừ nguyên liệu từ các lô
                            for (InventoryItem item : inventoryItems) {
                                if (totalNeeded <= 0) break;

                                if (item.getQuantity() >= totalNeeded) {
                                    // Nếu lô hàng đủ số lượng cần trừ
                                    item.setQuantity(item.getQuantity() - totalNeeded);
                                    totalNeeded = 0;
                                } else {
                                    // Nếu lô hàng không đủ, trừ hết lô này và chuyển sang lô khác
                                    totalNeeded -= item.getQuantity();
                                    item.setQuantity(0f);
                                }
                                updatedItems.add(item);
                            }

                            // Lưu lại các thay đổi trong kho
                            inventoryItemRepository.saveAll(updatedItems);

                            // Kiểm tra nếu không đủ nguyên liệu
                            if (totalNeeded > 0) {
                                throw new RuntimeException("Không đủ nguyên liệu " +
                                        ingredientRepository.findById(recipeIngredient.getIngredientId())
                                                .map(Ingredient::getName)
                                                .orElse("Không tìm thấy nguyên liệu") +
                                        " để hoàn thành đơn hàng");
                            }
                        });
                    });
        }
    }

    //    Update món ăn
    public Order addOrUpdateDishesInOrder(String orderId, OrderUpdateDishesReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        List<OrderItem> currentItems = order.getDishes(); // danh sách hiện tại

        for (OrderItemReq newItem : req.getDishes()) {
            boolean found = false;

            for (OrderItem existingItem : currentItems) {
                if (existingItem.getDishId().equals(newItem.getDishId())) {
                    // Nếu món đã có thì cộng dồn số lượng
                    existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Nếu là món mới thì thêm vào danh sách
                currentItems.add(OrderItem.builder()
                        .dishId(newItem.getDishId())
                        .quantity(newItem.getQuantity())
                        .build());
            }
        }

        // Tính lại tổng tiền
        float totalPrice = 0f;
        for (OrderItem item : currentItems) {
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn: " + item.getDishId()));

            totalPrice += dish.getPrice() * item.getQuantity();
        }

        order.setDishes(currentItems);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    //   Xóa món ăn khỏi Order
    public Order removeDishFromOrder(String orderId, OrderRemoveDishReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        List<OrderItem> items = order.getDishes();

        boolean removed = items.removeIf(item -> item.getDishId().equals(req.getDishId()));

        if (!removed) {
            throw new RuntimeException("Món ăn không tồn tại trong đơn hàng");
        }

        // Tính lại totalPrice
        float totalPrice = 0f;
        for (OrderItem item : items) {
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn: " + item.getDishId()));
            totalPrice += dish.getPrice() * item.getQuantity();
        }

        order.setDishes(items);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    //    Giảm số lượng món ăn
    public Order reduceDishQuantityInOrder(String orderId, OrderReduceDishQuantityReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        List<OrderItem> items = order.getDishes();

        boolean updated = false;

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            if (item.getDishId().equals(req.getDishId())) {
                int newQty = item.getQuantity() - req.getAmount();

                if (newQty <= 0) {
                    items.remove(i);
                } else {
                    item.setQuantity(newQty);
                }

                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new RuntimeException("Món ăn không tồn tại trong đơn hàng");
        }

        // Tính lại totalPrice
        float totalPrice = 0f;
        for (OrderItem item : items) {
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món: " + item.getDishId()));
            totalPrice += dish.getPrice() * item.getQuantity();
        }

        order.setDishes(items);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    //     Hiển thị đơn hàng của user
    public Order getOrderByUserId(String userId) {
        return orderRepository.findTopByUserIdOrderByDateDesc(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng cho user: " + userId));
    }

    //    Lấy đơn hàng theo trạng thái
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException(orderId));

        List<DishDTO> dishes = order.getDishes().stream().map(orderDish -> {
            Dish dish = dishRepository.findById(orderDish.getDishId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với ID: " + orderDish.getDishId()));

            return new DishDTO(dish.getName(), orderDish.getQuantity());
        }).collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getDate(),
                order.getNote(),
                dishes,
                order.getUserId(),
                order.getStaffId()
        );
    }
}
