package ru.tpu.hostel.notifications.service;

import org.springframework.http.ResponseEntity;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;

import java.util.UUID;

public interface TokenService {

    ResponseEntity<?> createToken(TokenRequestDto tokenRequestDto, UUID userId);
}
