package ewhabackendDDDstudy.Repository;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import ewhabackendDDDstudy.domain.*;
import ewhabackendDDDstudy.order.*;

// 왜 평소처럼 인터페이스 리포지토리가 아닌 클래스로 했는가?
// 구현 설명을 위해 여기선 이렇게 코드작성했지만 보통은 JPA 사용해 인터페이스 리포지토리를 구현한다.

// JpaOrderRepository 클래스는 OrderRepository에서 선언된 메서드(findBy, save)를 모두 구현해야한다.
@Repository
public class JpaOrderRepository implements OrderRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(OrderNo id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

}
