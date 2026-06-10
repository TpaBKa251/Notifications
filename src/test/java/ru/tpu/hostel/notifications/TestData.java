package ru.tpu.hostel.notifications;

import ru.tpu.hostel.notifications.dto.request.NotificationRequestDto;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.entity.NotificationType;
import ru.tpu.hostel.notifications.entity.Token;

import java.time.LocalDateTime;
import java.util.UUID;

public final class TestData {

    public static final UUID USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final UUID OTHER_USER_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final UUID NOTIFICATION_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    public static final UUID TOKEN_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static final NotificationType TYPE_BALANCE = NotificationType.BALANCE;

    public static final NotificationType TYPE_DOCUMENT = NotificationType.DOCUMENT;

    public static final String TITLE = "Заголовок уведомления";

    public static final String MESSAGE = "Текст уведомления";

    public static final String DEVICE_TOKEN = "device-token-value";

    public static final String OTHER_DEVICE_TOKEN = "other-device-token-value";

    public static final String FIREBASE_MESSAGE_ID = "projects/test/messages/1234567890";

    public static final LocalDateTime CREATED_AT = LocalDateTime.of(2026, 6, 9, 12, 0, 0);

    public static final LocalDateTime SENT_AT = LocalDateTime.of(2026, 6, 9, 12, 5, 0);

    private TestData() {
    }

    public static NotificationRequestDto notificationRequestDto() {
        return new NotificationRequestDto(USER_ID, TYPE_BALANCE, TITLE, MESSAGE);
    }

    public static NotificationRequestDto notificationRequestDto(UUID userId) {
        return new NotificationRequestDto(userId, TYPE_BALANCE, TITLE, MESSAGE);
    }

    public static Notification newNotification(UUID id, UUID userId) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setUserId(userId);
        notification.setType(TYPE_BALANCE);
        notification.setTitle(TITLE);
        notification.setMessage(MESSAGE);
        notification.setCreatedAt(CREATED_AT);
        return notification;
    }

    public static Notification defaultNotification() {
        return newNotification(NOTIFICATION_ID, USER_ID);
    }

    public static NotificationResponseDto notificationResponseDto() {
        return new NotificationResponseDto(
                NOTIFICATION_ID, USER_ID, TYPE_BALANCE, TITLE, MESSAGE, CREATED_AT);
    }

    public static Token newToken(UUID id, UUID userId, String tokenValue) {
        Token token = new Token();
        token.setId(id);
        token.setUserId(userId);
        token.setToken(tokenValue);
        return token;
    }

    public static Token defaultToken() {
        return newToken(TOKEN_ID, USER_ID, DEVICE_TOKEN);
    }

    public static TokenRequestDto tokenRequestDto() {
        return new TokenRequestDto(DEVICE_TOKEN);
    }

    public static TokenRequestDto tokenRequestDto(String tokenValue) {
        return new TokenRequestDto(tokenValue);
    }
}
