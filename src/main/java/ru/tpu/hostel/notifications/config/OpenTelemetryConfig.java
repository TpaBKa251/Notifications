package ru.tpu.hostel.notifications.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

@Validated
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({OpenTelemetryProperties.class})
public class OpenTelemetryConfig {
    private final OpenTelemetryProperties openTelemetryProperties ;

    @Bean
    public SpanExporter otlpSpanExporter() {
        if (Boolean.TRUE.equals(openTelemetryProperties.exportEnabled())) {
            return OtlpGrpcSpanExporter.builder()
                    .setEndpoint(openTelemetryProperties.endpoint())
                    .setTimeout(openTelemetryProperties.timeout().toMillis(), TimeUnit.MILLISECONDS)
                    .build();
        }

        return SpanExporter.composite();
    }

    @Bean
    public SdkTracerProvider sdkTracerProvider(SpanExporter otlpSpanExporter) {
        return SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(otlpSpanExporter).build())
                .setResource(Resource.getDefault().toBuilder()
                        .put("service.name", openTelemetryProperties.serviceName())
                        .build())
                .build();
    }
}

