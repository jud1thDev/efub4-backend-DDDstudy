package CH1;

import java.util.List;

// 1장. 도메인 모델 시작하기

//도메인 : 소프트웨어로 해결하고자 하는 문제 영역
//식별자를 통해서 엔티티 객체들 간의 구별이 가능하다!

public class Order {
    private String orderNumber;
    @Override
    public boolean equals(Object obj) {
        // 현재 객체와 비교 대상 객체가 동일한 객체인지 확인
        if ( this == obj) return true;
        // 비교 대상 객체가 null이면, 두 객체는 동일할 수 없으므로 false
        if ( obj == null) return false;
        // 비교 대상 객체의 클래스 타입이 Order 클래스가 아니면, 두 객체는 동일할 수 없으므로 false
        if (obj.getClass() != Order.class) return false;
        // 위의 조건들을 모두 통과했다면, obj를 Order 타입으로 강제 형변환
        Order other = (Order)obj;
        // 현재 객체의 orderNumber 필드가 null이면, 이 객체는 유효한 비교 대상이 아니므로 false
        if(this.orderNumber == null) return false;
        // 현재 객체의 orderNumber와 비교 대상 객체의 orderNumber를 비교하여 같으면 true, 다르면 false를 반환
        return this.orderNumber.equals(other.orderNumber);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // orderNumber가 null이면 0을 사용, 값있으면 hashcode 사용
        result = prime * result + ((orderNumber == null) ?0 : orderNumber.hashCode());
        return result;
    }

    private OrderState state;
    private ShippingInfo shippingInfo;
    private List<OrderLine> orderLines;
    private Money totalAmounts;

    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

//    5. 주문할 때 배송지 정보를 반드시 지정해야 한다.
    public void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null)
            throw new IllegalStateException("no ShippingInfo");
        this.shippingInfo = shippingInfo;
    }

//    1. 최소 한 종류 이상의 상품을 주문해야 한다.
    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()){
            throw new IllegalStateException("no OrderLine");
        }
    }

//    3. 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
    private void calculateTotalAmounts() {
        // stream을 통해 orderLines의 각 요소에 대한 작업 수행
        int sum = orderLines.stream()
                .mapToInt(x -> x.getAmounts())
                .sum();
        this.totalAmounts = new Money(sum);
    }


    public void changeShipped(){
    }


//     7. 출고를 하면 배송지를 변경할 수 없다.
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYetShipped();
        setShippingInfo(newShippingInfo);
    }

//    8. 출고 전에 주문을 취소할 수 있다.
    public void cancel(){
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    private void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING)
            throw new IllegalThreadStateException("already shipped");
    }

    public void completePayment(){
    }

}

