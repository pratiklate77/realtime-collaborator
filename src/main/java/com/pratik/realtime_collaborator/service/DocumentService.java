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
        if(documentRepository.existsByTitle(title)){
            throw new IllegalArgumentException(
                    "A Document with the same name exists !!");
        }
        CollaborativeDocument collaborativeDocument = new CollaborativeDocument( );
        collaborativeDocument.setTitle(title);
        Instant now = Instant.now();
        collaborativeDocument.setCreatedAt(now);
        collaborativeDocument.setUpdatedAt(now);
        collaborativeDocument.setCurrentContent("");
        collaborativeDocument.setCurrentVersion(0);
        return documentRepository.save(collaborativeDocument);
        }
    public CollaborativeDocument updateTitle(String id, String title){
        CollaborativeDocument document = documentRepository.findById(id)
                .orElseThrow( ()->
                new RuntimeException("Document Not Found !!"));
        if (documentRepository.existsByTitleAndIdNot(title, id)){
            throw new RuntimeException("Document with this title already Exists !! ");

        }

        document.setTitle(title);
        document.setUpdatedAt(Instant.now());
        return documentRepository.save(document);

    }

    public CollaborativeDocument findDocumentByTitle(String title){
        System.out.println("Searching for title: [" + title + "]");
        return  documentRepository.findByTitle(title)
                .orElseThrow( ()->
                        new RuntimeException("Document not found"));
    }

    public CollaborativeDocument findDocumentById(String id){
        return documentRepository.findById(id)
                .orElseThrow( () ->
                        new RuntimeException("Document Not Found !! "));
    }




}
