package com.lendbiz.p2p.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableScheduling
public class ApiApplication {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(new FileInputStream("service-account-file.json")))
				.build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(ApiApplication.class, args);



	}
}
