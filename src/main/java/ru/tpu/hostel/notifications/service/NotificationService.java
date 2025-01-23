package ru.tpu.hostel.notifications.service;

import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;

public interface NotificationService {

    NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto);
}
