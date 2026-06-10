package ru.tpu.hostel.notifications.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.entity.NotificationType;
import ru.tpu.hostel.notifications.repository.util.RepositoryTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
    }

    private Notification buildNotification(UUID userId, NotificationType type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(type);
        notification.setTitle(TestData.TITLE);
        notification.setMessage(TestData.MESSAGE);
        notification.setCreatedAt(TestData.CREATED_AT);
        return notification;
    }

    @Test
    void saveGeneratesIdWithSuccess() {
        Notification saved = notificationRepository.save(
                buildNotification(TestData.USER_ID, NotificationType.BALANCE));

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findByIdWhenExists() {
        Notification saved = notificationRepository.save(
                buildNotification(TestData.USER_ID, NotificationType.DOCUMENT));

        Optional<Notification> result = notificationRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getType()).isEqualTo(NotificationType.DOCUMENT);
        assertThat(result.get().getUserId()).isEqualTo(TestData.USER_ID);
    }

    @Test
    void findByIdWhenNotExists() {
        Optional<Notification> result = notificationRepository.findById(TestData.NOTIFICATION_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void savePersistsAllFields() {
        Notification saved = notificationRepository.save(
                buildNotification(TestData.USER_ID, NotificationType.KITCHEN_SCHEDULE));

        Notification found = notificationRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getTitle()).isEqualTo(TestData.TITLE);
        assertThat(found.getMessage()).isEqualTo(TestData.MESSAGE);
        assertThat(found.getCreatedAt()).isEqualTo(TestData.CREATED_AT);
        assertThat(found.getSentAt()).isNull();
    }
}
