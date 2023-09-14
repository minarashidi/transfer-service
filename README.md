
# transfer-service

transfer-service is a Spring Boot application that handles deposit and refund transfer types.

---

### Technologies / Changes
* Java 17
* Spring Boot 3
* Build tool: Maven
* Use docker-compose TestContainers for Integration Tests
* The use of [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)

---

### How to run locally

#### Prerequisites

- JDK 17

### Build
- Go to root directory and run: `./mvn clean install`

### Run

1. While in the `/scripts/` folder, run `./start-services.sh`.
2. Run `./create-database.sh` in the `/scripts/` folder.
3. Run application using: `mvn spring-boot:run`
4. If you want to play around with db, you can run
  * `docker ps` and grabbing Container ID for the postgres image and follow with
   `docker exec -it ${containerID} psql transfer_service -U postgres` 
    While in the container, run `\dt transfer_service.` you should see a list of created tables.
---

### API Documentation
When the application is up and running, it can be accessed through: http://localhost:8080/transfer-docs/swagger-ui-custom.html

### Log files

### Monitoring and Alerts

### Concepts / Improvement points

#### Handle idempotency for POST API
I have used database storage to handle idempotency for POST requests , but we could also use distributed Caching via Redis.
Ultimately, the choice depends on specific use case, performance requirements, and data retention needs, and both approaches have their trade-offs.

Database Storage: Reliability, Longer-Term Storage
Distributed Caching: Performance, Expiration

Also in some cases, we can use a hybrid approach, where we use both caching and a database. 
For example, we can use caching for quick lookups and database storage for long-term persistence