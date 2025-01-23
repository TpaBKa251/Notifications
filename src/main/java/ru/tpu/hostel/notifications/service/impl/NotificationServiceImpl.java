package ru.tpu.hostel.notifications.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.mapper.NotificationMapper;
import ru.tpu.hostel.notifications.repository.NotificationRepository;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.NotificationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TokenRepository tokenRepository;

    @Override
    public NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto) {
        // TODO Добавить исключение
        String token = tokenRepository.findById(notificationRequestDto.userId()).orElseThrow().getToken();

        Notification notification = NotificationMapper.mapNotificationRequestToNotification(notificationRequestDto);

        Message message = Message.builder()
                .setToken(token)
                .putData("title", notification.getTitle())
                .putData("body", notification.getMessage())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);
        } catch (FirebaseMessagingException e) {
            notificationRepository.save(notification);
            throw new RuntimeException(e);
        }

        return NotificationMapper.mapNotificationToNotificationResponseDto(notification);
    }
}
