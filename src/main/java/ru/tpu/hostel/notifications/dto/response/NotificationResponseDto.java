package ru.tpu.hostel.notifications.dto.response;

import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.enums.NotificationType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Notification}
 */
public record NotificationResponseDto(
        UUID id,
        UUID userId,
        NotificationType type,
        String title,
        String message,
        LocalDateTime createdAt
) implements Serializable {
}