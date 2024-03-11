package CH1;
// 1장. 도메인 모델 시작하기

//도메인 : 소프트웨어로 해결하고자 하는 문제 영역

import java.util.List;

public class Order {
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
        if (!isShippingInfoChangeable() ) {
            throw new IllegalStateException("can't change shipping in " + state);
        } this.shippingInfo = newShippingInfo;
    }
    private boolean isShippingInfoChangeable() {
        return state == OrderState.PAYMENT_WAITING ||
                state == OrderState.PREPARING;
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

