package ru.tpu.hostel.notifications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;
import ru.tpu.hostel.notifications.service.TokenService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
@AutoConfigureMockMvc(addFilters = false)
class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TokenService tokenService;

    @Test
    void createTokenWithSuccess() throws Exception {
        when(tokenService.createToken(any(TokenRequestDto.class)))
                .thenReturn(ResponseEntity.ok(TestData.defaultToken()));

        mockMvc.perform(post("/notifications/tokens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.tokenRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    void createTokenWhenValidationFails() throws Exception {
        TokenRequestDto invalid = new TokenRequestDto(null);

        mockMvc.perform(post("/notifications/tokens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}
