package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Order;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;


// 2장. 아키텍처 개요
// 인프라스트럭쳐 영역 : 구현 기술에 대한 것을 다룸 - RDMBMS연동, REST API 호출 등

@Service
public class CancelOrderService {
    private DroolsRules ruleEngine;

    // 응용 서비스는 트랜잭션(도메인의 상태 변경)을 관리한다.
    @Transactional
    public void cancelOrder(String orderId){
        Order order = findOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        order.cancel();
    }

    private Order findOrderById(String orderId) {
    }
}
