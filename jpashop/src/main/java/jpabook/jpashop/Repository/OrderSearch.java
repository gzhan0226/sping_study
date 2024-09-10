package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    //주문 검색...회원명, 회원상태를 통해 검색
    private String memberName;
    private OrderStatus orderStatus;
}