package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Model.DataTransferObject.NewPartnerDTO;
import fullstuck.green.wallet.Model.Entity.TopupPartner;
import fullstuck.green.wallet.Repository.TopupPartnerRepository;
import fullstuck.green.wallet.Service.TopupPartnerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TopupPartnerImpl implements TopupPartnerService {
    private final TopupPartnerRepository topupPartnerRepository;

    @Override
    public void registerNewPartner(NewPartnerDTO req) {
        TopupPartner topupPartner = TopupPartner.builder()
                .name(req.getName())
                .total(new BigDecimal("0.0"))
                .isDeleted(Boolean.FALSE)
                .created_at(Date.from(Instant.now()))
                .updated_at(Date.from(Instant.now()))
                .build();
        topupPartnerRepository.save(topupPartner);
    }

    @Override
    @Transactional
    public void updateBalance(String id, BigDecimal amount) {
        TopupPartner topupPartner = topupPartnerRepository.findById(id).get();
        topupPartner.setTotal(topupPartner.getTotal().add(amount));
        topupPartnerRepository.save(topupPartner);
    }

    @Override
    public void deletePartner(String id) {
        TopupPartner topupPartner = topupPartnerRepository.findById(id).get();
        topupPartner.setDeleted_at(Date.from(Instant.now()));
        topupPartner.setIsDeleted(Boolean.TRUE);
        topupPartnerRepository.save(topupPartner);
    }

    @Override
    public Boolean updatePartner(NewPartnerDTO req) {
        TopupPartner topupPartner = topupPartnerRepository.findById(req.getId()).get();
        if(topupPartner != null){
            topupPartner.setName(req.getName());
            topupPartnerRepository.save(topupPartner);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getPartnerId(String id) {
        return topupPartnerRepository.findById(id).get().getId();
    }

    @Override
    public TopupPartner getPartnerData(String id) {
        return topupPartnerRepository.findById(id).get();
    }

    @Override
    public TopupPartner getPartnerByName(String name) {
        return topupPartnerRepository.findByname(name);
    }
}
