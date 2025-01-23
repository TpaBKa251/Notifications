package ru.tpu.hostel.notifications.dto.request;

import jakarta.validation.constraints.NotNull;

public record TokenRequestDto(@NotNull String token) {
}
