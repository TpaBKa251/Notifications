package ru.tpu.hostel.notifications.mapper;

import org.junit.jupiter.api.Test;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    @Test
    void mapNotificationRequestToNotificationWithSuccess() {
        Notification notification = NotificationMapper.mapNotificationRequestToNotification(
                TestData.notificationRequestDto());

        assertThat(notification.getUserId()).isEqualTo(TestData.USER_ID);
        assertThat(notification.getType()).isEqualTo(TestData.TYPE_BALANCE);
        assertThat(notification.getTitle()).isEqualTo(TestData.TITLE);
        assertThat(notification.getMessage()).isEqualTo(TestData.MESSAGE);
    }

    @Test
    void mapNotificationToNotificationResponseDtoWithSuccess() {
        Notification notification = TestData.defaultNotification();

        NotificationResponseDto result = NotificationMapper.mapNotificationToNotificationResponseDto(notification);

        assertThat(result.id()).isEqualTo(TestData.NOTIFICATION_ID);
        assertThat(result.userId()).isEqualTo(TestData.USER_ID);
        assertThat(result.type()).isEqualTo(TestData.TYPE_BALANCE);
        assertThat(result.title()).isEqualTo(TestData.TITLE);
        assertThat(result.message()).isEqualTo(TestData.MESSAGE);
        assertThat(result.createdAt()).isEqualTo(TestData.CREATED_AT);
    }
}
