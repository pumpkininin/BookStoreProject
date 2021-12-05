package sad.fit2021.bookstoreproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sad.fit2021.bookstoreproject.model.composite.OrderLineId;
import sad.fit2021.bookstoreproject.model.dto.OrderDto;
import sad.fit2021.bookstoreproject.model.entity.Order;
import sad.fit2021.bookstoreproject.model.entity.OrderLine;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.repository.OrderLineRepository;
import sad.fit2021.bookstoreproject.repository.OrderRepository;
import sad.fit2021.bookstoreproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    public List<OrderDto> getAllOrder(String username) {
        User user =  userRepository.findUserByUsername(username);
        List<Order> orders = orderRepository.findAllByUser_UserId(user.getUserId());
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order o: orders){
            List<OrderLine> orderLines = o.getOrderLines();
            for(OrderLine ol : orderLines){
                orderDtos.add(new OrderDto( ol));
            }
        }
        return orderDtos;
    }

    public void cancelOrder(Integer orderId, Integer bookId) {
        orderLineRepository.deleteById(new OrderLineId(orderId, bookId));
    }
}
