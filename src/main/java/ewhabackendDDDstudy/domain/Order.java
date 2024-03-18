package ewhabackendDDDstudy.domain;

import java.util.List;

// 3장. 애그리거트
// 애그리거트는 복잡한 모델을 관리하는 기준을 준다.
// 애그리거트에 속한 구성요소는 보통 라이프사이클을 함께한다.
// 언뜻 생각하면, prooduct 하위에 review가 존재한다 생각할 수 있지만 이 둘의 "생성, 변경시기"는 다르므로 이 둘은 다른 애그리거트에 속한다.
// 실무에서 2개 이상의 엔티티를 가지는 애그리거트는 드물다.

public class Order {
//    private OrderNo id;
    private String orderNumber;
    @Override
    public boolean equals(Object obj) {
        if ( this == obj) return true;
        if ( obj == null) return false;
        if (obj.getClass() != Order.class) return false;
        Order other = (Order)obj;
        if(this.orderNumber == null) return false;
        return this.orderNumber.equals(other.orderNumber);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderNumber == null) ?0 : orderNumber.hashCode());
        return result;
    }

    private OrderState state;
    private ShippingInfo shippingInfo;
    private List<OrderLine> orderLines;
    private Money totalAmounts;
    private Orderer orderer;

    Order order = new Order(orderer, orderLines, shippingInfo, OrderState.PREPARING);

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfom, OrderState orderState) {
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderer(Orderer orderer) {
        if (orderer == null) throw new IllegalStateException("no order");
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

//    5. 주문할 때 배송지 정보를 반드시 지정해야 한다.
    public void setShippingInfo(ShippingInfo newShipping) {
        if (shippingInfo == null)
            throw new IllegalStateException("no ShippingInfo");
        this.shippingInfo = shippingInfo;
    }

    public void setOrderState(OrderState state) {
        this.state = state;
    }

    //    1. 최소 한 종류 이상의 상품을 주문해야 한다.
    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()){
            throw new IllegalStateException("no OrderLine");
        }
    }

//    3. 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
    private void calculateTotalAmounts() {
        // stream을 통해 orderLines의 각 요소에 대한 작업 수행v
        this.totalAmounts = new Money(orderLines.stream()
                .mapToInt(x -> x.getAmounts())
                .sum());
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

