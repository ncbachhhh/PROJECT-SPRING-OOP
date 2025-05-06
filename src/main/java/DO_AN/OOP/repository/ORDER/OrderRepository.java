package DO_AN.OOP.repository.ORDER;

import DO_AN.OOP.model.ORDER.Order;
import DO_AN.OOP.model.ORDER.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // Lấy danh sách đơn hàng theo trạng thái
    List<Order> findByStatus(OrderStatus status);

    // Lấy đơn hàng của một user cụ thể
    List<Order> findByUserId(String userId);

    // Lấy đơn hàng theo khoảng thời gian
    List<Order> findByDateBetween(LocalDateTime from, LocalDateTime to);

    // Lấy đơn hàng của user theo trạng thái
    List<Order> findByUserIdAndStatus(String userId, OrderStatus status);
}