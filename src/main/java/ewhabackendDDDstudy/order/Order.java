package ewhabackendDDDstudy.order;

import ewhabackendDDDstudy.domain.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Order {
    private OrderNo id;
    private String orderNumber;
    private OrderState state;
    private ShippingInfo shippingInfo;
//    private List<OrderLine> orderLines;
    private OrderLines orderLines;
    private Money totalAmounts;
    private Orderer orderer;

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

    Order order = new Order(orderer, orderLines, shippingInfo, OrderState.PREPARING);

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderState getState() {
        return state;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public OrderLines getOrderLines() {
        return orderLines;
    }

    public Money getTotalAmounts() {
        return totalAmounts;
    }

    public Orderer getOrderer() {
        return orderer;
    }

    public Order getOrder() {
        return order;
    }

    public OrderLines getLines() {
        return lines;
    }

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, OrderState orderState) {
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

//    set메서드는 private로 만들어라. 애그리거트 외부에서 내부 상태를 함부로 바꾸지 못하도록..
    private void setOrderer(Orderer orderer) {
        if (orderer == null) throw new IllegalStateException();
        this.orderer = orderer;
    }

//    OrderLines의 기눙 : changeLines(), getTotalAmounts()
    public void changeOrderLines(List<OrderLine> newLines){
        orderLines.changeOrderLines(newLines);
        this.totalAmounts = orderLines.getTotalAmounts();
    }

//    getOrderlines()를 통해 애그리거트 외부에서 Orderlines의 기능 수행이 가능해진다!
    OrderLines lines = order.getOrderLines();

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
        // stream을 통해 orderLines의 각 요소에 대한 작업 수행
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

