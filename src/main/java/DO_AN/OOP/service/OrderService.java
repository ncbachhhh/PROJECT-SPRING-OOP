package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.*;
import DO_AN.OOP.model.DISHES.Dish;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.model.ORDER.OrderItem;
import DO_AN.OOP.model.ORDER.OrderStatus;
import DO_AN.OOP.repository.DISHES.DishRepository;
import DO_AN.OOP.repository.ORDER.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

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

    // Cập nhật trạng thái đơn hàng
    public Order updateOrderStatus(String orderId, OrderUpdateStatusReq req) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));

        order.setStatus(req.getStatus());

        return orderRepository.save(order);
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


}
