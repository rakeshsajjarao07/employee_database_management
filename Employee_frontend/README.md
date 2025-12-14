# Employee Management System - Frontend

A vanilla HTML, CSS, and JavaScript frontend application for managing employees, integrated with Spring Boot backend.

## Features

- **Authentication**: Sign-in and Sign-up functionality
- **Add Employee**: Form to add new employees with image upload
- **View Employees**: View all employees in a table format with filtering
- **Search Employee**: Search for employees by ID or name
- **Department-based Access**: IT department can add employees, other departments can only view
- **Beautiful UI**: Modern design with gradient backgrounds and hover effects

## How to Run

1. **Open the application:**
   - Simply open `index.html` in your web browser
   - Or use a local server (recommended):
     ```bash
     # Using Python
     python -m http.server 3000
     
     # Using Node.js (http-server)
     npx http-server -p 3000
     ```

2. **Access the application:**
   - Open your browser and navigate to `http://localhost:3000` (if using a server)
   - Or double-click `index.html` to open directly

## Backend Configuration

The frontend is configured to connect to the Spring Boot backend at:
- **Base URL**: `http://localhost:8080`

### API Endpoints Used:

1. **POST** `/api/employees` - Add a new employee
2. **GET** `/api/employees` - Get all employees
3. **GET** `/api/employees/{id}` - Get employee by ID
4. **GET** `/api/employees/name/{name}` - Get employee by name
5. **PUT** `/api/employees/{id}` - Update employee
6. **POST** `/api/auth/signin` - Sign in user
7. **POST** `/api/auth/signup` - Sign up user

## Project Structure

```
Employee_frontend/
├── index.html          # Main HTML file
├── styles.css          # All CSS styling
├── app.js              # Main application logic
├── api.js              # API service functions
└── README.md           # This file
```

## Usage

1. **Sign Up/Sign In**: Create an account or sign in with existing credentials
2. **Add Employee** (IT Department only): Click on "Add a new Employee" card to add employees
3. **View Employees**: Click on "Get Employee Details" to view all employees or search for one
4. **Filter**: Use the filter dropdown in the employees table to sort by name or department

## Notes

- Image uploads are optional
- Employee images are displayed in circular format
- All API calls are logged to the browser console for debugging
- The application uses localStorage to persist authentication state
- Employee ID is manually entered (not auto-generated)

## Browser Compatibility

- Chrome (recommended)
- Firefox
- Safari
- Edge

## Important

- Ensure your Spring Boot backend is running on `http://localhost:8080` for full functionality
- CORS must be enabled on the backend to allow requests from the frontend
- All API endpoints must match the expected paths

