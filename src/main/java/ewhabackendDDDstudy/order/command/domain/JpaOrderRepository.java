package ewhabackendDDDstudy.order.command.domain;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

// 왜 평소처럼 인터페이스 리포지토리가 아닌 클래스로 했는가?
// 구현 설명을 위해 여기선 이렇게 코드작성했지만 보통은 JPA 사용해 인터페이스 리포지토리를 구현한다.

// JpaOrderRepository 클래스는 OrderRepository에서 선언된 메서드(findBy, save)를 모두 구현해야한다.
@Repository
public class JpaOrderRepository implements OrderRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Order findById(OrderNo no) {
        return em.find(Order.class, no);
    }

    @Override
    public void save(Order order) {
        em.persist(order);
    }

//    ID 외의 조건으로 애그리거트를 조회할 때 JPA의 Criteria나 JPQL을 사용한다.
    @Override
    public List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize){
        TypedQuery<Order> query = em.createQuery("select o from Order o" +
                "where o.orderer.memberId.id = :ordererId"+
                "order by o.number.number desc", Order.class);
        query.setParameter("ordererId", ordererId);
        query.setFirstResult(startRow);
        query.setMaxResults(fetchSize);

        return query.getResultList();
    }

//    @Override
//    public void delete(Order order){
//        em.remove(order);
//    }

}
