package ru.tpu.hostel.notifications.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;
import ru.tpu.hostel.notifications.entity.Token;
import ru.tpu.hostel.notifications.repository.TokenRepository;
import ru.tpu.hostel.notifications.service.TokenService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    // TODO: При логауте слать сюда сообщение рэббитом об удалении данных о токене юзера

    private final TokenRepository tokenRepository;

    @Override
    public ResponseEntity<?> createToken(TokenRequestDto tokenRequestDto, UUID userId) {
        Token token = new Token();
        token.setUserId(userId);
        token.setToken(tokenRequestDto.token());

        tokenRepository.upsertToken(token);

        return ResponseEntity.ok(token);
    }
}
