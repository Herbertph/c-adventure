# C-Adventure

An interactive **C# course platform** built with a **microservices architecture**, featuring user authentication, lesson management, code execution, and progress tracking.

The application is **containerized with Docker** and **deployed on Railway**.

---

## Architecture

The project consists of three main components:

- **Frontend**  
  A Vue.js single-page application (SPA) built with Vite, providing the user interface for the course.

- **Auth-Service**  
  A Spring Boot microservice handling user registration, login, and JWT-based authentication.

- **Lesson-Service**  
  A Spring Boot microservice managing lessons, user progress, and code execution for C# exercises.

Services communicate via **REST APIs**, with **PostgreSQL** databases for data persistence.  
The frontend consumes APIs from both backend services.

---

## Microservices Documentation

### Auth-Service

The Auth-Service is responsible for user management and authentication.  
It uses **JWT tokens** for secure access and integrates with a **PostgreSQL** database.

#### Key Endpoints

- `POST /auth/register`  
  Registers a new user.  
  **Body:** email, password, name  
  **Response:** success message

- `POST /auth/login`  
  Authenticates a user.  
  **Body:** email, password  
  **Response:** JWT token

- `GET /auth/me`  
  Retrieves the current authenticated user's details.  
  **Headers:** `Authorization: Bearer <token>`  
  **Response:** user information

- `GET /auth/health`  
  Health check endpoint for monitoring service status

---

### Lesson-Service

The Lesson-Service handles lesson content, user progress, and code execution.  
It includes **admin endpoints** for lesson management and uses **JWT authentication**.

#### Key Endpoints

- `GET /lessons`  
  Retrieves a list of all lessons

- `GET /lessons/{id}`  
  Retrieves details of a specific lesson by ID

- `POST /lessons/submit`  
  Submits user code for a lesson exercise  
  **Body:** lesson ID, code, user context  
  Executes and validates the C# code

- `POST /progress`  
  Updates user progress for a lesson  
  **Body:** user ID, lesson ID

- `GET /progress/{userId}`  
  Retrieves progress data for a specific user

- `POST /lessons` _(Admin)_  
  Creates a new lesson  
  **Requires:** admin secret, lesson data (title, content)

- `PUT /lessons/{id}` _(Admin)_  
  Updates an existing lesson  
  **Requires:** admin secret

- `DELETE /lessons/{id}` _(Admin)_  
  Deletes a lesson  
  **Requires:** admin secret

- `POST /execute`  
  Executes arbitrary C# code for testing  
  **Body:** code

- `GET /health`  
  Health check endpoint for monitoring service status

---

## Frontend

The frontend is a **Vue.js** application with:

- Routing
- State management via **Pinia**
- API integration using **Axios**

It provides pages for authentication, lesson viewing, and progress tracking.

### Key Features

- **HomePage**  
  Landing page with course overview

- **LoginPage / RegisterPage**  
  User authentication forms

- **LessonCardsPage**  
  Displays available lessons

- **LessonView**  
  Interactive lesson content with code editor and submission

- **UserPage**  
  User profile and progress tracking

The frontend uses environment variables to connect to backend services:

```env
VITE_API_AUTH_URL
VITE_API_LESSON_URL

## Setup and Deployment

### Prerequisites

- Docker and Docker Compose
- Java 17 and Maven
- Node.js 20 and npm

---

### Local Development

1. Clone the repository

2. Start all services with Docker Compose:

```bash
docker-compose up

### Or run services individually

#### Auth-Service

```bash
mvn spring-boot:run

Runs on port `8080`

#### Lesson-Service

```bash
mvn spring-boot:run
Runs on port 8082