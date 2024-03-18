package ewhabackendDDDstudy.domain;

// 이전 코드에선 Orderline의 price, amounts가 int를 사용하고 있었다.
// 돈을 의미하는 Money 타입을 만들어 코드 이해를 더 명확히 돕자.
// 정수 타입 연산 -? 돈 계산 으로의 코드의미 변경
public class Money {
    private int value;

    public Money(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

// 밸류 타입 사용 시 장점 : 해당 타입을 위한 기능 추가
    public Money add(Money money) {
        return new Money(this.value + money.value);
    }

    public Money multiply(int multiplier) {
        return new Money(value * multiplier);
    }
}
