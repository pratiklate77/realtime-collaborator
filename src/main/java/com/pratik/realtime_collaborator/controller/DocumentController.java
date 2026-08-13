package com.pratik.realtime_collaborator.controller;


import com.pratik.realtime_collaborator.dto.CreateDocumentRequest;
import com.pratik.realtime_collaborator.dto.UpdateTitleRequest;
import com.pratik.realtime_collaborator.model.CollaborativeDocument;
import com.pratik.realtime_collaborator.service.DocumentService;
import org.springframework.web.bind.annotation.*;


@RestController
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController (DocumentService documentService){
        this.documentService = documentService;
    }

    @PostMapping("/api/documents")
    public CollaborativeDocument createDocument(@RequestBody CreateDocumentRequest request){
        return documentService.createDocument(request.getTitle());
    }

    @PatchMapping("/api/documents/{id}/title")
    public CollaborativeDocument updateTitle(
            @PathVariable String id,
            @RequestBody UpdateTitleRequest request){
            return documentService.updateTitle(id, request.getTitle());
    }

    @GetMapping("/api/document/findByTitle/{title}")
    public CollaborativeDocument findDocumentByTitle(@PathVariable("title") String title){
        System.out.println("searching for " + title + " heee   Document   ");

        return documentService.findDocumentByTitle(title);
    }

    @GetMapping("/api/document/findById/{id}")

    public CollaborativeDocument findDocumentById(@PathVariable("id") String id){

        return documentService.findDocumentById(id);
    }

}
