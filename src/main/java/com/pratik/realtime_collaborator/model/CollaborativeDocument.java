package com.pratik.realtime_collaborator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "documents")
public class CollaborativeDocument {
    @Id
    private String id;
    private String title;

    private String currentContent;

    private long currentVersion;

    private Instant createdAt;

    private Instant updatedAt;

    public CollaborativeDocument() {
    }

    public CollaborativeDocument(Instant createdAt, String currentContent, long currentVersion, String title, Instant updatedAt) {
        this.createdAt = createdAt;
        this.currentContent = currentContent;
        this.currentVersion = currentVersion;
        this.title = title;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public long getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(long currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
