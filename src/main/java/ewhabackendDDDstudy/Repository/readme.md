## 4장 리포지터리와 모델 구현(JPA 중심)
이 장에서는 애그리거트의 논리적인 저장소인 리포지토리의 구현 방법을 알아보자.
<br>
리포지토리가 제공하는 기본 기능:
- ID로 애그리거트 조회
- 애그리거트 저장

OrderRepository에서 확인해보자. <br>
애그리거트 루트는 Order이므로 OrderRepository만 만든다. <br>
<br><br>
스프링 데이터 JPA를 활용해 리포지토리를 구현하는 것이 일반적이다.<br>
스프링 데이터 JPA는 지정된 규칙에 맞게 인터페이스를 정의하면, 리포지터리를 구현한 객체를 알아서 만들어 스프링 빈으로 등록해준다.

### 엔티티와 밸류 기본 매핑 구현
엔티티와 밸류 기본 매핑 구현:
- **엔티티**: 애그리거트 루트는 엔티티이므로 `@Entity`로 매핑 설정한다.
- **밸류**: 밸류는 `@Embeddable`로 매핑 설정한다. 밸류 타입 프로퍼티는 `@Embedded`로 매핑 설정한다.
- **기본 생성자**: 밸류 객체의 경우 불변 타입이라 생성 시점에 필요한 값을 모두 전달받으므로, 기본 생성자가 따로 필요 없다.
- **protected 기본 생성자**: 하지만, JPA에서 `@Entity`나 `@Embeddable`로 클래스를 매핑하려면 기본 생성자를 제공해야 한다. 이런 기술적인 제약으로, 불변 타입은 기본 생성자가 필요 없음에도 불구하고, 기본 생성자를 추가해야 한다. -> 단, 다른 코드에서 기본 생성자를 사용 못하도록 `protected`로 선언한다.

### JPA는 메서드와 필드 두 가지 방식으로 매핑을 처리할 수 있다.(필드방식으로 하자!)
JPA는 메서드와 필드 두 가지 방식으로 매핑을 처리할 수 있다.

1. **메서드 방식**: 메서드 방식을 사용하려면 get/set 메서드를 구현해야 한다. <br>
-> set 메서드를 노출하기 때문에, 외부에서 변경할 가능성을 높인다. <br>
-> 필드 방식을 선택해서 불필요한 get/set 메서드를 구현하지 않는 것이 좋다.

```java
@Entity
@Table(name = "purchase_order")
@Access(AccessType.PROPERTY)
public class Order {
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}
```
2. 필드방식
```java
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
```
### AttributeConverter를 이용한 밸류 매핑 처리
- AttributeConverter

밸류를 한 개 컬럼에 매핑해야 하는 경우가 있다. <br>
이 밖에도 도메인의 필드와 DB 컬럼을 따로 관리해주고 싶을 수 있다. <br>
이런 경우 AttributeConverter를 사용하면 좋다. <br>
AttributeConverter는 다음과 같이 도메인 필드와 컬럼 데이터 간의 변환을 처리하기 위한 기능을 정의하고 있다. <br>

```java
public interface AttributeConverter<X, Y> {
    Y convertToDatabaseColumn(X var1);
 
    X convertToEntityAttribute(Y var1);
}
```


AttributeConverter를 구현한 클래스를 보자.

```java
@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }
 
    @Override
    public Money convertToEntityAttribute(Integer value) {
        return value == null ? null : new Money(value);
    }
}
 
```
이렇게 AttrubuteConverter를 구현하고, @Converter(autoApply = true)를 적용하면,Money 타입에 대해 자동으로 컨버터가 적용된다.<br>
autoApply를 false로 지정하면 특정 필드에 직접 컨버터를 지정하면 된다.
```java
public class Order {
    @Convert(converter = MoneyConverter.class)
    @Column(name = "total_amounts")
    private Money totalAmounts;
}
 ```

### 밸류 컬렉션: 별도 테이블 매핑
- 밸류 컬렉션을 별도의 테이블로

한 엔티티는 밸류 컬렉션을 가질 수 있다. <br>
이런 경우 컬렉션을 따로 테이블로 빼는데, 이에 대한 매핑 설정은 다음과 같다.
```java
@Entity
@Table(name = "purchase_order")
@Access(AccessType.FIELD)
public class Order {
    @EmbeddedId
    private OrderNo number;
 
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLines;
}
```
@ElementCollection으로 해당 필드가 컬렉션 객체임을 알려주고, <br>

@CollectionTable을 통해 컬렉션 테이블 이름과, 외부키 세팅을 한다. <br>

@OrderColumn은 List의 순서에 대한 컬럼을 추가하는 애노테이션이다.<br>

<br><br>

### 밸류 컬렉션: 한개 컬럼 매핑
- 밸류 컬렉션을 한개 컬럼으로
  <br>
  밸류 컬렉션을 별도 테이블이 아닌, 한 개 컬럼에 저장해야 할 때가 있다.
  <br>
  ex) 이메일 주소 목록을 Set으로 보관하고, DB에는 한 개 컬럼으로 콤마로 구분해서 저장해야 함.

 <br>

이런 경우 컬렉션을 하나의 밸류 타입으로 묶고, AttributeConvert를 사용한다.
```java
public class EmailSet {
 
    private Set<Email> emails = new HashSet<>();
 
    public EmailSet(Set<Email> emails) {
        this.emails.addAll(emails);
    }
 
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails);
    }
}
 

public class EmailSetConverter implements AttributeConverter<EmailSet, String> {
    @Override
    public String convertToDatabaseColumn(EmailSet attribute) {
        if (attribute == null) return null;
        return attribute.getEmails().stream()
                .map(email -> email.getAddress())
                .collect(Collectors.joining(","));
        
    }
 
    @Override
    public EmailSet convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String[] emails = dbData.split(",");
        Set<Email> emailSet = Arrays.stream(emails)
                .map(value -> new Email(value))
                .collect(Collectors.toSet());
        return new EmailSet(emailSet);
    }
}
``` 

### 밸류를 이용한 ID 매핑
- 식별자를 밸류 타입으로 <br>
  식별자라는 의미를 부각시키기 위해 식별자 자체를 밸류 타입으로 만들 수 있다.<br>
  이때, @EmbeddedId 애너테이션을 사용한다.

```java
@Entity
@Table(name = "purchase_order")
public class Order {
    @EmbeddedId
    private OrderNo number;
}
 

@Embeddable
public class OrderNo implements Serializable {
    @Column(name = "order_number")
    private String number;
    protected OrderNo() {
    }
 
    public OrderNo(String number) {
        this.number = number;
    }
 
    public String getNumber() {
        return number;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNo orderNo = (OrderNo) o;
        return Objects.equals(number, orderNo.number);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
 
    public static OrderNo of(String number) {
        return new OrderNo(number);
    }
}
```
JPA에서 식별자 타입은 Serializable 타입이어야 하므로 식별자로 사용할 밸류 타입은 Serializable 인터페이스를 구현한다.

<br><br>
### 별도 테이블에 저장하는 밸류 매핑
- 엔티티와 테이블은 다르다<br>

애그리거트에서 루트 엔티티 외에 또 다른 엔티티가 있다면 진짜 엔티티인지 의심해봐야 한다.<br>

-> 별도 테이블에 데이터를 저장한다고 해서 엔티티인 것은 아니다.<br>

밸류가 아니라 엔티티인지 확실하다면, 해당 엔티티가 다른 애그리거트는 아닌지 확인해야 한다.

 <br>

- 애그리거트에 속한 객체가 밸류인지, 엔티티인지 구분하는 방법은,

고유 식별자를 갖는지를 확인하는 것이다.

<br>
엔티티는 Article하나이고, ArticleContent 밸류 타입을 다른 테이블과 매핑하기 위한 코드는 다음과 같다.

```java
@Entity
@Table(name = "article")
@SecondaryTable(
        name = "article_content",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @AttributeOverrides({
            @AttributeOverride(
                    name = "content",
                    column = @Column(table = "article_content", name = "content")),
            @AttributeOverride(
                    name = "contentType",
                    column = @Column(table = "article_content", name = "content_type"))
    })
    @Embedded
    private ArticleContent content;
}
```

@SecondaryTable은 밸류를 저장할 테이블을 지정한다.<br>

pkJoinColumns 속성은 밸류 테이블에서 엔티티 테이블로 조인할 때 사용할 컬럼을 지정한다.<br>

-> 이러면 entityManager.find(Article.class, 1L)과 같은 코드로 Article을 조회하면 두 테이블을 조인해서 데이터를 조회한다.<br>


### 밸류 컬렉션을 @Entity로 매핑하기
개념적으로 밸류인데 구현 기술의 한계나 팀 표준으로 인해 @Entity를 사용할 때도 있다. <br>

ex) Product가 여러 Image를 갖는다.
```java
@Entity
@Table(name = "product")
public class Product {
@EmbeddedId
private ProductId id;
private List<Image> images = new ArrayList<>();
}
 ```

여기서 Image는 밸류 타입인데, 요구사항에 따라 InternalImage, ExternalImage 등 다양한 자식 객체를 갖는다.<br>

즉, 상속 구조가 필요하다.<br>

 

- 상속 매핑을 위한 @Entity <br>

JPA에서 밸류 객체의 @Embeddable 타입은 상속 매핑을 지원하지 않는다. <br>

따라서, 밸류 타입에 @Entity를 이용해서 상속 매핑으로 처리해야 한다. <br>
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속을 위한 애노테이션
@DiscriminatorColumn(name = "image_type") // 타입 구분용 컬럼
@Table(name = "image")
public abstract class Image {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "image_id")
private Long id; // 엔티티이기 때문에 식별자를 지정

    @Column(name = "image_path")
    private String path;
 
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
 
    public abstract String getUrl();
 
    public abstract boolean hasThumbnail();
 
    public abstract String getThumbnailUrl();

}
 ```

@Inheritance: 상속 매핑을 위한 애노테이션 <br>

@DiscriminatorColumn: 타입 구분용 컬럼 추가 <br>

@Id: 밸류 타입이지만 @Entity를 설정했기 때문에 식별자를 추가한다. <br>
<br>
 

Image를 상속받은 클래스는 @Entity와 @Discriminator를 사용해서 매핑을 설정한다.
```java
@Entity
@DiscriminatorValue("II")
public class InternalImage extends Image {
protected InternalImage() {
}

    public InternalImage(String path) {
        super(path);
    }
 
    @Override
    public String getUrl() {
        return "/images/original/" + getPath();
    }
 
    @Override
    public boolean hasThumbnail() {
        return true;
    }
 
    @Override
    public String getThumbnailUrl() {
        return "/images/thumbnail/" + getPath();
    }
}


@Entity
@DiscriminatorValue("EI")
public class ExternalImage extends Image {
protected ExternalImage() {
}

    public ExternalImage(String path) {
        super(path);
    }
 
    @Override
    public String getUrl() {
        return getPath();
    }
 
    @Override
    public boolean hasThumbnail() {
        return false;
    }
 
    @Override
    public String getThumbnailUrl() {
        return null;
    }
}
``` 

- @OneToMany 매핑<br>

Image가 @Entity이므로 Product는 다음과 같이 @OneToMany 매핑을 사용한다.
```java
@Entity
@Table(name = "product")
public class Product {
@EmbeddedId
private ProductId id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OrderColumn(name = "list_idx")
    private List<Image> images = new ArrayList<>();
    
    public void changeImages(List<Image> newImages) {
        images.clear();
        images.addAll(newImages);
    }
}
``` 

이 때 같은 애그리게잇인 Product와 Image의 생명주기를 같게 하기 위해, cascade와 orphanRemoval 설정을 함께한다.
 

- 코드 유지 보수 vs 성능 <br>

Product의 changeImages() 메서드는 Image가 밸류이기 때문에, 전체를 지우고 다시 새로운 이미지를 추가한다. <br>

이 때, 하이버네이트의 동작 방식은 Image의 타입에 따라 다르다. <br>

- - @Entity: 1번의 전체 대상 select 쿼리 + n번의 delete 쿼리 <br>
밸류 컬렉션에 상속으로 인한 다형성이 중요<br>
- - @Embeddable: 1번의 delete 쿼리 <br>
현재 서비스 특성상 성능 이슈가 크티티컬<br>
-> 밸류 컬렉션이 엔티티인 것보다는 밸류 객체인 것이 성능상 유리하다. <br>

```java
@Embeddable
public class Image {

    @Column(name = "image_type")
    private String imageType;
    @Column(name = "image_path")
    private String imagePth;
 
    // 성능을 위해 다형성을 포기하고 if-else로 구현
    public boolean hasThumbnail() {
        if (imageType.equals("II")) {
            return true;
        } else {
            return false;
        } 
    }
}
 ```

요구사항을 구현하는 데 집합 연관을 사용하는 것이 유리하다면 ID 참조를 이용한 단방향 집합 연관을 적용해 볼 수 있다.
```java
@Entity
@Table(name = "product")
public class Product {
@EmbeddedId
private ProductId id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds;
}
```


### 애그리거트 로딩 전략
- 즉시 로딩 vs 지연 로딩<br>

애그리거트는 속한 객체가 모두 모여야 완전한 하나가 된다.<br>

-> 애그리거트 루트를 로딩하면 루트에 속한 모든 객체가 완전한 상태여야 한다.<br>


<br>
이를 위해 애그리거트 루트에 매핑된 @Entity나 @Embeddable 객체에 대해 즉시 로딩을 걸어줘야 한다.

다만, 즉시 로딩의 단점은<br>

여러 테이블을 한 번에 가져오기 위해 카타시안 조인을 사용하고 이는 쿼리 결과에 중복을 발생시킨다.<br>

-> 물론 실제 메모리에는 애그리거트 루트 1개로 잘 변환해주지만, 쿼리로 인한 조회량 자체가 많다는 것이 문제다.<br>

 <br><br>

애그리거트가 완전해야 하는 이유는 두 가지다.<br>

1. 상태를 변경하는 기능을 실행할 때, 애그리거트 상태가 완전해야 한다.<br>
2. 표현 영역에서 애그리거트 상태 정보를 보여줘야 한다.<br>
2번의 경우 조회 전용 기능을 사용하면 되고,<br>
1번의 경우 꼭 즉시 로딩이 아닌 지연 로딩(실제 해당 객체에 접근할 때 쿼리를 날림)을 사용해도 되기 때문에,<br>
각 애그리거트에 맞게 즉시 로딩과 지연 로딩을 선택하는 것이 좋다.<br> 

 

 

### 애그리거트의 영속성 전파
- 애그리거트는 완전한 상태<br>

애그리거트가 완전하다는 것은 조회 뿐만이 아니다.<br>

- 저장: 애그리거트 루트만 저장하면 안되고 애그리거트에 속한 모든 객체를 저장해야 한다. <br>
- 삭제: 애그리거트 루트 뿐만 아니라 애그리거트에 속한 모든 객체를 삭제해야 한다. <br>
@Embeddable 매핑은 함께 저장되고 삭제되므로 cascade 속성을 추가로 설정하지 않아도 된다. <br>
@Entity 매핑은 cascade 속성을 사용해서 저장, 삭제 시에 함께 처리되도록 설정해야 한다. <br>
```java
@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, // cascade 설정
orphanRemoval = true, fetch = FetchType.LAZY)
@JoinColumn(name = "product_id")
@OrderColumn(name = "list_idx")
private List<Image> images = new ArrayList<>();
 ```

 

### 식별자 생성 기능
- 사용자가 직접 생성 <br>

이메일 주소 같이 사용자가 직접 식별자를 입력하는 경우, 도메인 영역에 식별자 생성 기능을 구현할 필요 없다.

- 도메인 로직으로 생성 <br>

식별자 생성 규칙이 있다면 별도 서비스로 식별자 생성 기능을 분리해야 한다. <br>

```java
public class OrderIdService {

    public OrderId createId(UserId userId) {
        if (userId == null) {
            throw new IllegalArgumentException("invalid userid: " + userId);
        }
        return new OrderId(userId.toString + "-" + timestamp())
    }
 
    private String timestamp() {
        return Long.toString(System.currentTimeMillis());
    }
}
 ```

- DB를 이용한 일련번호 사용<br>

DB 자동 증가 컬럼을 식별자로 사용하면 식별자 매핑에서 @GeneratedValue를 사용한다.
```java
@Entity
@Table(name = "article")
public class Article {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
}

```
 

### 도메인 구현과 DIP
- DIP 위반 <br>
이 장에서 구현한 리포지터리는 DIP 원칙을 어기고 있다.<br>

엔티티는 @Entity, @Table, @Id, @Column 등 JPA에 특화된 애노테이션을 사용한다.<br>
리포지토리는 스프링 데이터 JPA의 Repository 인터페이스를 상속하고 있다.<br>
-> 즉, 도메인 영역에서 구현 기술인 인프라에 의존하고 있다.<br>
<br><br>
 

이런 부분을 방지하려면, 도메인 영역에서는 JPA에 대한 의존을 없애고,<br>

JpaArticleRepository, JpaArticle등 인프라를 위한 객체를 생성해야 한다.<br>

 

- DIP 적용 이유<br>

DIP를 적용하는 주된 이유는 저수준 구현이 변경되더라도 고수준이 영향을 받지 않도록 하기 위함이다.<br>

하지만 리포지터리와 도메인 모델의 구현 기술은 경험상 JPA, RDB에서 마이바티스, 몽고DB로 변경한 적이 거의 없다.<br>

-> 이렇게 변경이 거의 없는 상황에서 변경을 미리 대비하는 것은 과할 수 있다.<br>

-> DIP를 완벽하게 지키면 좋겠지만 개발의 편의성과 구조적인 유연함을 어느 정도 유지하는 것이 합리적인 선택이라고 본다.<br>
