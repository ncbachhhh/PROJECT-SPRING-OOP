package DO_AN.OOP.controller;

import DO_AN.OOP.dto.request.OrderCreationReq;
import DO_AN.OOP.dto.request.OrderUpdateStatusReq;
import DO_AN.OOP.dto.response.ApiResponse;
import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

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

    @PostMapping("/update-status/{orderId}")
    public ApiResponse<Order> updateOrderStatus(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderUpdateStatusReq req) {

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
}
