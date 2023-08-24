package study.neo.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.neo.application.dto.LoanApplicationRequestDTO;
import study.neo.application.dto.LoanOfferDTO;
import study.neo.application.service.interfaces.ApplicationService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест контроллера ApplicationController.")
public class ApplicationControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private ApplicationController applicationController;
    @Mock
    private ApplicationService applicationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
    }

    @Test
    @DisplayName("Тест метода prescore ApplicationController.")
    public void testPrescore() throws Exception {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO();
        List<LoanOfferDTO> responseDTO = new ArrayList<>();
        when(applicationService.prescoreData(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/application/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));

        verify(applicationService).prescoreData(requestDTO);
    }

    @Test
    @DisplayName("Тест метода offer ApplicationController.")
    public void testOffer() throws Exception {
        LoanOfferDTO requestDTO = new LoanOfferDTO();

        mockMvc.perform(put("/application/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(applicationService).offer(requestDTO);
    }
}
