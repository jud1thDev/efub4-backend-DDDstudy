package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.Repository.OrderRepository;
import ewhabackendDDDstudy.domain.ShippingInfo;
import ewhabackendDDDstudy.order.Order;
import ewhabackendDDDstudy.order.OrderNo;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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
