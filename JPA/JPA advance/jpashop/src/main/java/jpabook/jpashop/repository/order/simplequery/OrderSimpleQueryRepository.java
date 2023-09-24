package jpabook.jpashop.repository.order.simplequery;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    /*
    화면에 dependency가 큰 로직 (아래처럼 한 화면에서만 쓸 수 있게 구체적으로 작성하고 다른 코드에서 재 사용성이 떨어지는 코드)
    은 Query repository처럼 따로 분리해서 작성하는게 유지보수 측면에서 좋음
     */

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
            "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)"
                + " from Order o" +
                " join o.member m " +
                " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList(); //엔티티로 dto를 반환하려면 new를 꼭 써줘야함
    }
}
