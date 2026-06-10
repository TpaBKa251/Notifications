package ru.tpu.hostel.notifications.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tpu.hostel.internal.utils.TimeUtil;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.dto.response.NotificationResponseDto;
import ru.tpu.hostel.notifications.entity.Notification;
import ru.tpu.hostel.notifications.repository.NotificationRepository;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.impl.NotificationServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void createNotificationWithSuccess() {
        when(tokenRepository.findByUserId(TestData.USER_ID))
                .thenReturn(List.of(TestData.defaultToken()));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FirebaseMessaging firebaseMessaging = mock(FirebaseMessaging.class);

        try (MockedStatic<FirebaseMessaging> firebaseMock = mockStatic(FirebaseMessaging.class);
             MockedStatic<TimeUtil> timeMock = mockStatic(TimeUtil.class)) {
            firebaseMock.when(FirebaseMessaging::getInstance).thenReturn(firebaseMessaging);
            timeMock.when(TimeUtil::now).thenReturn(TestData.SENT_AT);

            NotificationResponseDto result = notificationService.createNotification(
                    TestData.notificationRequestDto());

            assertThat(result.userId()).isEqualTo(TestData.USER_ID);
            assertThat(result.title()).isEqualTo(TestData.TITLE);
            assertThat(result.message()).isEqualTo(TestData.MESSAGE);
            verify(notificationRepository).save(any(Notification.class));
        }
    }

    @Test
    void createNotificationWithMultipleTokens() throws FirebaseMessagingException {
        when(tokenRepository.findByUserId(TestData.USER_ID)).thenReturn(List.of(
                TestData.newToken(TestData.TOKEN_ID, TestData.USER_ID, TestData.DEVICE_TOKEN),
                TestData.newToken(TestData.OTHER_USER_ID, TestData.USER_ID, TestData.OTHER_DEVICE_TOKEN)
        ));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FirebaseMessaging firebaseMessaging = mock(FirebaseMessaging.class);
        when(firebaseMessaging.send(any(Message.class))).thenReturn(TestData.FIREBASE_MESSAGE_ID);

        try (MockedStatic<FirebaseMessaging> firebaseMock = mockStatic(FirebaseMessaging.class);
             MockedStatic<TimeUtil> timeMock = mockStatic(TimeUtil.class)) {
            firebaseMock.when(FirebaseMessaging::getInstance).thenReturn(firebaseMessaging);
            timeMock.when(TimeUtil::now).thenReturn(TestData.SENT_AT);

            notificationService.createNotification(TestData.notificationRequestDto());

            verify(firebaseMessaging, times(2)).send(any(Message.class));
            verify(notificationRepository, times(2)).save(any(Notification.class));
        }
    }

    @Test
    void createNotificationWhenTokensNotFound() {
        when(tokenRepository.findByUserId(TestData.USER_ID)).thenReturn(List.of());
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto result = notificationService.createNotification(
                TestData.notificationRequestDto());

        assertThat(result.userId()).isEqualTo(TestData.USER_ID);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void createNotificationWhenTokenRepositoryThrows() {
        when(tokenRepository.findByUserId(TestData.USER_ID))
                .thenThrow(new RuntimeException("db error"));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NotificationResponseDto result = notificationService.createNotification(
                TestData.notificationRequestDto());

        assertThat(result.userId()).isEqualTo(TestData.USER_ID);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void createNotificationWhenFirebaseSendFails() throws FirebaseMessagingException {
        when(tokenRepository.findByUserId(TestData.USER_ID))
                .thenReturn(List.of(TestData.defaultToken()));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FirebaseMessaging firebaseMessaging = mock(FirebaseMessaging.class);
        FirebaseMessagingException exception = mock(FirebaseMessagingException.class);
        when(firebaseMessaging.send(any(Message.class))).thenThrow(exception);

        try (MockedStatic<FirebaseMessaging> firebaseMock = mockStatic(FirebaseMessaging.class);
             MockedStatic<TimeUtil> timeMock = mockStatic(TimeUtil.class)) {
            firebaseMock.when(FirebaseMessaging::getInstance).thenReturn(firebaseMessaging);
            timeMock.when(TimeUtil::now).thenReturn(TestData.SENT_AT);

            NotificationResponseDto result = notificationService.createNotification(
                    TestData.notificationRequestDto());

            assertThat(result.userId()).isEqualTo(TestData.USER_ID);
            verify(firebaseMessaging).send(any(Message.class));
            verify(notificationRepository, atLeastOnce()).save(any(Notification.class));
        }
    }
}
