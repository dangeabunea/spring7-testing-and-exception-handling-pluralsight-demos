# Pluralsight: Testing and Exception Handling in Spring Framework 7

## Overview

 This repository contains demo code for the "Testing and Exception Handling in Spring Framework 7" course on Pluralsight. The demos implement a flight management system for an airline company,
 demonstrating testing strategies and exception handling in Spring applications.

## Project Structure

 ### Module Organization

 The repository is divided into six modules (m1 through m6), each functioning as an independent Spring Boot Maven project:

 - **m1**: Getting Started with Testing in Spring Applications
 - **m2**: Unit Testing Domain Entities and Services with JUnit and Mockito
 - **m3**: Integration Testing for Controllers
 - **m4**: Integration Testing for Repositories
 - **m5**: Implementing Effective User Input Validation
 - **m6**: Centralized Exception Handling

 ### Directory Layout

 Each module contains:
 - `before/` - Starting code for course exercises
 - `complete/` - Reference implementation showing completed solutions

## Requirements

 ### Required Software

 - **Java Development Kit (JDK) 21** or higher
 - **Spring Framework 7** / **Spring Boot 4**
 - **Maven 3.9+** for building and executing projects
 - **Docker** (optional, required only for Testcontainers demos in Module 4)
 - **Postman** or any HTTP client for testing REST endpoints

## Running Applications

### Build a Module

 ```bash
 cd m1/complete
 mvn clean install
 ```

 ### Run Tests

 ```bash
 mvn test
 ```

## Key Notes

 - Each module operates independently with its own POM configuration
 - "Before" directories are designed for interactive learning during course progression
 - "Complete" directories serve as reference implementations for comparison
 - The project structure supports progressive learning of testing and exception handling concepts across all six modules

## Troubleshooting

 ### Port Already in Use

 Change the port in `application.properties`:

 ```properties
 server.port=8081
 ```

 ### Testcontainers Not Starting

 Ensure Docker is running:

 ```bash
 docker ps
 ```

 ---

 **Note**: This is demo code for educational purposes. Production implementations would require additional security, monitoring, and scalability considerations.