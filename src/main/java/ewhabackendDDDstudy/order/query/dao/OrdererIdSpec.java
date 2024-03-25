package ewhabackendDDDstudy.order.query.dao;

import ewhabackendDDDstudy.order.query.dto.OrderSummary;
import ewhabackendDDDstudy.order.query.dto.OrderSummary_;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// OrderSummary에 대한 검색 조건을 표현하자.
public class OrdererIdSpec implements Specification<OrderSummary> {

    private String ordererId;

    public OrdererIdSpec(String ordererId) {
        this.ordererId = ordererId;
    }

// OrderSummary 엔티티의 ordererId 속성이 생성자로 전달받은 orderId와 동일한지 비교
    @Override
    public Predicate toPredicate(Root<OrderSummary> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        return cb.equal(root.get(OrderSummary_.ordererId), ordererId);
    }
}
