package com.pratik.realtime_collaborator.repository;

import com.pratik.realtime_collaborator.model.DocumentVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentVersionRepository extends MongoRepository<DocumentVersion, String> {
    
}
