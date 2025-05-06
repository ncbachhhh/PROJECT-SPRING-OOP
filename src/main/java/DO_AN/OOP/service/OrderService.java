package DO_AN.OOP.service;

import DO_AN.OOP.dto.request.OrderCreationReq;
import DO_AN.OOP.dto.request.OrderUpdateStatusReq;
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
}
