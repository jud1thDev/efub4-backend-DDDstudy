package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Money;
import ewhabackendDDDstudy.CH1.Order;
import ewhabackendDDDstudy.CH1.OrderLine;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;


// 2장. 아키텍처 개요
// 인프라스트럭쳐 영역 : 구현 기술에 대한 것을 다룸 - RDMBMS연동, REST API 호출 등

@Service
public class CancelOrderService {
    private DroolsRuleEngine ruleEngine;


//    문제1. CalculateDiscountService 단독 테스트 어려움(ruleEngine도 정상동작해야함)
//    문제2. 구현 방식 변경의 어려움 : Drools에 의존 중이라 drools에서 코드 변경이 일어나면 함께 바꿔줘야함
    public CalculateDiscountService() {
        ruleEngine = new DroolsRuleEngine();
    }

    public Money calculateDiscount(List<OrderLine> orderlines, String customerId){
        Customer customer = findCustomer(customerId);

        MutableMoney money = new MutableMoney(0);
        List<?> facts = Arrays.aslist(customer, money);
        facts.addAll(orderLines);
        ruleEngine.evaluate("discountCalculation", facts);
        return money.toimmutableMoney();
    }


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
