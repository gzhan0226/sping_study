package jpabook.jpashop.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        // CriteriaBuilder를 생성합니다.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // CriteriaQuery를 생성하고 반환타입을 Order.class로 지정합니다.
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);

        // 시작점을 설정합니다. Order 엔티티를 대상으로 쿼리를 시작
        Root<Order> o = cq.from(Order.class);

        // 회원과의 조인을 설정.
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        // 쿼리 조건(명제)을 담을 리스트를 생성합니다.
        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색 조건이 주어진 경우에 대한 처리입니다.
        if (orderSearch.getOrderStatus() != null) {
            // 주문 상태에 대한 Predicate를 생성하여 criteria 리스트에 추가합니다.
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status); //상태 추가
        }

        // 회원 이름 검색 조건이 주어진 경우에 대한 처리입니다.
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            // 회원 이름에 대한 Predicate를 생성하여 criteria 리스트에 추가합니다.
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        // 생성된 Predicate들을 AND 조건으로 연결하여 where 절을 구성합니다.
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));

        // TypedQuery를 생성하고 최대 결과 수를 1000건으로 설정합니다.
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        // 쿼리를 실행하고 결과를 반환합니다.
        return query.getResultList();
    }

}
