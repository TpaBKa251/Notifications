package ru.tpu.hostel.notifications.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.entity.NotificationType;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Notification}
 */
public record NotificationRequestDto(
        @NotNull(message = "ID пользователя не может быть пустым")
        UUID userId,

        @NotNull(message = "Тип уведомления не может быть пустым")
        NotificationType type,

        @NotNull(message = "Заголовок не может быть пустым")
        @NotBlank(message = "Заголовок не может быть пустым")
        @Length(message = "Заголовок должен быть в пределах от 1 до 100 символов", min = 1, max = 100)
        String title,

        @NotNull(message = "Заголовок не может быть пустым")
        @NotBlank(message = "Заголовок не может быть пустым")
        @Length(message = "Заголовок должен быть в пределах от 1 до 1000 символов", min = 1, max = 1000)
        String message
) implements Serializable {
}