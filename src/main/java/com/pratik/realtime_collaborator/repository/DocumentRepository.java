package com.pratik.realtime_collaborator.repository;

import com.pratik.realtime_collaborator.model.CollaborativeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocumentRepository extends MongoRepository<CollaborativeDocument, String> {

    boolean existsByTitle(String title);

    Optional<CollaborativeDocument> findByTitle(String title);

    boolean existsByTitleAndIdNot(String title, String id);





}
