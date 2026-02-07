# Backend README for Spring Boot + Activiti + Spring Security

## Prerequisites

- Java 17+
- Maven 3.9+
- MySQL 8.0+
- Docker & Docker Compose (optional)

## Quick Start

### Local Development

1. **Database Setup**
   ```bash
   mysql -u root -p
   CREATE DATABASE workflow_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **Build the Project**
   ```bash
   cd backend
   mvn clean package
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
   ```
   
   The application will start on `http://localhost:8080/api`

### Using Docker Compose

```bash
cd backend
docker-compose up -d
```

## Default Credentials

- **Admin User**: `admin` / `admin123`
- **Regular User**: `user` / `user123`

## API Endpoints

### Authentication

- `POST /api/auth/login` - Login and get JWT token
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

- `POST /api/auth/register` - Register new user
  ```json
  {
    "username": "newuser",
    "password": "password123",
    "email": "user@example.com"
  }
  ```

- `GET /api/auth/me` - Get current user info (requires authentication)

### Process Management

- `POST /api/process/start` - Start a process
- `GET /api/process/my-tasks` - Get user's tasks
- `POST /api/process/task/{taskId}/complete` - Complete a task

## Environment Variables

- `JWT_SECRET` - JWT signing key (default: configured in application.yml)
- `DB_USERNAME` - Database user
- `DB_PASSWORD` - Database password

## Configuration

Edit `src/main/resources/application.yml` to customize:
- Database connection
- JWT expiration time
- Server port

## Testing

```bash
mvn test
```

## Notes

- Change JWT_SECRET in production
- Use strong database passwords
- Keep sensitive configs out of version control
