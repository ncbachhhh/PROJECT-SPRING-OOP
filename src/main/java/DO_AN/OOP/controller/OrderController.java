package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.ORDER.*;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.dto.response.OrderResponse;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.model.ORDER.OrderStatus;
import DO_AN.OOP.service.DishService;
import DO_AN.OOP.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;

    // Tạo đơn hàng
    @PostMapping("/create")
    public ApiResponse<Order> createOrder(@RequestBody OrderCreationReq req) {
        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order order = orderService.createOrder(req);
            response.setMessage("Tạo đơn hàng thành công");
            response.setResult(order);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Tạo đơn hàng thất bại: " + e.getMessage());
        }

        return response;
    }

    //    Sửa trạng thái đơn hàng
    @PostMapping("/update-status/{orderId}")
    public ApiResponse<Order> updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody OrderUpdateStatusReq req) {

        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, req);
            response.setMessage("Cập nhật trạng thái đơn hàng thành công");
            response.setResult(updatedOrder);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật trạng thái thất bại: " + e.getMessage());
        }

        return response;
    }

    //    Thêm món ăn
    @PostMapping("/add-dishes/{orderId}")
    public ApiResponse<Order> addDishesToOrder(@PathVariable String orderId, @RequestBody OrderUpdateDishesReq req) {
        ApiResponse<Order> response = new ApiResponse<>();
        try {
            Order updated = orderService.addOrUpdateDishesInOrder(orderId, req);
            response.setMessage("Thêm/ cập nhật món ăn vào đơn hàng thành công");
            response.setResult(updated);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Cập nhật thất bại: " + e.getMessage());
        }
        return response;
    }

    // Xóa món ăn khỏi Order
    @PostMapping("/remove-dish/{orderId}")
    public ApiResponse<Order> removeDishFromOrder(@PathVariable String orderId, @RequestBody OrderRemoveDishReq req) {

        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order updatedOrder = orderService.removeDishFromOrder(orderId, req);
            response.setMessage("Xóa món khỏi đơn hàng thành công");
            response.setResult(updatedOrder);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Xóa món thất bại: " + e.getMessage());
        }

        return response;
    }

    //    Giảm số lượng món ăn
    @PostMapping("/reduce-quantity/{orderId}")
    public ApiResponse<Order> reduceDishQuantity(@PathVariable String orderId, @RequestBody OrderReduceDishQuantityReq req) {
        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order updatedOrder = orderService.reduceDishQuantityInOrder(orderId, req);
            response.setMessage("Giảm số lượng món thành công");
            response.setResult(updatedOrder);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Giảm số lượng thất bại: " + e.getMessage());
        }

        return response;
    }

    //     Hiển thị đơn hàng của user
    @GetMapping("/get/{userId}")
    public ApiResponse<Order> getOrderByUserId(@PathVariable String userId) {
        ApiResponse<Order> response = new ApiResponse<>();

        try {
            Order orders = orderService.getOrderByUserId(userId);
            response.setMessage("Lấy đơn hàng thành công");
            response.setResult(orders);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy đơn hàng thất bại: " + e.getMessage());
        }

        return response;
    }

    //  Lấy đơn hàng theo trạng thái
    @GetMapping("/status/{status}")
    public ApiResponse<List<Order>> getOrderByStatus(@PathVariable OrderStatus status) {
        ApiResponse<List<Order>> response = new ApiResponse<>();

        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            response.setMessage("Lấy danh sách đơn hàng theo trạng thái thành công");
            response.setResult(orders);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy danh sách đơn hàng thất bại: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/get-by-id/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable String orderId) {
        ApiResponse<OrderResponse> response = new ApiResponse<>();

        try {
            OrderResponse orderResponse = orderService.getOrderById(orderId);
            response.setMessage("Lấy đơn hàng thành công");
            response.setResult(orderResponse);
        } catch (RuntimeException e) {
            response.setCode(400);
            response.setMessage("Lấy đơn hàng thất bại: " + e.getMessage());
        }

        return response;
    }
}
