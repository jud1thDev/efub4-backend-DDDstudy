package CH1;
// 1장. 도메인 모델 시작하기

//도메인 : 소프트웨어로 해결하고자 하는 문제 영역

public class Order {
    private OrderState state;
    private ShippingInfo shippingInfo;

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (!isShippingInfoChangeable() ) {
            throw new IllegalStateException("can't change shipping in " + state);
        } this.shippingInfo = newShippingInfo;
    }

    private boolean isShippingInfoChangeable() {
        return state == OrderState.PAYMENT_WAITING ||
                state == OrderState.PREPARING;
    }

}

