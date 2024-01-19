# Angular and Spring Boot Project - TODO list
<img width="946" alt="Screenshot 2024-01-19 at 11 56 43 AM" src="https://github.com/anandhumohan/todo-web-app/assets/5299761/7aa34e88-bcd0-4717-9da9-353bee4fe7d9">


This is a simple todo web application built using Angular and Spring Boot. It provide a features like create/edit/delete lists.

Project is compiled using java 17 and springboot 3

## Prerequisites

- **Java Development Kit (JDK)**: Make sure you have Java 17 or higher installed.
- **Node.js and npm**: Ensure you have Node.js and npm installed on your machine.
- **Angular CLI**: You'll need the Angular CLI to manage and build the Angular frontend. Install it globally using `npm install -g @angular/cli`.

## Getting Started

Follow these steps to get the project up and running:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/anandhumohan/todo-web-app.git
   cd todo-web-app
   ```
2. **Build and Run the Frontend**:

   ```bash
   cd /todo-web-app/src/main/resources/frontend
   ng serve
   ```
3. **Build and Run the Backend**:

   ```bash
   ./gradlew build
   java -jar build/libs/todo-web-app.jar
   ```

   The Spring Boot backend should now be running on `http://localhost:8080`.

4. **Access the Application**:

   Open your web browser and visit `http://localhost:8080` to access the application.

5. **Improvements**:

   1) Checkbox buttons actions.
   2) Popups for after deleting one list.
   3) Test cases for frontend.
