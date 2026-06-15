

# Blink API 🚀

Blink API is a scalable, stateless RESTful service serving as the core engine powering the Blink real-time communication platform. It orchestrates user directory management, persistent message storage, stateless session tokens, and automated conversation routing. 

---

## 📐 Core Design Patterns & Architecture

### 1. The Unified Room Model
To prevent database schema bloating and minimize index maintenance costs, the persistence layer utilizes a unified, single-collection strategy for storage:
* **The NoSQL Schema:** All conversational units inhabit a single NoSQL collection, segregated structurally by an internal `RoomType` enum (`GROUP`, `DIRECT`).
* **Direct Messaging Strategy:** 1-to-1 private messages do not possess a dedicated entity framework. Instead, they are generated as specialized rooms with a `DIRECT` flag where the total capacity of the `memberIds` array is locked to exactly two active users. This strategy eliminates relational joins and permits a single HTTP route pipeline to feed both channels and private chats identically.

### 2. Stateless Security Filter Chains
* **JWT Interception:** The security envelope functions entirely statelessly. Every inbound HTTP resource request is verified by a custom Spring Security filter parsing authorization strings inside bearer tokens (`Authorization: Bearer <JWT>`).
* **Data Transfer Objects (DTO Isolation):** Database entities are strictly forbidden from passing straight into communication sockets. The service implements rigid serialization DTOs (like `MessageDTO`) to shield sensitive operational data (such as encrypted password hashes) from leaking over public lines.

---

## 🛠️ Technology Stack

| Component | Technology | Purpose |
| :--- | :--- | :--- |
| **Framework Core** | Spring Boot 4.1.0 | Enterprise inversion of control and automated configuration |
| **Security Envelope** | Spring Security & JJWT | Token parsing, claims tracking, and stateless access controls |
| **Data Access Layer** | Spring Data MongoDB | Object-Document mapping and database operations execution |
| **Database Cluster** | MongoDB Atlas | Highly available cloud database persistence infrastructure |
| **Build Framework** | Apache Maven | Project lifecycle automation and packaging orchestration |

---

## 🔒 Configuration & Environment Variables

To protect live cluster access, the backend prohibits hardcoded production strings. The Spring layer dynamically injects values via environmental parsing placeholders.

### Required Parameters
Before initiating the backend execution runtime, you must establish the following environment key-value property:

| Variable Identifier | Operational Target | Format Template |
| :--- | :--- | :--- |
| `MONGO_CONNECTION_STRING` | Destination pointer for the MongoDB cluster | `mongodb+srv://<user>:<pwd>@cluster.mongodb.net/blink` |
| `SECRET_KEY` | Secret for HS256 password encryption | `YourSuperSecretKeyThatIsVeryLongAndSecureEnoughForHS256AlgorithmToWorkProperly` |

### System Execution Setup
1. Clone the API service source directory:
```bash
   git clone https://github.com/hash1riya/BlinkApi.git
   cd BlinkApi
```

Inject your deployment parameter and start the Spring context via Maven:
   * **Linux / macOS:**

```bash
   export MONGO_CONNECTION_STRING="your_mongodb_connection_uri"
   export SECRET_KEY="your_secret_key"
   mvn spring-boot:run
```
   * **Windows (PowerShell):**
```powershell
   $env:MONGO_CONNECTION_STRING="your_mongodb_connection_uri";
   $env:SECRET_KEY="your_secret_key";
   mvn spring-boot:run
```

---

## 🗺️ API Endpoint Directory Specification

Every route wrapper outside public authentication blocks requires a validated authorization token attached to the request headers: `Authorization: Bearer <your_jwt_token>`.

### 1. Authentication Layer

#### `POST /blink/auth/register`
* **Context:** Public
* **Payload Request:**
```json
  {
    "username": "daniil",
    "email": "daniil@example.com",
    "password": "cleartest_password",
    "desc": "some_description"
  }
```
* Payload Response (200 OK): Authorize and get a token
```json
  {
    "token": "eyJhbGciOi...",
    "id": "65cb3f9a12b...",
    "username": "daniil",
    "email": "daniil@example.com"
  }
```

#### `POST /blink/auth/authenticate`
* **Context:** Public
* **Payload Request:**
```json
  {
    "username": "daniil",
    "password": "cleartext_password"
  }
```
* Payload Response (200 OK): Register a new user in database
```json
  {
    "token": "eyJhbGciOi...",
    "id": "65cb3f9a12b...",
    "username": "daniil",
    "email": "daniil@example.com"
  }
```

---

### 2. User Directory

#### `GET /blink/users/{userId}/friends/accepted`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Get all accepted friends requests per user
```json
  [
    {
        "id": "65cb3f9a12b...",
        "friend": {
            "id": "65cb3f9a12b...",
            "username": "daniil",
            "email": "daniil@example.com",
            "desc": "daniil",
            "status": "ONLINE",
            "createdAt": "2026-06-02T21:27:25.817"
        },
        "status": "ACCEPTED",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:39.407",
        "updatedAt": "2026-06-02T21:42:39.407",
        "lastInteractionAt": "2026-06-02T21:46:22.344"
    },
    {
        "id": "6a1f3238071e90cf23a52223",
        "friend": {
            "id": "6a1f2ec616833306520660a7",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:28:06.293"
        },
        "status": "ACCEPTED",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:48.85",
        "updatedAt": "2026-06-02T21:42:48.85",
        "lastInteractionAt": "2026-06-02T21:42:48.85"
    },
    ...
]
```

#### `GET /blink/users/{userId}/friends/pending/received`
and
#### `GET /blink/users/{userId}/friends/pending/sent`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Get pending friend requests per user
```json
  [
    {
        "id": "65cb3f9a12b...",
        "friend": {
            "id": "65cb3f9a12b...",
            "username": "daniil",
            "email": "daniil@example.com",
            "desc": "daniil",
            "status": "ONLINE",
            "createdAt": "2026-06-02T21:27:25.817"
        },
        "status": "PENDING",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:39.407",
        "updatedAt": "2026-06-02T21:42:39.407",
        "lastInteractionAt": "2026-06-02T21:46:22.344"
    },
    {
        "id": "65cb3f9a12b...",
        "friend": {
            "id": "65cb3f9a12b...",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:28:06.293"
        },
        "status": "PENDING",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:48.85",
        "updatedAt": "2026-06-02T21:42:48.85",
        "lastInteractionAt": "2026-06-02T21:42:48.85"
    },
    ...
]
```

#### `POST /blink/users/{requesterId}/friends?receiverId="65cb3f9a12b..."`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Send a friend request to a user
```json
  {
        "id": "65cb3f9a12b...",
        "friend": {
            "id": "65cb3f9a12b...",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:28:06.293"
        },
        "status": "PENDING",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:48.85",
        "updatedAt": "2026-06-02T21:42:48.85",
        "lastInteractionAt": "2026-06-02T21:42:48.85"
    }
```

#### `PATCH /blink/users/{receiverId}/friends?requesterId="65cb3f9a12b..."`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Accept a friend request from a user
```json
  {
        "id": "65cb3f9a12b...",
        "friend": {
            "id": "65cb3f9a12b...",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:28:06.293"
        },
        "status": "ACCEPTED",
        "actionUserId": null,
        "createdAt": "2026-06-02T21:42:48.85",
        "updatedAt": "2026-06-02T21:42:48.85",
        "lastInteractionAt": "2026-06-02T21:42:48.85"
    }
```

---

### 3. Room Directory

#### `GET /blink/users/{userId}/rooms`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Get rooms user participates in
```json
  [
    {
        "id": "65cb3f9a12b...",
        "ownerId": "65cb3f9a12b...",
        "name": "Room 1",
        "desc": "Room 1",
        "type": null,
        "createdAt": "2026-06-02T21:25:34.926"
    },
    {
        "id": "65cb3f9a12b...",
        "ownerId": "65cb3f9a12b...",
        "name": "Room 2",
        "desc": "Room 2",
        "type": null,
        "createdAt": "2026-06-02T21:25:34.926"
    },
    ...
  ]
```

#### `GET /blink/rooms/{roomId}/members?userId="65cb3f9a12b..."`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Get all room members
```json
  [
    {
        "id": "65cb3f9a12b...",
        "user": {
            "id": "65cb3f9a12b...",
            "username": "daniil",
            "email": "daniil@example.com",
            "desc": "daniil",
            "status": "ONLINE",
            "createdAt": "2026-06-02T21:24:42.926"
        },
        "role": "OWNER",
        "joinedAt": "2026-06-02T21:25:35"
    },
    {
        "id": "65cb3f9a12b...",
        "user": {
            "id": "65cb3f9a12b...",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:27:25.817"
        },
        "role": "MEMBER",
        "joinedAt": "2026-06-02T21:39:58.314"
    }
  ]
```

#### `POST /blink/rooms/{roomId}/members?userId="65cb3f9a12b..."`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Join room
```json
  {
        "id": "65cb3f9a12b...",
        "user": {
            "id": "65cb3f9a12b...",
            "username": "coolBob",
            "email": "coolBob@coolBob.com",
            "desc": "coolBob",
            "status": "OFFLINE",
            "createdAt": "2026-06-02T21:27:25.817"
        },
        "role": "MEMBER",
        "joinedAt": "2026-06-02T21:39:58.314"
  }
```

#### `DELETE /blink/rooms/{roomId}/members/{userId}`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Leave room

#### `GET /blink/rooms/{roomId}/history`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Get room message history 
```json
  [
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "coolBob",
      "content": "Sup guys!",
      "createdAt": "2026-06-02T21:24:42.926",
      "updatedAt": "2026-06-02T21:24:42.926"
    },
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "Sup coolBOb",
      "createdAt": "2026-06-02T21:25:42.926",
      "updatedAt": "2026-06-02T21:25:42.926"
    },
    ...
  ]
```

#### `GET /blink/rooms/{roomId}/history/search?content="sup"`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (200 OK): Search message in room history 
```json
  [
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "coolBob",
      "content": "Sup guys!",
      "createdAt": "2026-06-02T21:24:42.926",
      "updatedAt": "2026-06-02T21:24:42.926"
    },
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "Sup coolBob",
      "createdAt": "2026-06-02T21:25:42.926",
      "updatedAt": "2026-06-02T21:25:42.926"
    },
    ...
  ]
```

---

### 4. Message Directory

#### `POST /blink/messages`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* **Payload Request:**
```json
   {
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "Sup coolBob"
   }
```
* Payload Response (201 CREATED): Send message 
```json
   {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "Sup coolBob",
      "createdAt": "2026-06-02T21:25:42.926",
      "updatedAt": "2026-06-02T21:25:42.926"
    }
```

#### `PUT /blink/messages/{messageId}?content="SUP COOLBOB!!!"`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (201 CREATED): Update message 
```json
   {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "SUP COOLBOB!!!",
      "createdAt": "2026-06-02T21:25:42.926",
      "updatedAt": "2026-06-02T21:25:42.926"
    }
```

#### `GET /blink/messages/search?content="sup"`
* **Context:** Protected (Requires Valid JWT Bearer Token)
* Payload Response (201 CREATED): Search sent and received messages by content 
```json
  [
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "coolBob",
      "content": "Sup guys!",
      "createdAt": "2026-06-02T21:24:42.926",
      "updatedAt": "2026-06-02T21:24:42.926"
    },
    {
      "id": "65cb3f9a12b...",
      "userId": "65cb3f9a12b...",
      "roomId": "65cb3f9a12b...",
      "username": "daniil",
      "content": "Sup coolBob",
      "createdAt": "2026-06-02T21:25:42.926",
      "updatedAt": "2026-06-02T21:25:42.926"
    },
    ...
  ]
```

---

## Future Roadmap

### 1. Database Indexing & Pagination Layer
**Objective:** Refactor the message history route (**`GET /blink/rooms/{roomId}/history`**) to utilize Spring Data's Pageable sorting framework and establish compound indexing on MongoDB collections.

**Impact:** Prevents database performance degradation. Instead of dumping thousands of old messages onto the client at once, the API will stream history incrementally (e.g., 100 messages per request) as the user scrolls up.

### 2. Media Handling & Blob Storage Service
**Objective** Build out a dedicated multi-part file routing controller integrated with an object storage cloud solution (like AWS S3 or MinIO).

**Impact** Expands Blink past basic text-only interactions, giving users the ability to safely upload, store, and stream attachments, images, and profile avatars.

### 3. Scalable Event Brokerage (Redis Pub/Sub)
**Objective** Implement a Redis caching and messaging abstraction layer on top of the Core Service layer.

**Impact** Prepares the backend for horizontal scaling. If you deploy multiple instances of your Spring Boot API behind a load balancer, Redis ensures that a message sent to Server A is instantly broadcasted to users connected to Server B.
