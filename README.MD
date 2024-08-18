# Recipe Management Application

## Overview

This is a standalone Java application that allows users to manage their favorite recipes. The application provides functionalities to add, update, remove, and fetch recipes, with advanced filtering options based on various criteria such as vegetarian status, number of servings, specific ingredients, and text search within instructions.

## Features

- **Add, Update, Remove, and Fetch Recipes**: Users can easily manage their recipes.
- **Advanced Filtering**:
    - Filter by vegetarian status
    - Filter by the number of servings
    - Include or exclude specific ingredients
    - Text search within instructions
- **RESTful API**: Built using Spring Boot, ensuring a clean and efficient interface.
- **Database Persistence**:
    - PostgreSQL is used for production.
    - H2 is used for local and Docker environments.
- **Authentication**: Basic Auth is implemented for secure access.
- **Documentation**: Comprehensive API documentation is provided.

## Requirements

- Java (preferably version 21)
- Maven
- Docker
- HashiCorp Vault for secret management

## Architectural Choices

- **Framework**: Spring Boot is used for its simplicity and power in building RESTful applications.
- **Database**:
    - H2 for local and Docker environments for easy setup and testing.
    - PostgreSQL for production to ensure robustness and scalability.
- **Search Functionality**: Hibernate Search with Lucene is utilized for efficient searching capabilities.
- **Containerization**: Docker is used to simplify deployment and ensure consistency across environments.
- **Testing**: Unit and integration tests are included to ensure code quality and reliability. Functional Tests are written with Cucumber.

## Getting Started

### Prerequisites

1. **Java 21**: Ensure you have Java installed on your machine.
2. **Maven**: Install Maven for building the application.
3. **Docker**: Install Docker to run the application in a containerized environment.

### Running the Application

1. **Build the Application**:
   ```bash
   mvn clean install -DskipTests
   ```

2. **Run the Application**
- Local Profile
  ```bash
  mvn spring-boot:run -Dspring.profiles.active=local
  ```
- Docker Profile
  ```bash
  mvn spring-boot:run -Dspring.profiles.active=docker
  ```
- Production Profile
  ```bash
  docker compose up -d --build
  ```
Please note that docker profile uses ```-Dspring.profiles.active=prod```

### Running Tests
To run tests, ensure all Docker containers except the application are up and execute the following command:
  ```bash
  mvn clean test
  ```
### Accessing the Application

- Swagger UI: Access the API documentation at http://localhost:8080/swagger-ui.html
- Grafana: Access Grafana at http://localhost:3000
- Prometheus: Access Prometheus at http://localhost:9090
- API Docs: Access v3 API documentation at http://localhost:8080/v3/api-docs

### Authentication
Use the following credentials to access the application:

    Username: recipeuser
    Password: recipepass

### API Endpoints
The application exposes various RESTful API endpoints to manage recipes. Below are some examples:

    Add a Recipe: POST /api/recipes
    Update a Recipe: PUT /api/recipes/{id}
    Delete a Recipe: DELETE /api/recipes/{id}
    Fetch All Recipes: GET /api/recipes
    Filter Recipes: POST /api/recipes/search
        {
          "vegetarian": true,
          "servings": 3,
          "includeIngredients": ["lemon", "pasta"],
          "excludeIngredients": ["onions", "garlic"],
          "instructionSearch": "Season salmon serve"
        }

Feel free to explore, contribute, and enjoy managing your favorite recipes!