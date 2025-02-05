package ru.tpu.hostel.notifications.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.enums.NotificationType;
import ru.tpu.hostel.notifications.mapper.NotificationMapper;
import ru.tpu.hostel.notifications.repository.NotificationRepository;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.NotificationService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TokenRepository tokenRepository;

    @Override
    public NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto) {
        // TODO Добавить исключение
        String token;
        try {
            token = tokenRepository.findById(notificationRequestDto.userId()).orElseThrow().getToken();
        } catch (Exception e) {
            Notification faildNotification = notificationRepository
                    .save(NotificationMapper.mapNotificationRequestToNotification(notificationRequestDto));

            return NotificationMapper.mapNotificationToNotificationResponseDto(faildNotification);
        }

        Notification notification = NotificationMapper.mapNotificationRequestToNotification(notificationRequestDto);

        com.google.firebase.messaging.Notification notificationFireBase = com.google.firebase.messaging.Notification
                .builder()
                .setTitle(notification.getTitle())
                .setBody(notification.getMessage())
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notificationFireBase) // Добавить уведомление
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            notification.setSentAt(LocalDateTime.now(ZoneId.of("UTC")));
            notificationRepository.save(notification);
            log.info(
                    "Уведомление отправлено для {}: {}. {}",
                    notificationRequestDto.userId(),
                    notification.getTitle(),
                    notification.getMessage()
            );
        } catch (FirebaseMessagingException e) {
            notificationRepository.save(notification);
            log.error(
                    "Не удалось отправить уведомление для {} по причине: {}",
                    notificationRequestDto.userId(),
                    e.getMessage());
        }

        return NotificationMapper.mapNotificationToNotificationResponseDto(notification);
    }
}
