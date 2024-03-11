package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Money;
import ewhabackendDDDstudy.CH1.OrderLine;

import java.util.List;

public class CalculateDiscountService {
//    문제1. CalculateDiscountService 단독 테스트 어려움(ruleEngine도 정상동작해야함)
//    문제2. 구현 방식 변경의 어려움 : Drools에 의존 중이라 drools에서 코드 변경이 일어나면 함께 바꿔줘야함

    private ruleEngine = new DroolsRuleEngine();

    public Money calculateDiscount(List<OrderLine> orderlines, String customerId){
        Customer customer = findCustomer(customerId);

        MutableMoney money = new MutableMoney(0);
        List<?> facts = Arrays.aslist(customer, money);
        facts.addAll(orderLines);
        ruleEngine.evaluate("discountCalculation", facts);
        return money.toimmutableMoney();
    }
}
