package com.pratik.realtime_collaborator;

import com.pratik.realtime_collaborator.model.CollaborativeDocument;
import com.pratik.realtime_collaborator.service.DocumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RealtimeCollaboratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeCollaboratorApplication.class, args);
	}

	@Bean
	CommandLineRunner testDocumentCreation(DocumentService documentService){
		return args -> {
			CollaborativeDocument collaborativeDocument = documentService.createDocument("My First Document");

			System.out.println("Created Document ID : " + collaborativeDocument.getId());
		};
	}

}
