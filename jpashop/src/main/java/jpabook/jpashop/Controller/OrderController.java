package jpabook.jpashop.Controller;

import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Domain.Order;
import jpabook.jpashop.Domain.item.Item;
import jpabook.jpashop.Repository.OrderSearch;
import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    //orderForm 생성
    @GetMapping("/order")
    public String createForm(Model model)
    {
        List<Member> members=memberService.findAllmembers();
        List<Item> items=itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }
    //@RequestParam : 넘겨 받은 {값}
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId ,
                        @RequestParam("itemId") Long itemId ,
                        @RequestParam("count") int count)
    {
        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model)
    {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList"; //orderList 파일(view)에 뿌림
    }
    //주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId)
    {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}