// =======================================
// FRONTEND CONFIGURATION FILE
// =======================================

// Change BACKEND_URL only if your Spring Boot runs on a different IP/Port.
const config = {

    FRONTEND_PORT: 3000,

    // Backend Spring Boot Server URL
    BACKEND_URL: "http://localhost:8080",

    // API Endpoints used across the frontend
    API_ENDPOINTS: {
        SIGNUP: "/api/auth/signup",
        SIGNIN: "/api/auth/signin",

        GET_EMPLOYEES: "/api/employees",
        GET_EMPLOYEE_BY_ID: "/api/employees/:id",
        GET_EMPLOYEE_BY_NAME: "/api/employees/name/:name",

        ADD_EMPLOYEE: "/api/employees/form", 
        UPDATE_EMPLOYEE: "/api/employees/:id"
    }
};


// Export if file is used inside Node.js (optional)
if (typeof module !== "undefined" && module.exports) {
    module.exports = config;
}
