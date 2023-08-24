package study.neo.application.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;
import study.neo.application.service.interfaces.ApplicationService;
import study.neo.application.service.interfaces.FeignDealClient;
import study.neo.application.validation.LoanApplicationRequestValidation;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final FeignDealClient feignDealClient;
    private final LoanApplicationRequestValidation loanApplicationRequestValidation;

    @Override
    public List<LoanOfferDTO> prescoreData(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("Запрос с контроллера на расчет предложений по кредиту передан в ApplicationService.");
        loanApplicationRequestValidation.callAllValidations(loanApplicationRequestDTO);
        log.info("Валидация loanApplicationDTO: {} успешна пройдена.", loanApplicationRequestDTO);
        log.info("Отправляем Post-запрос на МС Deal");
        return feignDealClient.sendToApplication(loanApplicationRequestDTO);
    }

    @Override
    public void offer(LoanOfferDTO loanOfferDTO) {
        log.info("Отправляем Post-запрос на МС Deal");
        feignDealClient.sendToOffer(loanOfferDTO);
    }
}
