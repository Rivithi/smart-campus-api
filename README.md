# Smart Campus API

A JAX-RS RESTful API for managing Rooms and Sensors in a Smart Campus environment.

## API Overview
This API provides endpoints to manage Rooms, Sensors, and Sensor Readings for the University's Smart Campus initiative. Built using Jersey (JAX-RS) with an embedded Grizzly server.

## How to Build and Run

### Prerequisites
- Java JDK 17 or higher
- Apache Maven
- Apache NetBeans (recommended)

### Steps to Run
1. Clone the repository:
   git clone https://github.com/Rivithi/smart-campus-api.git
2. Open the project in NetBeans
3. Right-click Main.java and select Run File
4. Server starts at http://localhost:8080/api/v1/

## API Endpoints
- GET /api/v1/ - Discovery endpoint
- GET /api/v1/rooms - Get all rooms
- POST /api/v1/rooms - Create a room
- GET /api/v1/rooms/{roomId} - Get a specific room
- DELETE /api/v1/rooms/{roomId} - Delete a room
- GET /api/v1/sensors - Get all sensors (optional ?type= filter)
- POST /api/v1/sensors - Create a sensor
- GET /api/v1/sensors/{sensorId} - Get a specific sensor
- GET /api/v1/sensors/{sensorId}/readings - Get all readings for a sensor
- POST /api/v1/sensors/{sensorId}/readings - Add a reading for a sensor

## Sample curl Commands

### 1. Discovery Endpoint
curl -X GET http://localhost:8080/api/v1/

### 2. Create a Room
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":50}"

### 3. Create a Sensor
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-001\",\"type\":\"Temperature\",\"status\":\"ACTIVE\",\"currentValue\":22.5,\"roomId\":\"LIB-301\"}"

### 4. Add a Sensor Reading
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\":25.5}"

### 5. Get All Sensors filtered by type
curl -X GET "http://localhost:8080/api/v1/sensors?type=Temperature"

## Report - Answers to Questions

### Part 1 - JAX-RS Lifecycle
By default, JAX-RS creates a new instance of a Resource class for every incoming request (per-request lifecycle). This means shared state cannot be stored in instance variables. To manage in-memory data structures safely across requests, static variables must be used with proper synchronization to prevent race conditions and data loss.

### Part 1 - HATEOAS
Hypermedia as the Engine of Application State (HATEOAS) is considered advanced RESTful design because it makes APIs self-discoverable. Clients receive links to related resources in responses, so they don't need to hardcode URLs. This reduces client-server coupling and makes the API easier to navigate without relying solely on static documentation.

### Part 2 - IDs vs Full Objects
Returning full room objects provides all the data a client needs in one request, reducing the number of API calls. However, it increases network bandwidth usage. Returning only IDs is more lightweight but requires the client to make additional requests to fetch details, increasing complexity and round trips.

### Part 2 - DELETE Idempotency
The DELETE operation is idempotent in this implementation. The first DELETE request removes the room and returns 200 OK. Subsequent DELETE requests for the same room return 404 Not Found since the room no longer exists. The server state remains consistent regardless of how many times the request is sent.

### Part 3 - @Consumes Annotation
If a client sends data in a format other than application/json (e.g., text/plain or application/xml), JAX-RS will return a 415 Unsupported Media Type error. The @Consumes annotation tells the JAX-RS runtime to only accept requests with the specified media type, rejecting all others automatically.

### Part 3 - @QueryParam vs Path Parameter
Query parameters are preferred for filtering and searching because they are optional and do not change the resource identity. Using a path parameter like /sensors/type/CO2 implies CO2 is a unique resource, which is semantically incorrect. Query parameters like ?type=CO2 clearly indicate filtering of a collection.

### Part 4 - Sub-Resource Locator Pattern
The Sub-Resource Locator pattern improves code organization by delegating nested resource logic to dedicated classes. Instead of defining all paths in one massive controller, each resource class handles its own responsibilities. This makes the code more maintainable, testable, and easier to understand as the API grows.

### Part 5 - HTTP 422 vs 404
HTTP 422 is more semantically accurate than 404 when a referenced resource is missing inside a valid JSON payload. A 404 implies the requested URL was not found, but in this case the URL is valid — it's the roomId inside the request body that doesn't exist. HTTP 422 specifically means the request was well-formed but contained invalid data.

### Part 5 - Security Risk of Stack Traces
Exposing internal Java stack traces to external API consumers is a serious security risk. Stack traces reveal internal class names, method names, file paths, and line numbers. An attacker can use this information to identify vulnerabilities, understand the application structure, and craft targeted attacks such as injection or exploitation of known library vulnerabilities.