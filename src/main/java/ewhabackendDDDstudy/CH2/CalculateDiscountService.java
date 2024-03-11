package ewhabackendDDDstudy.CH2;

import ewhabackendDDDstudy.CH1.Customer;
import ewhabackendDDDstudy.CH1.Money;
import ewhabackendDDDstudy.CH1.OrderLine;

import java.util.List;

public class CalculateDiscountService {
//    문제1. CalculateDiscountService 단독 테스트 어려움(ruleEngine도 정상동작해야함)
//    문제2. 구현 방식 변경의 어려움 : Drools에 의존 중이라 drools에서 코드 변경이 일어나면 함께 바꿔줘야함


//    CalculateDiscountService : 고수준 모듈(가격 할인 계산 기능 -> 고객 정보 구하기, 룰 실행하기)
//    고수쥰 모듈 : 의미 있는 단일 기능을 제공하는 모듈. 여러 하위 기능 요구
//    저소준 모듈 : 하위 기능을 제공하는 모듈

//    위의 문제 발생의 원인은 고수준 모듈이 저소준 모듈을 사용하면서 발생한다.
//    -> (sol)저소준 모듈이 고소준 모듈에 의존하게 바꾸자!
//    -> 어떻게? 추상화한 인터페이스를 통해

    private RuleDiscounter ruleDiscounter;

    public CalculateDiscountService(RuleDiscounter ruleDiscounter) {
        this.ruleDiscounter = ruleDiscounter;
    }

//    이렇게 코드를 작성하면, CalculateDiscountServicesms Drools에 의존하지 않음.
    public Money calculateDiscount(List<OrderLine> orderLines, String customerId){
        Customer customer = findCustomer(customerId);
        return ruleDiscounter.applyRules(customer, orderLines);
    }

    private Customer findCustomer(String customerId) {
    }
}
