# DesignDesk

DesignDesk is a web application for freelance graphic designers to manage their clients, design projects, deadlines, prices, and revision requests in one place.

The project was created as my final project during the Everyone Codes Software Development program and focuses mainly on backend development with Spring Boot, PostgreSQL, and a simple Thymeleaf frontend.

## Features

- Create, view, edit, and delete clients
- Create, view, edit, and delete design projects
- Assign projects to clients
- Track project type, status, deadline, price, and description
- Filter projects by status
- Add revision requests to projects
- Mark revisions as completed
- View all projects belonging to a client
- Form validation for clients and projects
- Custom 404 and 500 error pages
- Responsive frontend layout

## Technologies

### Backend
- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Database
- PostgreSQL

### Frontend
- Thymeleaf
- HTML
- CSS
- JavaScript

### Testing
- JUnit 5
- Mockito

### Development Tools
- IntelliJ IDEA
- Maven
- Git
- GitHub

## Project Structure

The application follows a layered architecture:

```text
src/main/java/com/emmahare/designdesk
├── controller
├── exception
├── model
├── repository
└── service