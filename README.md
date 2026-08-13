# Real-Time Collaborator

A real-time collaborative document editing platform built with **Java, Spring Boot, MongoDB, Docker, and WebSockets**.

The goal of this project is to build a system where multiple users can collaborate on documents in real time while maintaining document history, versions, and data recovery.

> **Project Status:** 🚧 In Development

---

## 🎯 Project Goals

Real-Time Collaborator is designed to provide:

* Real-time collaborative document editing
* Multiple users working on the same document
* Automatic synchronization of document changes
* Document versioning and history
* Autosave and recovery mechanisms
* Persistent document storage
* REST APIs for document management
* WebSocket-based real-time communication
* Scalable backend architecture

---

## 🏗️ Architecture

The project follows a layered Spring Boot architecture:

```text
Client
  │
  ▼
REST API / WebSocket
  │
  ▼
Controller
  │
  ▼
Service Layer
  │
  ▼
Repository Layer
  │
  ▼
MongoDB
```

Real-time collaboration will additionally use:

```text
User A ─────┐
            │
            ▼
       WebSocket
            │
            ▼
     Collaboration
       Service
            │
            ▼
       WebSocket
            │
            ▼
User B ─────┘
```

---

## 🛠️ Technology Stack

### Backend

* Java
* Spring Boot
* Spring Data MongoDB
* REST APIs
* WebSockets

### Database

* MongoDB

### Infrastructure

* Docker
* Docker Compose

### Development

* IntelliJ IDEA
* Maven
* Git
* GitHub

---

## 📂 Project Structure

The project is organized into separate layers:

```text
src/
└── main/
    ├── java/
    │   └── com/pratik/realtime_collaborator/
    │       ├── controller/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── RealTimeCollaboratorApplication.java
    │
    └── resources/
        └── application.properties

docker-compose.yml
pom.xml
.gitignore
README.md
```

---

## 📄 Document Model

The core entity of the application is the collaborative document.

A document currently contains information such as:

```text
CollaborativeDocument
├── id
├── title
├── currentContent
├── currentVersion
├── createdAt
└── updatedAt
```

The document content represents the current state of the document, while the version information will be used to maintain document history.

---

## 🕒 Document Versioning

One of the major features of the project is maintaining document history.

The planned versioning architecture is:

```text
Document
   │
   ├── Current Content
   └── Current Version
            │
            ▼
     Document Versions
            │
            ├── Version 1
            ├── Version 2
            ├── Version 3
            └── ...
```

Versions will allow users to:

* View previous document states
* Track changes over time
* Recover previous versions
* Understand the history of a document

---

## 💾 Saving Strategy

The project is designed around two types of saving:

### Explicit Save

When the user intentionally saves the document, a permanent version can be created.

### Temporary Autosave

During active editing, temporary document state can be stored periodically to reduce the risk of data loss.

The planned flow is:

```text
User edits document
       │
       ▼
Temporary state
       │
       ▼
Periodic autosave
       │
       ▼
Temporary storage
       │
       ▼
Permanent version
```

This approach is intended to balance:

* Data safety
* Database writes
* Version history
* Application performance

---

## 🐳 MongoDB with Docker

MongoDB is currently run using Docker Compose.

The database architecture is:

```text
Spring Boot Application
        │
        ▼
   MongoDB :27017
        │
        ▼
Docker Volume
        │
        ▼
Persistent MongoDB Data
```

Docker provides an isolated and reproducible MongoDB development environment.

---

## 🔐 Configuration

Sensitive configuration values are provided through environment variables rather than being hardcoded into the application.

For example:

```properties
spring.mongodb.uri=${MONGODB_URI}
```

The actual MongoDB connection URI is supplied through the environment.

Sensitive files such as `.env` are excluded using `.gitignore`.

---

## 🔌 REST API

The application currently provides REST APIs for creating, retrieving, and updating collaborative documents.

### Base URL

```text
http://localhost:8080
```

---

### API Endpoints

| Method  | Endpoint                            | Description                   |
| ------- | ----------------------------------- | ----------------------------- |
| `POST`  | `/api/documents`                    | Create a new document         |
| `GET`   | `/api/document/findById/{id}`       | Find a document by MongoDB ID |
| `GET`   | `/api/document/findByTitle/{title}` | Find a document by title      |
| `PATCH` | `/api/documents/{id}/title`         | Update a document's title     |

---

### 1. Create Document

Creates a new collaborative document and stores it in MongoDB.

**Request**

```http
POST /api/documents
Content-Type: application/json
```

**Request Body**

```json
{
  "title": "DSA Notes"
}
```

**cURL**

```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{"title":"DSA Notes"}'
```

**Behavior**

* Creates a new document.
* MongoDB automatically generates the document ID.
* The initial document version is set to `0`.
* `createdAt` and `updatedAt` are initialized.
* Document titles must be unique.
* Creating a document with an existing title is rejected.

**Example Response**

```json
{
  "id": "64f123abc456789",
  "title": "DSA Notes",
  "currentContent": null,
  "currentVersion": 0,
  "createdAt": "2026-08-13T10:30:00Z",
  "updatedAt": "2026-08-13T10:30:00Z"
}
```

---

### 2. Find Document by ID

Retrieves a document using its MongoDB-generated ID.

**Request**

```http
GET /api/document/findById/{id}
```

**Example**

```http
GET /api/document/findById/64f123abc456789
```

**cURL**

```bash
curl http://localhost:8080/api/document/findById/64f123abc456789
```

**Behavior**

* Searches MongoDB using the document ID.
* Returns the matching document.
* Returns an error when the document does not exist.

**Example Response**

```json
{
  "id": "64f123abc456789",
  "title": "DSA Notes",
  "currentContent": null,
  "currentVersion": 0,
  "createdAt": "2026-08-13T10:30:00Z",
  "updatedAt": "2026-08-13T10:30:00Z"
}
```

---

### 3. Find Document by Title

Retrieves a document using its title.

**Request**

```http
GET /api/document/findByTitle/{title}
```

**Example**

```http
GET /api/document/findByTitle/DSA%20Notes
```

The space in the title is URL encoded as `%20`.

**cURL**

```bash
curl "http://localhost:8080/api/document/findByTitle/DSA%20Notes"
```

**Behavior**

* Searches MongoDB using the document title.
* Returns the matching document.
* The MongoDB `_id` remains the actual document identifier.
* The title provides a convenient human-readable lookup mechanism.
* Returns an error when no document with the specified title exists.

**Example Response**

```json
{
  "id": "64f123abc456789",
  "title": "DSA Notes",
  "currentContent": null,
  "currentVersion": 0,
  "createdAt": "2026-08-13T10:30:00Z",
  "updatedAt": "2026-08-13T10:30:00Z"
}
```

---

### 4. Update Document Title

Updates the title of an existing document.

**Request**

```http
PATCH /api/documents/{id}/title
Content-Type: application/json
```

**Example**

```http
PATCH /api/documents/64f123abc456789/title
```

**Request Body**

```json
{
  "title": "Advanced DSA Notes"
}
```

**cURL**

```bash
curl -X PATCH http://localhost:8080/api/documents/64f123abc456789/title \
  -H "Content-Type: application/json" \
  -d '{"title":"Advanced DSA Notes"}'
```

**Behavior**

* Finds the document using its MongoDB ID.
* Updates the document title.
* Updates the `updatedAt` timestamp.
* Prevents changing the title to a title already used by another document.
* Returns an error when the document does not exist.

**Example Response**

```json
{
  "id": "64f123abc456789",
  "title": "Advanced DSA Notes",
  "currentContent": null,
  "currentVersion": 0,
  "createdAt": "2026-08-13T10:30:00Z",
  "updatedAt": "2026-08-13T11:00:00Z"
}
```

---

### Request DTOs

The API uses DTOs to receive request data instead of directly exposing the MongoDB entity.

#### CreateDocumentRequest

Used by:

```http
POST /api/documents
```

```json
{
  "title": "DSA Notes"
}
```

#### UpdateTitleRequest

Used by:

```http
PATCH /api/documents/{id}/title
```

```json
{
  "title": "Advanced DSA Notes"
}
```

#### UpdateDocumentRequest

The project currently contains an `UpdateDocumentRequest` DTO with support for:

```json
{
  "title": "Updated Document",
  "content": "Updated document content"
}
```

The document-content update endpoint is **not implemented yet** and will be added in a future stage.

---

### Current API Architecture

```text
Client
  │
  │ HTTP Request
  ▼
DocumentController
  │
  ▼
DocumentService
  │
  ▼
DocumentRepository
  │
  ▼
MongoDB
```

The controller handles HTTP requests, the service layer contains business logic, the repository handles database operations, and MongoDB provides persistent document storage.


## 🚧 Current Progress

### Completed

* [x] Create Spring Boot project
* [x] Configure Maven
* [x] Configure MongoDB
* [x] Create Docker Compose configuration
* [x] Connect Spring Boot to MongoDB
* [x] Create `CollaborativeDocument` model
* [x] Create `DocumentVersion` model
* [x] Create MongoDB repository
* [x] Create document service
* [x] Successfully persist a document in MongoDB
* [x] Initialize Git repository
* [x] Push initial project to GitHub
* [x] Create DTO layer
* [x] Create REST controller
* [x] Implement document creation API
* [x] Implement document retrieval by MongoDB ID
* [x] Implement document retrieval by title
* [x] Implement document title update API
* [x] Add duplicate document-title validation
* [x] Remove temporary startup document creation test

### In Progress

* [ ] Implement complete document content update API
* [ ] Implement document deletion API
* [ ] Implement document version history
* [ ] Implement autosave
* [ ] Implement WebSocket communication
* [ ] Implement real-time collaboration
* [ ] Implement concurrent editing handling
* [ ] Implement user authentication
* [ ] Add tests
* [ ] Add production deployment

---

## 🧠 Learning Objectives

This project is also being developed as a learning project.

While building it, the following concepts will be explored:

* Spring Boot fundamentals
* Dependency Injection
* REST API design
* DTOs
* MongoDB
* Spring Data MongoDB
* Repository pattern
* Service layer architecture
* WebSockets
* Multithreading
* Concurrent programming
* Real-time communication
* Document versioning
* Autosave mechanisms
* Data consistency
* Docker
* Environment-based configuration
* Git and GitHub
* Testing
* Production deployment

---

## 🔮 Future Architecture

The final system is expected to evolve into something similar to:

```text
                    ┌──────────────┐
                    │    Client    │
                    └──────┬───────┘
                           │
                    REST / WebSocket
                           │
                           ▼
                ┌────────────────────┐
                │   Spring Boot API  │
                └─────────┬──────────┘
                          │
             ┌────────────┴────────────┐
             │                         │
             ▼                         ▼
       Document Service       Collaboration Service
             │                         │
             ▼                         ▼
        MongoDB                  WebSocket
             │                         │
             ▼                         ▼
      Version History          Connected Users
```

---

## 🚀 Running the Project

### 1. Start MongoDB

Make sure Docker is running and execute:

```bash
docker compose up -d
```

Verify the container:

```bash
docker ps
```

### 2. Configure MongoDB URI

Set the environment variable:

```text
MONGODB_URI
```

Example for local development:

```text
mongodb://admin:adminpassword@localhost:27017/realtime_collaborator_db?authSource=admin
```

### 3. Start Spring Boot

Run the application using IntelliJ IDEA or Maven:

```bash
./mvnw spring-boot:run
```

---

## 📌 Development Philosophy

This project is being developed incrementally.

Each major feature is implemented only after understanding the underlying concept. The objective is not only to build a working application, but also to understand the architectural decisions behind a real-time collaborative system.

---

## 👨‍💻 Author

**Pratik Late**

This project is being developed as a backend/system-design focused project to explore real-time distributed application development with Java and Spring Boot.
