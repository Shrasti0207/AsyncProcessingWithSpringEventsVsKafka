# Async Processing Kafka Vs Spring Events

This project demonstrates and compares **Spring Events** and **Apache Kafka** for asynchronous processing across microservices. It includes:

- A **producer service** (`order-service`) that creates orders.
- Two **consumer services** (`inventory-service` and `notification-service`) that respond to order creation via **Kafka**.
- Local event-driven logic inside `order-service` using **Spring Application Events**.

---

## Concept

- **Spring Events** are used within a single application context (in-process).
- **Kafka** is used to asynchronously communicate between multiple microservices (inter-process).

This project showcases both strategies to highlight their differences in scalability, performance, and architecture.

---

## Project Structure
```bash
  AsyncProcessingKafkaVsEvents/
  ├── docker-compose.yml                  # Docker setup for Kafka and Zookeeper
  ├── inventory-service/                 
  │   ├── Kafka listener for orders       # Consumes messages from 'order-topic'
  │   └── Integration test for Kafka consumer
  ├── notification-service/
  │   ├── Kafka listener for orders       # Consumes messages from 'order-topic'
  │   └── Integration test for Kafka consumer
  └── order-service/
  ├── Spring Events                   # In-process event classes and listeners
  ├── Kafka Producer                  # Publishes messages to 'order-topic'
  └── Integration tests               # Covers both Kafka and Spring Events
```
---

## 🚀 How to Run This Project

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### 1. Clone the Repository

```bash
  git clone https://github.com.mcas.ms/Shrasti0207/AsyncProcessingWithSpringEventsVsKafka
  cd AsyncProcessingKafkaVsEvents

```
### 2. Start Kafka (with KRaft)

We are using **KRaft mode** to run Kafka.

Make sure Docker is running, then start the Kafka container:

```bash
  docker-compose up -d
```

### 3. How to Run the Microservices

Each service in this project is a standalone Spring Boot application. You can run them either from the terminal or using your IDE (e.g., IntelliJ IDEA).

---

### 4. Using Terminal

Open **three separate terminals** and run the following commands for each service:

```bash
  # Run Order Service
  cd order-service
  ./mvnw spring-boot:run

  # Run Inventory Service
  cd /inventory-service
  ./mvnw spring-boot:run

  #Run Notification Service
  cd notification-service
  ./mvnw spring-boot:run
``` 

## Running Tests

To run unit and integration tests:

```bash
  ./mvnw test
```

## API Endpoint

**Create Order**

**Request Body:**
```json
{
  "id": "order-1",
  "status": "CREATED"
}
```

Expected flow:

- Spring event triggered in `order-service`
- Kafka message published to `order-topic`
- `inventory-service` and `notification-service` consume the Kafka message.


## Observed Differences: Spring Events vs Kafka

| **Aspect**            | **Spring Events**                        | **Kafka**                                  |
|------------------------|------------------------------------------|--------------------------------------------|
| Scope                 | In-process only                          | Cross-service / distributed                |
| Reliability           | Not guaranteed                           | High (with retries and offsets)            |
| Communication         | Synchronous or Async within JVM          | Always Async via broker                    |
| Use Case              | Lightweight, single-app event handling   | Inter-service messaging                    |
| Testing               | Simple to unit test                      | Requires EmbeddedKafka for integration     |
| Performance (Local)   | Faster (in-memory dispatch)              | Slightly slower due to network & broker    |
| Observed in Template  | Spring event fired instantly in same app | Kafka delivered to all subscribed services |
