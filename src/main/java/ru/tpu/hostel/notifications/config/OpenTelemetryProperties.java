package ru.tpu.hostel.notifications.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "otlp.tracing")
public record OpenTelemetryProperties(
        @NotEmpty
        boolean exportEnabled,

        @NotEmpty
        String endpoint,

        @DurationUnit(ChronoUnit.MILLIS)
        @NotNull
        Duration timeout,

        @NotEmpty
        String serviceName
) {
}
