package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.Entity.TransDetail;
import fullstuck.green.wallet.Repository.TransDetailRepository;
import fullstuck.green.wallet.Service.TransDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransDetailImpl implements TransDetailService {
    private final TransDetailRepository transDetailRepository;

    @Override
    public void saveTransactionDetail(TransDetail transDetail) {
        this.transDetailRepository.save(transDetail);
    }
}