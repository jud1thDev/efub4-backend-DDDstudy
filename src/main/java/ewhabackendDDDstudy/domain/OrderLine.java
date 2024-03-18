package ewhabackendDDDstudy.domain;

// 주문항목
public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        // money객체가 불변이 아니였더라면,
        // 생성자를 데이터 복사한 새로운 객체를 생성하는 방식으로 작성해야한다.
//        this.price = new Money(price.getValue());
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    //    4. 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다.
    private int calculateAmounts() {
        return price*quantity;
    }

    public int getAmounts(){
        return 0;
    }

}
