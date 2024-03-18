package ewhabackendDDDstudy.domain;

import java.util.List;
import java.util.Set;

public class Category {
//    애그리거트 간 1-N 관계는 Set컬렉션을 통해 표현할 수 있다.
    private Set<Product> products;

//    이 코드를 실제 DBMS와 연동해서 구현하면 Category에 속한 모든 Product를 조회하게 된다.
//    Product 개수가 수백에서 수만 개 정도로 많다면 이 코드를 실행할 때마다 실행 속도가 급격히 느려져 성능에 심각한 문제를 일으킬 것이다.
//    이는 1:N 연관이더라도 N:1로 연관지어 구하면 해결할 수 있다.
    public List<Product> getProducts(int page, int size) {
        List<Product> sortedProducts = sortById(Products);
        return sortedProducts.subList((page - 1) * size, page * size);
    }
}