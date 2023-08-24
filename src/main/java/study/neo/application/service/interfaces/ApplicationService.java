package study.neo.application.service.interfaces;

import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;

import java.util.List;

public interface ApplicationService {
    List<LoanOfferDTO> prescoreData(LoanApplicationRequestDTO loanApplicationRequestDTO);
    void offer(LoanOfferDTO loanOfferDTO);
}
