Here is a complete, production-ready `README.md` template designed to make your repositories stand out to tech recruiters and open-source contributors. It perfectly highlights the architectural choices you made (like the Optimistic UI and Unified Room Model).

You can use this as a unified main README or split it across your two repositories.

---

```markdown
# Blink 💬

Blink is a high-performance, real-time desktop chat ecosystem featuring a modular **JavaFX client** and a stateless **Spring Boot REST API** backed by **MongoDB**. 

Designed to mimic modern chat platforms like Discord and Slack, Blink utilizes an engineered **Unified Room Model** to manage both public multi-user channels and private 1-to-1 direct messages seamlessly through a single messaging pipeline.

---

## 🚀 Key Architectural Features

* **Optimistic UI Engine:** The client utilizes an asynchronous multithreaded architecture (`CompletableFuture` + `Platform.runLater()`). Outbound messages are injected into the UI instantly with a `PENDING` state, preventing UI stuttering during network transmission, and updating to `SENT` or `FAILED` based on backend delivery validation.
* **Unified Room Architecture:** Instead of maintaining fractured data pipelines for group channels vs. private chats, DMs are treated as specialized rooms containing exactly two users. This minimizes API routing complexity and drastically speeds up query execution.
* **Stateless Security & Data Separation:** Secured using JWT Bearer authentication. Data payloads are tightly governed using dedicated Data Transfer Objects (DTOs) to safely filter out sensitive credentials and strip out unneeded data layers prior to client delivery.

---

## 🛠️ Technology Stack

| Layer | Technologies Used |
| :--- | :--- |
| **Frontend / Client** | Java, JavaFX, FXML, Jackson (JSON Core & JSR310 Datatypes) |
| **Backend / API** | Java, Spring Boot, Spring Security (JWT), Spring Data MongoDB |
| **Database** | MongoDB (Cloud Cluster) |
| **Build System** | Maven (Modular Java System Framework) |

---

## ⚙️ System Architecture & Data Flow

```text
  [ JavaFX Client UI ] 
        │
        ▼ (Optimistic UI Update -> Set Status: PENDING)
  [ CompletableFuture Worker Thread ]
        │
        ▼ (HTTP POST with JWT Auth Header)
  [ Spring Boot Controller ] ──(Verify Token)──► [ Spring Security ]
        │                                             │ (Valid)
        ▼                                             ▼
  [ MongoDB Database ] ◄────(Persist Document)──── [ Service Layer ]
        │
        ▼ (Returns Message DTO with ID & Generated Timestamp)
  [ JavaFX Application Thread ] ──(Platform.runLater())──► (Update Status: SENT)

```

---

## 📦 Getting Started

### Backend Configuration

The API protects production credentials strictly through machine environment variables.

1. Clone the backend repository.
2. Configure your local runtime environment variable:
```bash
# Name of the variable required by application.properties
MONGO_CONNECTION_STRING="your_mongodb_connection_uri"

```


3. Build and launch the Spring Boot context using Maven:
```bash
mvn spring-boot:run

```



### Client Configuration

The client application relies on Java 9 Module System architecture.

1. Clone the client repository.
2. Verify that your JVM targets **Java 21** or later.
3. Build and execute the modular application:
```bash
mvn clean javafx:run

```



---

## 🛠️ Roadmap & Upcoming Enhancements

* [ ] Implement a custom JavaFX `ListCell` Factory to render status indicators (faded text for pending, red error nodes for failed dispatches).
* [ ] Transition the API transmission layer from a polling HTTP layer to a duplex **WebSocket** pipeline for fully native real-time message pushing.
* [ ] Build a search query filter to dynamically look up global profiles by `Username` or `Email` keys.

```

```
