package com.pratik.realtime_collaborator.service;


import com.pratik.realtime_collaborator.model.CollaborativeDocument;
import com.pratik.realtime_collaborator.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public CollaborativeDocument createDocument(String title){
        CollaborativeDocument collaborativeDocument = new CollaborativeDocument( );
        collaborativeDocument.setTitle(title);
        Instant now = Instant.now();
        collaborativeDocument.setCreatedAt(now);
        collaborativeDocument.setUpdatedAt(now);
        collaborativeDocument.setCurrentContent("");
        collaborativeDocument.setCurrentVersion(0);
        return documentRepository.save(collaborativeDocument);
        }


}
