package study.neo.application.service.interfaces;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;

import java.util.List;

@FeignClient(name = "deal", url = "${feign-client.url}")
public interface FeignDealClient {
    @PostMapping("/deal/application")
    List<LoanOfferDTO> sendToApplication(@RequestBody @Parameter(description =
            "Входные параметры для расчета условий кредита для пользователя")
                                         LoanApplicationRequestDTO loanApplicationRequestDTO);
    @PutMapping ("/deal/offer")
    void sendToOffer(@RequestBody @Parameter(description = "Входные параметры в виде " +
            "рассчитанных условий кредита для пользователя") LoanOfferDTO loanOfferDTO);
}