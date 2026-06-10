package ru.tpu.hostel.notifications.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tpu.hostel.internal.utils.ExecutionContext;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.entity.Token;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.impl.TokenServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void createTokenWithSuccess() {
        try (MockedStatic<ExecutionContext> contextMock = mockStatic(ExecutionContext.class)) {
            ExecutionContext context = mock(ExecutionContext.class);
            contextMock.when(ExecutionContext::get).thenReturn(context);
            when(context.getUserID()).thenReturn(TestData.USER_ID);

            ResponseEntity<?> result = tokenService.createToken(TestData.tokenRequestDto());

            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isInstanceOf(Token.class);
            Token body = (Token) result.getBody();
            assertThat(body.getId()).isEqualTo(TestData.USER_ID);
            assertThat(body.getToken()).isEqualTo(TestData.DEVICE_TOKEN);
        }
    }

    @Test
    void createTokenCallsUpsertWithTokenValue() {
        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        try (MockedStatic<ExecutionContext> contextMock = mockStatic(ExecutionContext.class)) {
            ExecutionContext context = mock(ExecutionContext.class);
            contextMock.when(ExecutionContext::get).thenReturn(context);
            when(context.getUserID()).thenReturn(TestData.USER_ID);

            tokenService.createToken(TestData.tokenRequestDto());

            verify(tokenRepository).upsertTokenNative(isNull(), tokenCaptor.capture());
            assertThat(tokenCaptor.getValue()).isEqualTo(TestData.DEVICE_TOKEN);
        }
    }
}
