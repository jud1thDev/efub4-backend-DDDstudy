package ewhabackendDDDstudy.order.query.dao;

import ewhabackendDDDstudy.order.query.dto.OrderSummary;
import ewhabackendDDDstudy.order.query.dto.OrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OrderSummaryDao extends Repository<OrderSummary, String> {
    List<OrderSummary> findByOrdererId(String ordererId);
    List<OrderSummary> findByOrdererId(String ordererId, Sort sort);
    List<OrderSummary> findByOrdererId(String ordererId, Pageable pageable);

    // 특정 속성으로 조회하는 find메서드는 이름 뒤에 OrderBy를 사용해서 정렬 순서를 지정할 수 있다.
    // OrderBy: 결과를 특정 속성에 따라 정렬하라는 지시어, 여기선 Number
    // Asc: 오름차순 정렬, Desc: 내림차순 정렬
    List<OrderSummary> findByOrdererIdOrderByNumberDesc(String ordererId);
    // => 즉, ordererId를 가진 OrderSummary 객체들을 Number 속성 기준으로 내림차순 정렬하란 의미

    List<OrderSummary> findAll(Specification<OrderSummary> spec);
    List<OrderSummary> findAll(Specification<OrderSummary> spec, Sort sort);
//    Sort 객체 생성
//    Sort sort = Sort.by("number").ascending();
//    List<OrderSummary> results = orderSummaryDao.findByOrderId("user1", sort);

    List<OrderSummary> findAll(Specification<OrderSummary> spec, Pageable pageable);

    Page<OrderSummary> findAll(Pageable pageable);

    @Query("""    
            select new com.myshop.order.query.dto.OrderView(
                o.number, o.state, m.name, m.id, p.name
            )
            from Order o join o.orderLines ol, Member m, Product p
            where o.orderer.memberId.id = :ordererId
            and o.orderer.memberId.id = m.id
            and index(ol) = 0
            and ol.productId.id = p.id
            order by o.number.number desc
            """)
    List<OrderView> findOrderView(String ordererId);
}
