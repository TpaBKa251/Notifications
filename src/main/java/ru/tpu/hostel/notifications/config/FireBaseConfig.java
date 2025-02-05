package ru.tpu.hostel.notifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FireBaseConfig {

    private static final String FILE_PATH = System.getenv("SERVICE_ACCOUNT_FILE_PATH");

    @PostConstruct
    public static void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream(FILE_PATH);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
