## 4장 리포지터리와 모델 구현(JPA 중심)
이 장에서는 애그리거트의 논리적인 저장소인 리포지토리의 구현 방법을 알아보자.<br>
<br>
리포지토리가 제공하는 기본 기능
- ID로 애그리거트 조회
- 애그리거트 저장
OrderRepository에서 확인해보자. <br>
애그리거트 루트는 Order이므로 OrderRepository만 만든다. <br>
<br>
스프링 데이터 JPA를 활용해 리포지토리를 구현하는 것이 일반적이다. <br>
스프링 데이터 JPA는 지정된 규칙에 맞게 인터페이스를 정의하면, <br>
리포지터리를 구현한 객체를 알아서 만들어 스프링 빈으로 등록해준다. <br>
OrderRepository에서 확인해보자. <br>
<br>
<br>
### 엔티티와 밸류 기본 매핑 구현
엔티티와 밸류 기본 매핑 구현
- 엔티티 <br>
애그리거트 루트는 엔티티이므로 @Entity로 매핑 설정한다.
- 밸류<br>
밸류는 @Embeddable로 매핑 설정한다. <br>
밸류 타입 프로퍼티는 @Embedded로 매핑 설정한다. <br>
- 기본 생성자v
밸류 객체의 경우 불변 타입이라 생성 시점에 필요한 값을 모두 전달받으므로, <br>
기본 생성자가 따로 필요 없다.
- protected 기본 생성자<br>
하지만, JPA에서 @Entity나 @Embeddable로 클래스를 매핑하려면 기본 생성자를 제공해야 한다. <br>
이런 기술적인 제약으로, 불변 타입은 기본 생성자가 필요 없음에도 불구하고, 기본 생성자를 추가해야 한다.<br>
-> 단, 다른 코드에서 기본 생성자를 사용 못하도록 protected로 선언한다. (물론 같은 패키지에서 작성하는 것은 막기 힘들다)
<br>
<br>
### JPA는 메서드와 필드 두 가지 방식으로 매핑을 처리할 수 있다.(필드방식으로 하자!)
JPA는 메서드와 필드 두 가지 방식으로 매핑을 처리할 수 있다. <br>
1. 메서드 방식 <br>
메서드 방식을 사용하려면 get/set 메서드를 구현해야 한다.
-> set 메서드를 노출하기 때문에, 외부에서 변경할 가능성을 높인다.
-> 필드 방식을 선택해서 불필요한 get/set 메서드를 구현하지 않는 것이 좋다.
```@Entity
   @Table(name = "purchase_order")
   @Access(AccessType.PROPERTY)
   public class Order {

   @Column(name = "state")
   @Enumertated(EnumType.STRING)
   public OrderState getState() {
   return state;
   }

   public void setState(OrderState state) {
   this.state = state;
   }
   }
<br>
2. 필드 방식 <br>

   ``` 
   @Entity
    @Table(name = "purchase_order")
    @Access(AccessType.FIELD)
    public class Order {
    @EmbeddedId
    private OrderNo number;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState state;
}
