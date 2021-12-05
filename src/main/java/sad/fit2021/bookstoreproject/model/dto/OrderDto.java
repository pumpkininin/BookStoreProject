package sad.fit2021.bookstoreproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.Order;
import sad.fit2021.bookstoreproject.model.entity.OrderLine;
import sad.fit2021.bookstoreproject.model.entity.Voucher;

import java.sql.Timestamp;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private Integer orderId;
    private Integer userId;
    private Book book;
    private int quantity;
    private double subTotal;
    private Timestamp createAt;
    private Voucher voucher;
    private String status;
    public OrderDto( OrderLine order){
        this.orderId = order.getOrder().getId();
        this.userId = order.getOrder().getUser().getUserId();
        this.book = order.getBook();
        this.status = order.getOrder().getStatus();
        this.createAt = order.getOrder().getCreatedAt();
        this.voucher = order.getOrder().getVoucher();
        this.subTotal = order.getUnitPrice();
        this.quantity = order.getQuantity();
    }
}
