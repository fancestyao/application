package study.neo.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;
import study.neo.application.service.interfaces.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Application API")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "Прескоринг данных для расчета условий кредита для пользователя",
            description = "Происходит прескоринг данных LoanApplicationRequestDTO, " +
                    "перенаправляет запрос на MC deal, " +
                    "возвращает лист с 4 LoanOfferDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Прескоринг данных прошел успешно"),
            @ApiResponse(responseCode = "409", description = "Входные данные не прошли прескоринг")
    })
    public ResponseEntity<List<LoanOfferDTO>> prescoreData(@RequestBody @Parameter(description =
            "Входные параметры для расчета условий кредита для пользователя")
                                                     LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("Получен запрос в контроллер на расчет условий кредита с loanApplicationRequestDTO: {}",
                loanApplicationRequestDTO);
        return new ResponseEntity<>(applicationService.prescoreData(loanApplicationRequestDTO), HttpStatus.OK);
    }

    @PutMapping("/offer")
    @Operation(summary = "Обновление истории статуса заявки на кредит",
            description = "Перенаправляет запрос на обновление истории с входным параметром LoanOfferDTO" +
                    " на МС Deal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "409", description = "Неверные начальные данные")
    })
    public void offer(@RequestBody @Parameter(description =
            "Входные параметры в виде рассчитанных условий кредита для пользователя")
                                                           LoanOfferDTO loanOfferDTO) {
        log.info("Получен запрос в контроллер на перенаправление на МС Deal " +
                        "условий кредита с loanOfferDTO: {}", loanOfferDTO);
        applicationService.offer(loanOfferDTO);
    }
}
