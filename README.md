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

## Sample curl Commands

### 1. Discovery Endpoint
curl -X GET http://localhost:8080/api/v1/

### 2. Create a Room
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":50}"

### 3. Get All Rooms
curl -X GET http://localhost:8080/api/v1/rooms

### 4. Get a Specific Room
curl -X GET http://localhost:8080/api/v1/rooms/LIB-301

### 5. Delete a Room
curl -X DELETE http://localhost:8080/api/v1/rooms/LIB-301

## API Endpoints
- GET /api/v1/ - Discovery endpoint
- GET /api/v1/rooms - Get all rooms
- POST /api/v1/rooms - Create a room
- GET /api/v1/rooms/{roomId} - Get a specific room
- DELETE /api/v1/rooms/{roomId} - Delete a room