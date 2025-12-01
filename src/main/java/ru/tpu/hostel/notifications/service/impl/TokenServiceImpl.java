package ru.tpu.hostel.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.tpu.hostel.internal.utils.ExecutionContext;
import ru.tpu.hostel.internal.utils.LogFilter;
import ru.tpu.hostel.internal.utils.SecretArgument;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;
import ru.tpu.hostel.notifications.entity.Token;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    // TODO: При логауте слать сюда сообщение рэббитом об удалении данных о токене юзера

    private final TokenRepository tokenRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @LogFilter(enableParamsLogging = false)
    @Override
    public ResponseEntity<?> createToken(@SecretArgument TokenRequestDto tokenRequestDto) {
        Token token = new Token();
        token.setId(ExecutionContext.get().getUserID());
        token.setToken(tokenRequestDto.token());

        tokenRepository.upsertTokenNative(token.getUserId(), token.getToken());

        return ResponseEntity.ok(token);
    }
}
