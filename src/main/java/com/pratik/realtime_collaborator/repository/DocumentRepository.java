package com.pratik.realtime_collaborator.repository;

import com.pratik.realtime_collaborator.model.CollaborativeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<CollaborativeDocument, String> {


}
