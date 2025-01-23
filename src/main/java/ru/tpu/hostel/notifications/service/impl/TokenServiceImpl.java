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

    private final TokenRepository tokenRepository;

    @Override
    public ResponseEntity<?> createToken(TokenRequestDto tokenRequestDto, UUID userId) {
        Token token = new Token();
        token.setId(userId);
        token.setToken(tokenRequestDto.token());

        tokenRepository.save(token);

        return ResponseEntity.ok(token);
    }
}
