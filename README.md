# JobTrack

A microservices-based job application tracking platform built with Java 17 and Spring Boot.

JobTrack allows users to manage job applications, search application records, and monitor application statistics. 
This project also serves as a practical implementation of several backend technologies and concepts.

## Technology Stack

- **Language:** Java 17
- **Framework:** Spring Boot
- **Architecture:** Microservices
- **Messaging:** Apache Kafka
- **Search Engine:** Elasticsearch
- **Caching:** Redis
- **Containerization:** Docker
- **Build Tool:** Maven

## Features

### 1. Application Management

- Create and manage job application records.
- Store information such as company name, position, location, employment type, salary, and application status.

### 2. Application Search with Elasticsearch

- Index application data in Elasticsearch.
- Implement search functionality using Elasticsearch queries.
- Support filtering and searching across application fields.
- Synchronize search indexes through Kafka events.

### 3. Application Analytics

- Generate application summaries.
- Aggregate application records by status.
- Process application events asynchronously.

### 4. Kafka Event-Driven Messaging

Explore asynchronous communication between microservices using Apache Kafka.

Implemented concepts include:
- Kafka producers and consumers.
- Event-based communication between services.
- Consumer groups.
- Message retry mechanisms using `@RetryableTopic`.
- Dead Letter Topics (DLT) for messages that exceed configured retry attempts.

### 5. Redis Caching

Explore caching with Redis and Spring Cache.

Implemented concepts include:
- Caching frequently accessed analytics endpoints using `@Cacheable`.
- Cache invalidation using `@CacheEvict`.
- Reducing repeated database queries.

## Example API

### Create Application

**POST** `/api/applications`

**Request Body:**

```json
{
  "companyName": "Company Name",
  "position": "Backend Developer",
  "location": "Jakarta",
  "employmentType": "FULL_TIME",
  "salary": 125000
}
```

**Example Request:**

```bash
curl -X POST "http://localhost:8080/api/applications" \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Company Name",
    "position": "Backend Developer",
    "location": "Jakarta",
    "employmentType": "FULL_TIME",
    "salary": 125000
  }'
```

## Docker Compose Notes

Docker Compose currently uses `depends_on` to define service startup dependencies. However, `depends_on` does not necessarily wait until dependent services are fully ready to accept connections.

As a result, some services might fail to start properly if their dependencies are not ready yet. If this happens, restart the affected service manually.
