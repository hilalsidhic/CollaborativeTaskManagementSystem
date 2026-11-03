# TaskMaster API

Welcome to the official backend repository for **TaskMaster**, a powerful task tracking and management application designed for seamless team collaboration. This document provides all the necessary information to understand, run, and contribute to the project.

Check whether the application is up - https://stats.uptimerobot.com/45azxgmHZS

---

## 🧭 Overview

**TaskMaster** is a robust backend system built with **Java** and **Spring Boot** that facilitates organization and productivity within teams.  
It allows users to create, assign, and track tasks, collaborate through comments and attachments, and manage projects efficiently.  
The system is built on a **secure, stateless authentication model** using **JSON Web Tokens (JWT)**.

---

## ✨ Features

### 🧍‍♂️ User Management & Security
- ✅ **Secure Registration:** Users can create accounts with hashed passwords.  
- ✅ **JWT-Based Authentication:** Users log in and receive secure, stateless tokens.  
- ✅ **Profile Management:** View and update user profile details.  
- ✅ **Secure Logout:** Stateless session — users log out by discarding their token.

### ✅ Task Management
- 🗂 **CRUD Operations:** Create, Read, Update, Delete tasks.  
- 🏷 **Task Attributes:** Title, description, due date, and status.  
- 🔍 **Filter & Search:** Filter by status or search by title/description.  
- 🕓 **Status Update:** Mark tasks as completed.

### 👥 Collaboration & Team Features *(Upcoming)*
- 🧱 Project/Team creation and management.  
- 📩 Invite users to join projects.  
- 📎 Comments and attachments for better collaboration.

---

## 🛠️ Technology Stack

| Layer | Technology |
|-------|-------------|
| Backend | Java 17, Spring Boot 3.x |
| Security | Spring Security 6, JWT |
| Database | Spring Data JPA (PostgreSQL/MySQL) |
| Build Tool | Apache Maven |
| API | RESTful |

---

## 🚀 Getting Started

Follow these steps to set up and run the project locally.

### 🔧 Prerequisites
- JDK 17 or higher  
- Apache Maven  
- A running SQL database (e.g., PostgreSQL)

### 📦 Installation & Running

```bash
# Clone the repository
git clone https://github.com/hilalsidhic/CollaborativeTaskManagementSystem.git
cd CollaborativeTaskManagementSystem

# Configure the database
# Edit src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmaster_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

# JWT Secret Key
application.security.jwt.secret-key=your_super_strong_and_long_secret_key_here

# Build and run
mvn clean install
mvn spring-boot:run
```

The app runs at [http://localhost:8080](http://localhost:8080).

---

## 🔌 API Endpoints

### **Authentication** (`/auth`)
| Method | Endpoint | Description | Access |
|--------|-----------|-------------|---------|
| POST | `/signup` | Register a new user | Public |
| POST | `/login` | Log in to receive JWT | Public |

### **Profile** (`/profile`)
| Method | Endpoint | Description | Access |
|--------|-----------|-------------|---------|
| GET | `/` | Get logged-in user's profile | Protected |
| PUT | `/` | Update user's profile | Protected |

### **Teams** (`/teams`)
| Method | Endpoint | Description | Access |
|--------|-----------|-------------|---------|
| POST | `/` | Create a team (creator becomes ADMIN) | Authenticated |
| PUT | `/{teamId}/adduser?userEmail=...` | Add a user as a USER member | Team ADMIN or MANAGER |

Only team members with the `ADMIN` or `MANAGER` role may add members. Other roles and users who are not members receive HTTP `403 Forbidden`.

### **Tasks** (`/tasks`) *(To Be Implemented)*
| Method | Endpoint | Description | Access |
|--------|-----------|-------------|---------|
| POST | `/` | Create a task | Protected |
| GET | `/` | Get all tasks (with filters/search) | Protected |
| GET | `/{taskId}` | Get specific task details | Protected |
| PUT | `/{taskId}` | Update a task | Protected |
| DELETE | `/{taskId}` | Delete a task | Protected |
| POST | `/{taskId}/complete` | Mark a task as complete | Protected |
| POST | `/{taskId}/assign/{userId}` | Assign a task to a user | Protected |

---

## ⚠️ Error Handling

A global `@ControllerAdvice` handles validation errors, missing resources, and exceptions — returning structured JSON error responses.

---

## 🗺 Roadmap

- [ ] Implement task and project endpoints  
- [ ] Add unit & integration tests (JUnit, Mockito)  
- [ ] Add WebSocket-based real-time notifications *(optional)*  

---

## 🧑‍💻 Contributing

Contributions are welcome!  
Fork the repo, make your changes, and submit a pull request.

---

## 📜 License

This project is licensed under the **MIT License** — feel free to use and modify.

---

**Made with ❤️ by Hilal**
