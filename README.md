# Strategy and Factory Pattern Implementation - PIM Export Service

## Project Overview

This is a **Spring Boot** application that demonstrates the implementation of **Strategy** and **Factory** design patterns for handling multiple data export formats. The application provides a flexible and extensible system for exporting different types of data (Items, Users, UOM) based on input identifiers.

---

## Design Patterns Used

### 1. **Strategy Pattern**
- **Purpose**: Encapsulates a family of algorithms and makes them interchangeable
- **Implementation**: 
  - `ExportStrategy` interface defines the contract
  - Concrete implementations: `ItemDataExportStrategy`, `UserDataExportStrategy`, `UomDataStrategy`
  - Each strategy handles specific ID ranges and provides its own export logic
- **Benefit**: Easy to add new export types without modifying existing code

### 2. **Factory Pattern (Implicit)**
- **Purpose**: Dynamically creates and selects the appropriate strategy at runtime
- **Implementation**: `ExportService` acts as a factory that selects the correct strategy based on ID
- **Benefit**: Centralizes strategy selection logic in one place

### 3. **Dependency Injection Pattern**
- **Purpose**: Loose coupling and automatic dependency management
- **Implementation**: 
  - Spring `@Component` registers strategies as beans
  - Spring automatically injects `List<ExportStrategy>` into `ExportService`
  - Constructor-based injection used throughout
- **Benefit**: Easy testing and configuration

### 4. **Chain of Responsibility Pattern (Implicit)**
- **Purpose**: Pass request along a chain of handlers
- **Implementation**: `ExportService` iterates through strategies until finding one that can handle the ID
- **Benefit**: Flexible handler selection based on runtime conditions

---

## Project Structure

```
strategyandfactorypim/
├── src/
│   ├── main/
│   │   ├── java/com/practice/strategyandfactorypim/
│   │   │   ├── StrategyandfactorypimApplication.java      (Main entry point)
│   │   │   ├── controller/
│   │   │   │   └── ExportController.java                  (REST Controllers)
│   │   │   ├── service/
│   │   │   │   ├── ExportService.java                     (Business logic & strategy selection)
│   │   │   │   └── strategies/
│   │   │   │       ├── ExportStrategy.java                (Strategy Interface)
│   │   │   │       ├── ItemDataExportStrategy.java        (Handles IDs 0-10)
│   │   │   │       ├── UserDataExportStrategy.java        (Handles IDs 11-20)
│   │   │   │       └── UomDataStrategy.java               (Handles IDs 21-30)
│   │   │   └── exception/
│   │   │       └── ExportStrategyNotFound.java            (Custom Exception)
│   │   └── resources/
│   │       ├── application.properties                     (Spring configuration)
│   │       ├── static/                                    (Static files)
│   │       └── templates/                                 (HTML templates)
│   └── test/
│       └── java/com/practice/strategyandfactorypim/
│           └── StrategyandfactorypimApplicationTests.java (Test cases)
├── pom.xml                                                (Maven configuration)
└── README.md                                              (This file)
```

---

## Technologies Used

- **Java**: Core programming language
- **Spring Boot**: Application framework
- **Maven**: Build tool & dependency management
- **JUnit**: Unit testing framework (from Spring Boot Test starter)

---

## API Endpoints

### Export Data by ID

**Endpoint**: `GET /export/{id}`

**Description**: Exports data based on the provided ID. The appropriate export strategy is selected automatically.

**Path Parameters**:
- `id` (integer): Identifier to determine which strategy to use

**Response**: 
- **Status 202 (Accepted)**: Success with exported data message
- **Status 400 (Bad Request)**: If no valid strategy found for the given ID

**Examples**:

```bash
# Export Item Data (ID 0-10)
GET http://localhost:8080/export/5
Response: "item data exported"

# Export User Data (ID 11-20)
GET http://localhost:8080/export/15
Response: "User Data Exported"

# Export UOM Data (ID 21-30)
GET http://localhost:8080/export/25
Response: "UOM Data Exported"

# Invalid ID (no strategy found)
GET http://localhost:8080/export/100
Response: 400 Bad Request - "no valid export strategy found for ID: 100"
```

---

## Strategies Breakdown

### 1. **ItemDataExportStrategy**
- **ID Range**: 0 - 10
- **Purpose**: Handles export of item/product data
- **Export Message**: `"item data exported"`

### 2. **UserDataExportStrategy**
- **ID Range**: 11 - 20
- **Purpose**: Handles export of user/customer data
- **Export Message**: `"User Data Exported"`

### 3. **UomDataStrategy**
- **ID Range**: 21 - 30
- **Purpose**: Handles export of Unit of Measurement (UOM) data
- **Export Message**: `"UOM Data Exported"`

---

## How to Run

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Build the Project

```bash
# Using Maven wrapper
mvn clean package

# Or using Maven directly
maven clean package
```

### Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or run the JAR file
java -jar target/strategyandfactorypim-0.0.1-SNAPSHOT.jar
```

The application will start on **http://localhost:8080**

### Test the API

Use curl or Postman to test the endpoints:

```bash
curl http://localhost:8080/export/5
curl http://localhost:8080/export/15
curl http://localhost:8080/export/25
```

---

## Error Handling

### Custom Exception: `ExportStrategyNotFound`
- **Thrown When**: No valid export strategy is found for the provided ID
- **HTTP Response**: 400 Bad Request
- **Error Message**: `"no valid export strategy found for ID: {id}"`

### Exception Flow:
1. `ExportController` catches `ExportStrategyNotFound`
2. Returns HTTP 400 (Bad Request) with error message
3. Caller receives clear feedback about the invalid ID

---

## Key Features

✅ **Extensible Design**: Adding new export types requires only creating a new strategy implementation  
✅ **Clean Separation of Concerns**: Each component has a single responsibility  
✅ **Automatic Strategy Selection**: No manual routing logic needed  
✅ **Type-Safe Error Handling**: Custom exceptions for specific error scenarios  
✅ **Dependency Injection**: Loose coupling and easy testing  
✅ **RESTful API**: Clean HTTP interface for consumers  

---

## Future Improvements

1. **Configurable ID Ranges**: Move ID ranges to properties file or annotations
2. **Logging**: Add logging for debugging and audit trails
3. **Database Integration**: Store export history and metadata
4. **Additional Export Formats**: Support JSON, XML, CSV export formats
5. **Rate Limiting**: Prevent abuse of export APIs
6. **Caching**: Cache export results for frequently requested IDs
7. **Batch Export**: Support exporting multiple IDs in one request
8. **Unit Tests**: Comprehensive test coverage for all strategies
9. **API Documentation**: Swagger/OpenAPI integration for interactive documentation

---

## Contributing

To add a new export strategy:

1. Create a new class implementing `ExportStrategy`
2. Annotate with `@Component` (Spring will auto-register it)
3. Implement `canHandle(int id)` to define your ID range
4. Implement `exportData(int id)` with your export logic
5. The `ExportService` will automatically pick it up

### Example:
```java
@Component
public class ProductDataExportStrategy implements ExportStrategy {
    @Override
    public boolean canHandle(int id) {
        return (id >= 31 && id <= 40);
    }

    @Override
    public String exportData(int id) {
        return "Product data exported for ID: " + id;
    }
}
```

---

## License

This project is for educational purposes as part of Low-Level Design (LLD) practice.

---

## Author Notes

This project demonstrates practical implementation of design patterns used in real-world enterprise applications. The Strategy pattern makes the codebase flexible and maintainable, while the Dependency Injection pattern ensures loose coupling between components.
