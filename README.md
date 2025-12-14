# Employee Management System (EMS)

A full-featured Employee Management System frontend built using **HTML, CSS, and Vanilla JavaScript**, designed to integrate seamlessly with a **Spring Boot REST API backend**.

This project supports **authentication, role-based access, employee CRUD operations, image handling, filtering, and search**, all without using React or any frontend framework.

---

## 🔧 Tech Stack

### Frontend
- HTML5  
- CSS3 (Glassmorphism UI + Animations)  
- Vanilla JavaScript (ES6+)  
- Fetch API  
- LocalStorage (Session handling)  

### Backend (Integrated)
- Spring Boot REST APIs  
- Multipart & JSON-based requests  
- Image Upload & Base64 Image Handling  

---

## 🚀 Features Implemented

### 🔐 Authentication & Authorization
- **User Sign Up**
  - Username  
  - Employee ID  
  - Email  
  - Department (with “Other” option)  
  - Password & Confirm Password  
  - Mandatory profile image upload  
  - Image preview before submission  

- **User Sign In**
  - Username  
  - Employee ID  
  - Department  
  - Password  

- Session persistence using LocalStorage  
- Automatic login state check on page refresh  
- Logout functionality with session cleanup  

---

### 👤 User Profile & Session UI
- User profile avatar displayed in header  
- Dropdown profile card showing:
  - Username  
  - Employee ID  
  - Employee Name (fetched dynamically)  
- Auto-fetches employee image if user image is unavailable  
- Graceful fallback to placeholder image  

---

### 🏠 Home Page Experience
- Separate UI for:
  - Before Login  
  - After Login  
- Animated greeting message  
- Interactive action cards  
- Dynamic rendering based on user role  

---

### 🛂 Role-Based Access Control
- **IT & HR users**
  - Can add new employees  
  - Can view all employees  

- **Non-IT/HR users**
  - Read-only access to employee details  

- Access control handled fully on frontend logic  

---

### ➕ Add Employee Module
- Complete employee form with validation:
  - Employee ID  
  - Name  
  - Email  
  - Phone  
  - Department  
  - Position  
  - Salary  
  - Date of Joining  
  - Address  

- Optional employee image upload  
- Live image preview  
- Remove / Change image before submit  
- Multipart form submission to backend  
- Error handling with user-friendly messages  
- Automatic redirect after successful creation  

---

### 📋 View All Employees
- Fetches all employees from backend  
- Displays employees in card-based grid layout  
- Each card includes:
  - Profile image (if available)  
  - Employee ID  
  - Employee Name  

- Clickable cards open detailed employee view  

---

### 🔍 View Single Employee
- Search employee by:
  - Employee ID  
  - Employee Name  

- Dynamic input placeholder switching  
- Full employee detail card:
  - Image  
  - Personal details  
  - Job details  
  - Address  

- Proper error handling when employee not found  

---

### 🧮 Filtering System
- Filter employees by:
  - Department  
  - Designation  

- Filter applied dynamically without page reload  
- Filter dropdown with smooth animation  

---

### 🖼 Image Handling
- Supports:
  - Multipart image upload (Add Employee)  
  - Base64 image upload (Sign Up)  

- Auto image formatting for:
  - Base64 images  
  - Backend-served image paths  

- Graceful fallback when image loading fails  

---

### 🔄 Navigation & UX
- SPA-like navigation (no page reloads)  
- Page visibility managed using CSS classes  
- Back buttons for all major flows  
- Click-outside detection for dropdowns  
- Loading indicators while fetching data  

---

### 🎨 UI & Styling
- Modern Glass morphism design  
- Animated gradient background  
- Responsive design (Mobile / Tablet / Desktop)  
- Smooth hover effects and transitions  
- Fully custom CSS (no libraries)  

---

### ⚙️ Configuration Support
- Centralized API configuration via `config.js`  
- Easily switch backend URL  
- Modular API service layer (`api.js`)  
- Clean separation of concerns  

---

## 📁 Project Structure for frontend:

```
├── index.html      # UI Structure
├── styles.css      # Complete Styling & Animations
├── app.js          # Application Logic & State
├── api.js          # Backend API Integration
├── config.js       # Environment Configuration
└── README.md       # Project Documentation
```

## 📁 Project Structure for backend:

backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/ems/
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   └── EmployeeController.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   └── EmployeeService.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java
│   │   │       │   └── EmployeeRepository.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── User.java
│   │   │       │   └── Employee.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── SignUpRequest.java
│   │   │       │   ├── SignInRequest.java
│   │   │       │   └── EmployeeResponse.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── WebConfig.java
│   │   │       │
│   │   │       └── EmployeeManagementApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── uploads/
│   │
│   └── test/
│       └── java/
│           └── com/example/ems/
│
├── pom.xml
└── README.md

---


## ✅ Highlights 
- Clean, modular JavaScript architecture  
- Fully production-ready UI  
- Role-based access logic  
- Robust image handling  
- Backend-friendly request formats  

---

GET-CODE from here : https://github.com/rakeshsajjarao07/employee_database_management

developed by:
-------------
NAME: Sajjarao Rakesh

linkedin: https://www.linkedin.com/in/sajjarao-rakesh-837234281/
