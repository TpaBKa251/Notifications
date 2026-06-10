package ru.tpu.hostel.notifications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.entity.NotificationType;
import ru.tpu.hostel.notifications.service.NotificationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void createNotificationWithSuccess() throws Exception {
        when(notificationService.createNotification(any(NotificationRequestDto.class)))
                .thenReturn(TestData.notificationResponseDto());

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestData.notificationRequestDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestData.NOTIFICATION_ID.toString()))
                .andExpect(jsonPath("$.userId").value(TestData.USER_ID.toString()))
                .andExpect(jsonPath("$.title").value(TestData.TITLE))
                .andExpect(jsonPath("$.message").value(TestData.MESSAGE));
    }

    @Test
    void createNotificationWhenValidationFails() throws Exception {
        NotificationRequestDto invalid = new NotificationRequestDto(
                null, TestData.TYPE_BALANCE, TestData.TITLE, TestData.MESSAGE);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNotificationReturnsNull() throws Exception {
        mockMvc.perform(get("/notifications/get")
                        .param("type", NotificationType.BALANCE.name())
                        .param("date", "2026-06-09"))
                .andExpect(status().isOk());
    }
}
