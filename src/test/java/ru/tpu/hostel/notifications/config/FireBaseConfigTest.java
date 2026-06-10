package ru.tpu.hostel.notifications.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;

class FireBaseConfigTest {

    private final FireBaseConfig fireBaseConfig = new FireBaseConfig();

    @Test
    void initializeFirebaseWhenPathIsInvalidDoesNotThrow() {
        ReflectionTestUtils.setField(fireBaseConfig, "serviceAccountFilePath", "non-existent-file.json");

        assertThatCode(fireBaseConfig::initializeFirebase).doesNotThrowAnyException();
    }

    @Test
    void initializeFirebaseWhenPathIsEmptyDoesNotThrow() {
        ReflectionTestUtils.setField(fireBaseConfig, "serviceAccountFilePath", "");

        assertThatCode(fireBaseConfig::initializeFirebase).doesNotThrowAnyException();
    }
}
