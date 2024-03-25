package ewhabackendDDDstudy.order.command.domain;

import ewhabackendDDDstudy.order.command.domain.Order;
import ewhabackendDDDstudy.order.command.domain.OrderNo;
import org.springframework.data.repository.Repository;

import java.util.Optional;

// 지정된 규칙은 다음과 같다.
// org.springframework.data.repository.Repository<T, ID> 인터페이스 상속
// T는 엔티티 타입, ID는 식별자 타입을 지정

// Repository가 아닌 JpaRepository를 정의하면
// findById(), save()와 같은 기본 메서드들은 따로 작성하지 않아도 알아서 정의해준다.
public interface OrderRepository extends Repository<Order, OrderNo> {
    Optional<Order> findById(OrderNo id);
    void save(Order order);
}
