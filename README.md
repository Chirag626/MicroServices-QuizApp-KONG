# MicroServices Project

A Spring Boot microservices project with Eureka service discovery, API Gateway, and PostgreSQL databases.

## Services

| Service | Port |
|---------|------|
| Service Registry (Eureka) | 8761 |
| API Gateway | 8765 |
| Question Service | 8080 |
| Quiz Service | 8081 |
| Postgres (Question) | 5433 |
| Postgres (Quiz) | 5434 |

## Prerequisites

- Docker Desktop installed

## Setup & Run

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd MicroServices-Project
```

### 2. Create `.env` file
```bash
cp .env.example .env
```

Open `.env` and set your passwords:
```env
QUESTION_DB=questiondb
QUESTION_DB_USER=postgres
QUESTION_DB_PASSWORD=your_password

QUIZ_DB=quizdb
QUIZ_DB_USER=postgres
QUIZ_DB_PASSWORD=your_password
```

### 3. Run the project
```bash
docker-compose up --build
```

### 4. Access Services
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8765
- Question Service: http://localhost:8080
- Quiz Service: http://localhost:8081
