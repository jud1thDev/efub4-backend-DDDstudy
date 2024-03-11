package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {

    @Transactional
    public void cancelOrder(String orderId){
        Order order = findOrderById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
    }
}
