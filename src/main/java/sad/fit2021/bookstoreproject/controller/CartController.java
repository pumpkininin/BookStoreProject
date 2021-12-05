package sad.fit2021.bookstoreproject.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sad.fit2021.bookstoreproject.exception.OutOfStockException;
import sad.fit2021.bookstoreproject.model.dto.CartDto;
import sad.fit2021.bookstoreproject.service.CartService;
import sad.fit2021.bookstoreproject.service.VoucherService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private VoucherService voucherService;
    @GetMapping(value = "/cart-list")
    public ModelAndView getAll(Principal principal) {
        ModelAndView mav = new ModelAndView("cart");
        mav.addObject("cartDtos", cartService.getCartItems("hieunk"));
        mav.addObject("totalPrice", cartService.getTotalPrice("hieunk"));
        mav.addObject("count", cartService.getCartItems("hieunk").size());
        mav.addObject("vouchers", voucherService.getAllVoucher());
        return mav;
    }

    @GetMapping(value = "/add-to-cart/{bookId}")
    public String addToCart(@PathVariable(value = "bookId") Integer bookId, Model model, Principal principal){
        try{
            cartService.addToCart(bookId, "hieunk");
            System.out.println("htttt");
            model.addAttribute("outofStock", "");
        }catch (OutOfStockException e){
            model.addAttribute("outofStock", "Book you require is out of stock");
        }
        List<CartDto> carts =  cartService.getCartItems("hieunk");
        model.addAttribute("carts", carts);
        model.addAttribute("totalPrice", cartService.getTotalPrice("hieunk"));
        return "redirect:/user/cart/cart-list";
    }
    @GetMapping(value = "/add/{bookId}")
    public String addQuantity(@PathVariable(value = "bookId") Integer bookId, Principal principal, Model model){

        System.out.println(bookId);
        cartService.increaseQuantityByOne(bookId, "hieunk");
        List<CartDto> carts =  cartService.getCartItems("hieunk");
        model.addAttribute("carts", carts);
        model.addAttribute("totalPrice", cartService.getTotalPrice("hieunk"));
        model.addAttribute("count", cartService.getCartItems("hieunk").size());
        return "redirect:/user/cart/cart-list";
    }
    @GetMapping(value = "/sub/{bookId}")
    public String subtractQuantity(@PathVariable(value = "bookId") Integer bookId, Principal principal, Model model){
        cartService.decreaseQuantityByOne(bookId, "hieunk");
        List<CartDto> carts =  cartService.getCartItems("hieunk");
        model.addAttribute("carts", carts);
        model.addAttribute("totalPrice", cartService.getTotalPrice("hieunk"));
        model.addAttribute("count", cartService.getCartItems("hieunk").size());
        return "redirect:/user/cart/cart-list";
    }
    @GetMapping(value = "/checkout")
    public String checkout(Principal principal, Model model, @RequestParam(value = "voucher") Integer id){
        cartService.checkOut("hieunk", id);
        return "redirect:/user/order/order-list";
    }
}
