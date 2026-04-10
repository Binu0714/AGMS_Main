# 🌱 Automated Greenhouse Management System (AGMS)

The **Automated Greenhouse Management System (AGMS)** is a cloud-native, microservices-based platform designed for **precision agriculture**.  
The system monitors environmental conditions in real-time and automates greenhouse hardware (such as fans and heaters) to maintain optimal growing conditions.

---

## 🏗️ Architecture Overview

The system is built using a **Distributed Microservices Architecture** with the following components:

### 🔹 Infrastructure Services
- **Discovery Server (Netflix Eureka)**  
  Dynamic service registration and discovery.

- **Config Server**  
  Centralized property management using a local Git/Native repository.

- **API Gateway**  
  Single entry point for all requests, handling routing and JWT validation.

---

### 🔹 Domain Microservices
- **Auth Service**  
  Manages user identity and issues secure JWT tokens.

- **Zone Service**  
  Manages greenhouse sections and temperature thresholds.  
  Integrates with external IoT providers.

- **Sensor Service**  
  Acts as a data bridge, fetching telemetry every 10 seconds with automated mock fallback.

- **Automation Service**  
  The system **"Brain"**. Processes telemetry against zone rules and logs actions.

- **Crop Service**  
  Tracks plant lifecycle batches using a strict state-machine:  
  `Seedling → Vegetative → Harvested`

---

## 🚀 Startup Instructions (Order of Operations)

To ensure the system starts correctly, services must be launched in the following order:

### 1️⃣ Infrastructure Layer

- **Eureka Server**
  - Start the `eureka_server` module  
  - Port: `8761`  
  - Dashboard: http://localhost:8761  

- **Config Server**
  - Start the `config_server` module  
  - Ensure the `config-data` folder is accessible  
  - Port: `8888`

- **API Gateway**
  - Start the `api_gateway` module  
  - Port: `8080`

---

### 2️⃣ Domain Layer

Start these services in any order once the infrastructure is up:

- **Auth Service**  
  Port: `8085` → Connects to `gms_auth_db`

- **Zone Service**  
  Port: `8081` → Connects to `gms_zone_db`

- **Sensor Service**  
  Port: `8082` → Scheduled worker

- **Automation Service**  
  Port: `8083` → Connects to `gms_automation_db`

- **Crop Service**  
  Port: `8084` → Connects to `gms_crop_db`

---

## 📡 External IoT Integration & Resilience

As per project requirements, the system communicates with a **Live External IoT Data Provider API**.

### 🔧 Implementation Details
- **Device Registration**  
  Handled via **OpenFeign** in the Zone Service

- **Telemetry Ingestion**  
  Sensor Service fetches data every 10 seconds using `RestTemplate`

- **Fault Tolerance (Fallback Mechanism)**  
  If the external API is unavailable:
  - System automatically switches to **mock data generation**
  - Ensures uninterrupted system demonstration and testing

---

## 🔐 Security Configuration

- **Global Security**  
  Implemented at the API Gateway level

- **JWT Validation**  
  All requests to `/api/**` require a valid **Bearer Token**

- **Public Endpoints**
  - `/auth/register`
  - `/auth/login`

---

## 🛠️ Technology Stack

| Category        | Technology |
|----------------|-----------|
| Framework      | Spring Boot 3.4.1 |
| Cloud Tools    | Spring Cloud (Gateway, Eureka, Config, OpenFeign) |
| Database       | MySQL (Polyglot Persistence per service) |
| Communication  | Synchronous HTTP / JSON |
| Security       | JWT & BCrypt Encryption |
| Utilities      | Lombok, ModelMapper, Jackson |

---

## 📂 Project Structure & Submission Files

- `/config-data`  
  Contains centralized YAML configuration files

- `/docs`  
  Contains **Eureka Dashboard Screenshot** showing all services **UP**

- `AGMS_Postman_Collection.json`  
  Import into Postman to test the full system flow

---

## 🧪 Testing the System (Postman)

1. **Register / Login**
   - Obtain `accessToken` from Auth Service

2. **Create Zone**
   - Set `maxTemp = 25°C`

3. **Monitor Sensors**
   - `GET /api/sensors/latest`

4. **Verify Automation**
   - `GET /api/automation/logs/{userId}`
   - Example action: `TURN_FAN_ON`

5. **Lifecycle Management**
   - Update crop status
   - Verify state-machine prevents invalid transitions

---

## 👨‍💻 Developed By

- **Name:** Binu Jinajith  

---
