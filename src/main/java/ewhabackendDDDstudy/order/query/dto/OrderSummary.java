package ewhabackendDDDstudy.order.query.dto;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

// @Subselect를 이용해서 @Entity를 매핑한 예
// @Immutable, @Subselect, @Synchronize는 하이버네이트 전용 어노테이션이다.
// 이 태그를 사용하면 테이블이 아닌 쿼리 결과를 @Entity로 매핑할 수 있다.
@Entity
@Immutable
@Subselect(
        """
        select o.order_number as number,
        o.version,
        o.orderer_id,
        o.orderer_name,
        o.total_amounts,
        o.receiver_name,
        o.state,
        o.order_date,
        p.product_id,
        p.name as product_name
        from purchase_order o inner join order_line ol
            on o.order_number = ol.order_number
            cross join product p
        where
        ol.line_idx = 0
        and ol.product_id = p.product_id"""
)
@Synchronize({"purchase_order", "order_line", "product"})
public class OrderSummary {
    @Id
    private String number;
    private long version;
    @Column(name = "orderer_id")
    private String ordererId;
    @Column(name = "orderer_name")
    private String ordererName;
    @Column(name = "total_amounts")
    private int totalAmounts;
    @Column(name = "receiver_name")
    private String receiverName;
    private String state;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "product_name")
    private String productName;

    protected OrderSummary() {
    }

    public String getNumber() {
        return number;
    }

    public long getVersion() {
        return version;
    }

    public String getOrdererId() {
        return ordererId;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public int getTotalAmounts() {
        return totalAmounts;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getState() {
        return state;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}