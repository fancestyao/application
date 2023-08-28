package study.neo.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;
import study.neo.application.service.classes.ApplicationServiceImpl;
import study.neo.application.service.interfaces.FeignDealClient;
import study.neo.application.validation.LoanApplicationRequestValidation;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса ApplicationServiceImpl.")
public class ApplicationServiceImplTest {
    @Mock
    private LoanApplicationRequestValidation loanApplicationRequestValidation;
    @Mock
    private FeignDealClient feignDealClient;
    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Test
    @DisplayName("Тестирование метода configureCalculation.")
    public void whenPrescoreLoanApplicationRequestDTO_thenReturnListOfLoanOfferDtoViaFeignClient() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO();
        loanApplicationRequestDTO.setFirstName("firstName");
        List<LoanOfferDTO> listToReturn = new ArrayList<>();
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(1L);
        listToReturn.add(loanOfferDTO);

        when(feignDealClient.sendToApplication(any(LoanApplicationRequestDTO.class))).thenReturn(listToReturn);

        applicationService.prescoreData(loanApplicationRequestDTO);

        verify(feignDealClient, times(1))
                .sendToApplication(any(LoanApplicationRequestDTO.class));
        verify(loanApplicationRequestValidation, times(1))
                .callAllValidations(any(LoanApplicationRequestDTO.class));
    }

    @Test
    @DisplayName("Тестирование метода configureCalculation.")
    public void feignDealClientSendToOfferTest() {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(1L);

        applicationService.offer(loanOfferDTO);

        verify(feignDealClient, times(1))
                .sendToOffer(any(LoanOfferDTO.class));
    }
}
