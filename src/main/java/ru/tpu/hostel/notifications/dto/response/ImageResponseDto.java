package ru.tpu.hostel.notifications.dto.response;

import ru.tpu.hostel.notifications.entity.Image;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Image}
 */
public record ImageResponseDto(UUID id, String uri) implements Serializable {
}