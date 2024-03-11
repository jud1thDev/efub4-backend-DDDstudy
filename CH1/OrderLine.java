package CH1;

// 주문항목
public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;

    public OrderLine(Product product, int price, int quantity, int amounts) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = amounts;
    }

//    4. 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
    private int calculateAmounts() {
        return price*quantity;
    }

    public int getAmounts(){
        return 0;
    }

}
