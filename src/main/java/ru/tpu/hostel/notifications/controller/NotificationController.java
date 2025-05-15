package ru.tpu.hostel.notifications.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.NotificationType;
import ru.tpu.hostel.notifications.service.NotificationService;

import java.time.LocalDate;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponseDto createNotification(@Valid @RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.createNotification(notificationRequestDto);
    }

    @GetMapping("get")
    public NotificationResponseDto getNotification(
            @RequestParam(name = "type") NotificationType type,
            @RequestParam(name = "date") LocalDate date
            ) {
        return null;

    }
}
