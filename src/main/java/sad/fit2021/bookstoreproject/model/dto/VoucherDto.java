package sad.fit2021.bookstoreproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sad.fit2021.bookstoreproject.model.entity.Voucher;

import javax.persistence.Column;
import java.sql.Timestamp;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VoucherDto {
    private Integer voucherId;
    private String code;
    private Integer discountPercent;
    private Timestamp expiredAt;
    private Timestamp createdAt;
    public VoucherDto(Voucher voucher){
        this.voucherId = voucher.getId();
        this.code = voucher.getCode();
        this.discountPercent = voucher.getDiscountPercent();
        this.expiredAt = voucher.getExpiredAt();
        this.createdAt = voucher.getCreatedAt();
    }
}
