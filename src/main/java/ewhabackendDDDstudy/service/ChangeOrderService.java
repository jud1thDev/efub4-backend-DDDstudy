package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.Repository.OrderRepository;
import ewhabackendDDDstudy.order.command.domain.ShippingInfo;
import ewhabackendDDDstudy.order.command.domain.Order;
import ewhabackendDDDstudy.order.command.domain.OrderNo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ChangeOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void changeShippingInfo(OrderNo no, ShippingInfo newShippingInfo){
        Optional<Order> orderOpt = orderRepository.findById(no);
        Order order = orderOpt.orElseThrow(()->new OrderNotFoundException("e"));
        order.changeShippingInfo(newShippingInfo);
    }

}
