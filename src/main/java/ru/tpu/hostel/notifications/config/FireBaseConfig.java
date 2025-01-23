package ru.tpu.hostel.notifications.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseConfig {

    private static final String FILE_PATH = System.getenv("SERVICE_ACCOUNT_FILE_PATH");

    public static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(FILE_PATH);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
