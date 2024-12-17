Spring Boot Quiz Api's

Project Overview
This is a Spring Boot-based Quiz Api's that allows users to take a randomized quiz with 5 questions, tracking answers and session state.


Prerequisites

Java 17 or higher
Maven 3.6+
PostgreSQL Database


Project Structure

Copysrc/
├── main/
│   ├── java/
│   │   └── demo/
│   │       └── id/
│   │           ├── Main.java
│   │           ├── db.java
│   │           └── SpringBootProjectApplication.java
│   └── resources/
│       └── application.properties
pom.xml


Database Connection

Update the database connection details in db.java:
javaCopypublic String url = "jdbc:postgresql://localhost:5432/demo";
public String usename = "postgres";
public String password = "123456";


Installation Steps

1. Clone the Repository
bashCopygit clone https://github.com/yourusername/spring-boot-quiz-app.git
cd spring-boot-quiz-app

3. Build the Project
bashCopymvn clean install

5. Run the Application
bashCopymvn spring-boot:run


API Endpoints

Start Quiz

URL: /api/start
Method: GET
Description: Initializes a new quiz session and returns the first question

Submit Answer

URL: /api/submit/{ans}
Method: GET
Description: Submits an answer and proceeds to the next question


Troubleshooting

Ensure PostgreSQL is running
Verify database connection credentials
Check that all Maven dependencies are downloaded
Confirm Java 17 is installed and configured

Dependencies

Spring Boot Web
Spring Session
PostgreSQL JDBC Driver
Jackson JSON
Java JSON API
