// =======================================
// API CONFIG (Uses config.js BACKEND_URL)
// =======================================

const API_BASE = config.BACKEND_URL;

// =======================================
// API SERVICE OBJECT
// =======================================

const employeeAPI = {

    /* -----------------------------
       SIGN UP (Base64 image, JSON)
    ----------------------------- */
    signUp: async (payload) => {
        const res = await fetch(`${API_BASE}${config.API_ENDPOINTS.SIGNUP}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || "Signup failed");
        }
        return res.json();
    },

    /* -----------------------------
       SIGN IN (JSON)
    ----------------------------- */
    signIn: async (payload) => {
        const res = await fetch(`${API_BASE}${config.API_ENDPOINTS.SIGNIN}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || "Signin failed");
        }
        return res.json();
    },

    /* -----------------------------
       GET ALL EMPLOYEES
    ----------------------------- */
    getAllEmployees: async () => {
        const res = await fetch(`${API_BASE}${config.API_ENDPOINTS.GET_EMPLOYEES}`);

        if (!res.ok) {
            return []; // safe fallback
        }
        return res.json();
    },

    /* -----------------------------
       GET Employee BY ID
    ----------------------------- */
    getEmployeeById: async (id) => {
        const endpoint = config.API_ENDPOINTS.GET_EMPLOYEE_BY_ID.replace(":id", id);
        const res = await fetch(`${API_BASE}${endpoint}`);

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || "Employee not found");
        }
        return res.json();
    },

    /* -----------------------------
       GET Employee BY NAME
    ----------------------------- */
    getEmployeeByName: async (name) => {
        const endpoint = config.API_ENDPOINTS.GET_EMPLOYEE_BY_NAME.replace(":name", name);
        const res = await fetch(`${API_BASE}${endpoint}`);

        if (!res.ok) return [];
        return res.json();
    },

    /* =====================================================
       ADD EMPLOYEE — FormData (Multipart File Upload)
       (Used for: Add Employee)
    ===================================================== */
    addEmployeeFormData: async (formData) => {

        const res = await fetch(`${API_BASE}/api/employees/form`, {
            method: "POST",
            body: formData
        });

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || "Failed to add employee");
        }

        return res.json();
    },

    /* =====================================================
       UPDATE EMPLOYEE — FormData (Multipart Update)
       (Used for: Edit Employee)
    ===================================================== */
    updateEmployee: async (id, formData) => {

        const endpoint = config.API_ENDPOINTS.UPDATE_EMPLOYEE.replace(":id", id);

        const res = await fetch(`${API_BASE}${endpoint}`, {
            method: "PUT",
            body: formData
        });

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || "Failed to update employee");
        }

        return res.json();
    }
};
