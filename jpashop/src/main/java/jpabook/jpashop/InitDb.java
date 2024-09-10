package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.Domain.*;
import jpabook.jpashop.Domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA","서울","1","1111");
            em.persist(member);
            Book book1 = createBook("JPA1 BOOK",10000,100);
            em.persist(book1);
            Book book2 = createBook("JPA2 BOOk",20000,100);
            em.persist(book2);

            OrderItem orderItem1=OrderItem.createOrderItem(book1,10000,1); //1권 주문
            em.persist(orderItem1);
            OrderItem orderItem2=OrderItem.createOrderItem(book2,20000,2); //2권 주문
            em.persist(orderItem2);
            Order order1 = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order1);
        }

        public void dbInit2() {
            Member member=createMember("userB","광주", "2","2222");
            em.persist(member);
            Book book1= createBook("SPRING1 BOOK",20000,200);
            em.persist(book1);
            Book book2= createBook("SPRING2 BOOK",40000,300);
            em.persist(book2);

            OrderItem orderItem1=OrderItem.createOrderItem(book1,20000,3); //3권 주문
            em.persist(orderItem1);
            OrderItem orderItem2=OrderItem.createOrderItem(book2,40000,4); //4권 주문
            em.persist(orderItem2);
            Order order2 = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order2);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
