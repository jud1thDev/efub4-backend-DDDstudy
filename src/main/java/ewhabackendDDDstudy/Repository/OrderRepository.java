package ewhabackendDDDstudy.Repository;

import ewhabackendDDDstudy.order.Order;
import ewhabackendDDDstudy.order.OrderNo;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order findById(OrderNo no);

//    null 사용하고 싶지 않다면..
//    Optional<Order> findById(OrderNo no);

//    특정 ID가 주문한 Order 리스트 찾기
//    List가 findBy메서드의 리턴타입
    List<Order> findByOrdererId(String OrderId, int startRow, int size);

    void save(Order order);

//    애그리거트 삭제 기능도 지원하긴 하지만
//    실무에서 관리자가 삭제한 데이터까지 조회해야하는 경우가 많기 때문에 DB에서 삭제하지않고 일정기간 보관한다.
//    public void delete(Order order);
}
