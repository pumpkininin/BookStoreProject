package sad.fit2021.bookstoreproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.model.dto.VoucherDto;
import sad.fit2021.bookstoreproject.model.entity.Voucher;
import sad.fit2021.bookstoreproject.repository.VoucherRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<VoucherDto> getAllVoucher() {
        List<Voucher> vouchers =  voucherRepository.findAll();
        List<VoucherDto> voucherDtos = new ArrayList<>();
        for (Voucher voucher : vouchers){
            voucherDtos.add(new VoucherDto(voucher));
        }
        return voucherDtos;
    }
}
