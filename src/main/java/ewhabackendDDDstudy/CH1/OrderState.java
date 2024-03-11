package ewhabackendDDDstudy.CH1;

// STEP1, 2, 3, ... 이런 이름 대신 아래처럼 이름짓는게 좋다. = 도메인용어를 적절히 활용하자.
public enum OrderState {
    PAYMENT_WAITING, PREPARING, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED;
}
