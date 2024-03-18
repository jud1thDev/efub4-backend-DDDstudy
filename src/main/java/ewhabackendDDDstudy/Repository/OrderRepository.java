package ewhabackendDDDstudy.Repository;

import ewhabackendDDDstudy.order.Order;
import ewhabackendDDDstudy.order.OrderNo;

import java.util.Optional;

public interface OrderRepository {
    Order findById(OrderNo no);

//    null 사용하고 싶지 않다면..
//    Optional<Order> findById(OrderNo no);
    void save(Order order);

}
