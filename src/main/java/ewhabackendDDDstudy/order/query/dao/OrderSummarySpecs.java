package ewhabackendDDDstudy.order.query.dao;

import ewhabackendDDDstudy.order.query.dto.OrderSummary;
import ewhabackendDDDstudy.order.query.dto.OrderSummary_;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;

public class OrderSummarySpecs {
    public static Specification<OrderSummary> ordererId(String ordererId) {
        return (Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.<String>get("ordererId"), ordererId);
    }

    public static Specification<OrderSummary> orderDateBetween(
            LocalDateTime from, LocalDateTime to) {
        return (Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.between(root.get(OrderSummary_.orderDate), from, to);
    }
}