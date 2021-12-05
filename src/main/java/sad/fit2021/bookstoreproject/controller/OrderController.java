package sad.fit2021.bookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sad.fit2021.bookstoreproject.model.dto.OrderDto;
import sad.fit2021.bookstoreproject.service.OrderService;

import java.security.Principal;
import java.util.List;
@Controller
@RequestMapping(value = "/user/order")
public class OrderController {
    @Autowired
    private OrderService orderSerice;
    @GetMapping(value = "/order-list")
    public ModelAndView orderList(Principal principal){
        ModelAndView mav = new ModelAndView("order");
        List<OrderDto> orderDtos = orderSerice.getAllOrder("hieunk");
        mav.addObject("orderDtos", orderDtos);
        return mav;
    }
    @GetMapping(value = "/cancel/{orderId}/{bookId}")
    public ModelAndView cancelOrder(@PathVariable(value = "orderId") Integer orderId, @PathVariable(value = "bookId") Integer bookId){
        orderSerice.cancelOrder(orderId, bookId);
        ModelAndView mav = new ModelAndView("order");
        List<OrderDto> orderDtos = orderSerice.getAllOrder("hieunk");
        mav.addObject("orderDtos", orderDtos);
        return mav;
    }
}
