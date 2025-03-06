# Pulsar Consumer Demo

This is a Spring Boot application that demonstrates how to consume messages from Apache Pulsar topics.

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- Apache Pulsar 4.0.1 (local or remote instance)

## Setup

1. Make sure you have Apache Pulsar running. If you're running it locally, the default service URL is `pulsar://localhost:6650`

2. Clone this repository and navigate to the project directory

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

## Configuration

The application can be configured through `src/main/resources/application.properties`:

- `pulsar.service-url`: The Pulsar service URL (default: pulsar://localhost:6650)
- `pulsar.topic`: The topic to consume messages from (default: my-topic)
- `pulsar.subscription`: The subscription name (default: my-subscription)

## How it works

The application sets up a Pulsar consumer that:

1. Connects to the specified Pulsar service
2. Subscribes to the configured topic
3. Processes incoming messages and logs them
4. Automatically acknowledges successful message processing
5. Handles errors with negative acknowledgment

The consumer expects messages in JSON format with the following structure:
```json
{
    "id": "message-id",
    "content": "message content",
    "timestamp": 1234567890
}
```

## Technology Stack

- Spring Boot 3.2.2
- Apache Pulsar 4.0.1
- Java 21
- Maven

## Logging

The application logs received messages and any processing errors. You can adjust the log level in the `application.properties` file. 