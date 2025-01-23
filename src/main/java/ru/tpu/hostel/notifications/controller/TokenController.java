package ru.tpu.hostel.notifications.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tpu.hostel.notifications.dto.request.TokenRequestDto;
import ru.tpu.hostel.notifications.service.TokenService;

import java.util.UUID;

@RestController
@RequestMapping("/notifications/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createToken(
            @Valid @RequestBody TokenRequestDto tokenRequestDto,
            @PathVariable UUID userId
    ) {
        return tokenService.createToken(tokenRequestDto, userId);
    }
}
