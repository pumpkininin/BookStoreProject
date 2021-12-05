package sad.fit2021.bookstoreproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.exception.OutOfStockException;
import sad.fit2021.bookstoreproject.model.composite.CartLineId;
import sad.fit2021.bookstoreproject.model.dto.CartDto;
import sad.fit2021.bookstoreproject.model.entity.*;
import sad.fit2021.bookstoreproject.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartLineRepository cartLineRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<CartDto> getCartItems(String username) {
        Integer userId = userRepository.findUserByUsername(username).getUserId();
        Cart cart = cartRepository.findAllByUser_UserId(userId);
        List<CartLine> cartLines = cart.getCartLines();
        List<CartDto> cartDtos = new ArrayList<>();
        for (CartLine cl :
                cartLines) {
            cartDtos.add(new CartDto(cl, userId));
        }
        return cartDtos;
    }



    public void addToCart(Integer bookId, String username) throws OutOfStockException {
        User user = userRepository.findUserByUsername(username);
        Cart cart = cartRepository.findAllByUser_UserId(user.getUserId());
        Book selectedBook = bookRepository.findBookByBookId(bookId);
        if (selectedBook.getInStock() >= 1) {
            System.out.println("enough");
            CartLine cartLine = cartLineRepository.findCartLinesById(new CartLineId(cart.getId(), bookId));
            if (cartLine != null) {
                cartLine.setQuantity(cartLine.getQuantity() + 1);
                cartLineRepository.save(cartLine);
            } else {
                cartLine = new CartLine();
                cartLine.setBook(bookRepository.findBookByBookId(bookId));
                cartLine.setQuantity(1);
                cartLine.setCart(cartRepository.findAllByUser_UserId(user.getUserId()));
                cartLineRepository.save(cartLine);
            }
            selectedBook.setInStock(selectedBook.getInStock() -1);
            bookRepository.save(selectedBook);

        } else {
            throw new OutOfStockException("Your book you require is not available");
        }
    }

    public CartDto increaseQuantityByOne(Integer bookId, String username){
        User user = userRepository.findUserByUsername(username);
        Cart cart = cartRepository.findAllByUser_UserId(user.getUserId());
        Book selectedBook = bookRepository.findBookByBookId(bookId);
        CartLine cartLine = cartLineRepository.findCartLinesById(new CartLineId(cart.getId(), bookId));
        if(selectedBook.getInStock() >=1 ){
            cartLine.setQuantity(cartLine.getQuantity() + 1);
            cartLineRepository.save(cartLine);
            selectedBook.setInStock(selectedBook.getInStock() - 1);
            bookRepository.save(selectedBook);
            return new CartDto(cartLine, bookId);
        }else{
            throw new IllegalArgumentException("Book with id: " + bookId + " do not have enough in stock");
        }
    }

    public CartDto decreaseQuantityByOne(Integer bookId, String username) {
        User user = userRepository.findUserByUsername(username);
        Cart cart = cartRepository.findAllByUser_UserId(user.getUserId());
        Book selectedBook = bookRepository.findBookByBookId(bookId);
        CartLine cartLine = cartLineRepository.findCartLinesById(new CartLineId(cart.getId(), bookId));
        if(cartLine.getQuantity() == 1){
            cartLineRepository.delete(cartLine);
            return null;
        }else{
            cartLine.setQuantity(cartLine.getQuantity() - 1);
            cartLineRepository.save(cartLine);
            return new CartDto(cartLine, bookId);
        }
    }
    public int getTotalPrice(String username){
        User user = userRepository.findUserByUsername(username);
        List<CartLine> cartLines = cartLineRepository.findAllByCart(cartRepository.findAllByUser(user));
        int totalPrice = 0;
        for(CartLine c : cartLines){
            totalPrice += c.getSubTotal();
        }
        return totalPrice;
    }
    public void checkOut(String username, Integer id){
        User user = userRepository.findUserByUsername(username);
        Cart cart = cartRepository.findAllByUser_UserId(user.getUserId());
        List<CartLine> cartLines = cart.getCartLines();
        Voucher voucher = voucherRepository.getOne(id);
        Order order = new Order();
        order.setUser(user);
        order.setStatus("Pending");
        order.setTotalPrice((long) getTotalPrice(username));
        order.setPaymentMode("COD");
        order.setVoucher(voucher);
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
        List<OrderLine> orderLines = new ArrayList<>();
        for(CartLine c: cartLines){
            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setBook(c.getBook());
            orderLine.setQuantity(c.getQuantity());
            orderLine.setUnitPrice((long) c.getSubTotal()*voucher.getDiscountPercent()/100);
            orderLineRepository.save(orderLine);
        }

    }
}
