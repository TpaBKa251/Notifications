package ru.tpu.hostel.notifications.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;

@Component
@UtilityClass
public class NotificationMapper {

    public static Notification mapNotificationRequestToNotification(NotificationRequestDto notificationRequestDto) {
        Notification notification = new Notification();
        notification.setType(notificationRequestDto.type());
        notification.setUserId(notificationRequestDto.userId());
        notification.setTitle(notificationRequestDto.title());
        notification.setMessage(notificationRequestDto.message());

        return notification;
    }

    public static NotificationResponseDto mapNotificationToNotificationResponseDto(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getUserId(),
                notification.getType(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getCreatedAt()
        );
    }
}
