 /* =====================================================

   app.js - FINAL CLEAN VERSION (2025)

   - Stable Auth (SignIn / SignUp)

   - DP fix + logout reset

   - Add Employee / Edit Employee

   - Employee Detail Page

   - Dashboard updates live after login

===================================================== */



let currentUser = null;

let allEmployees = [];

let selectedEmployee = null;

let isEditMode = false;

let editingEmployeeId = null;



/* -------------------- INIT -------------------- */

document.addEventListener("DOMContentLoaded", () => {

    wireUI();

    restoreLoginState();

    showOnlyHome();

    renderSidebarAccess();

});



/* -------------------- UI Events -------------------- */

function wireUI() {



    const sidebar = document.getElementById("sidebar");

    const menuBtn = document.getElementById("menu-toggle");



    /* Sidebar Toggle */

    menuBtn?.addEventListener("click", (e) => {

        e.stopPropagation();

        sidebar.classList.toggle("active");

    });



    /* Outside click handling */

    document.addEventListener("click", (e) => {

        if (!e.target.closest("#sidebar") && !e.target.closest("#menu-toggle")) {

            sidebar.classList.remove("active");

        }



        if (!e.target.closest(".user-mini")) {

            document.getElementById("user-details-pop").style.display = "none";

        }



        const authPanel = document.getElementById("auth-panel");

        if (

            authPanel.style.display === "block" &&

            !e.target.closest(".auth-card") &&

            !e.target.closest("#top-signin-btn") &&

            !e.target.closest("#sidebar-auth-btn")

        ) {

            closeAuthPanel();

        }

    });



    /* Navigation */

    document.querySelectorAll(".nav-btn").forEach(btn =>

        btn.addEventListener("click", () => gotoPage(btn.dataset.page))

    );



    /* Sign-in buttons */

    document.getElementById("top-signin-btn")?.addEventListener("click", () => openAuth(true));

    document.getElementById("sidebar-auth-btn")?.addEventListener("click", () =>

        currentUser ? logout() : openAuth(true)

    );



    /* Auth switching */

    document.getElementById("open-signup")?.addEventListener("click", showSignup);

    document.getElementById("open-signin")?.addEventListener("click", showSigninCard);



   



    /* Forms */

    document.getElementById("signin-form")?.addEventListener("submit", login);

    document.getElementById("signup-form")?.addEventListener("submit", signup);

    document.getElementById("add-employee-form")?.addEventListener("submit", submitEmployeeForm);

    document.getElementById("edit-employee-form")?.addEventListener("submit", updateEmployee);



    /* Back buttons */

    document.getElementById("btn-back")?.addEventListener("click", () => gotoPage("employees"));

    document.getElementById("edit-back-btn")?.addEventListener("click", () => gotoPage("employee-detail"));

   



    /* DP Popup */

    document.getElementById("user-mini-img")?.addEventListener("click", (e) => {

        e.stopPropagation();

        toggleUserPopup();

    });



    /* Popup sign-out button */

    document.getElementById("pop-signout-btn")?.addEventListener("click", () => {

        logout();

        document.getElementById("user-details-pop").style.display = "none";

    });

    document.getElementById("download-excel-btn")

    ?.addEventListener("click", downloadExcel);



document.getElementById("employee-search")

    ?.addEventListener("input", filterEmployees);

document.getElementById("btn-download-pdf")?.addEventListener("click", downloadEmployeePDF);





}



//search employee function

function filterEmployees(e) {

    const text = e.target.value.toLowerCase();



    const filtered = allEmployees.filter(emp =>

        (emp.employeeName || "").toLowerCase().includes(text) ||

        (emp.employeeCode || "").toLowerCase().includes(text)

    );



    renderEmployeeList(filtered);

    renderEmployeeTable(filtered);

}

// function downloadExcel() {

//     window.location.href = `${config.BACKEND_URL}/api/employees/download/excel`;

// }



//donload all employee

async function downloadExcel() {

    try {

        const response = await fetch(`${config.BACKEND_URL}/api/employees/download/excel`, {

            method: "GET"

        });



        const blob = await response.blob();

        const url = window.URL.createObjectURL(blob);



        const a = document.createElement("a");

        a.href = url;

        a.download = "employee-data.xlsx";

        document.body.appendChild(a);

        a.click();

        document.body.removeChild(a);



    } catch (err) {

        alert("Failed to download Excel");

    }

}



// async function downloadExcel() {

//     try {

//         const response = await fetch(`${config.BACKEND_URL}/api/employees/download/excel`, {

//             method: "GET"

//         });



//         if (!response.ok) {

//             throw new Error("Download failed");

//         }



//         const blob = await response.blob();

//         const url = window.URL.createObjectURL(blob);



//         const a = document.createElement("a");

//         a.href = url;

//         a.download = "employee-data.xlsx";

//         a.click();



//         window.URL.revokeObjectURL(url);



//     } catch (err) {

//         alert("Failed to download Excel");

//     }

// }







//download single employee PDF



async function downloadEmployeePDF() {

    if (!selectedEmployee) return alert("No employee selected");



    try {

        const response = await fetch(

            `${config.BACKEND_URL}/api/employees/${selectedEmployee.id}/download/pdf`

        );



        if (!response.ok) {

            throw new Error("Download failed");

        }



        const blob = await response.blob();

        const url = window.URL.createObjectURL(blob);



        const a = document.createElement("a");

        a.href = url;

        a.download = `${selectedEmployee.employeeName}_details.pdf`;

        a.click();



        window.URL.revokeObjectURL(url);



    } catch (err) {

        alert("Failed to download PDF");

    }

}









/* -------------------- Auth Panel -------------------- */

function openAuth(showSignin = true) {

    document.getElementById("auth-panel").style.display = "block";

    showSignin ? showSigninCard() : showSignup();

}



function closeAuthPanel() {

    document.getElementById("auth-panel").style.display = "none";

}



function showSigninCard() {

    document.getElementById("signin-card").style.display = "block";

    document.getElementById("signup-card").style.display = "none";



    // Reset signup fields when switching

    document.getElementById("signup-form")?.reset();

}



function showSignup() {

    document.getElementById("signin-card").style.display = "none";

    document.getElementById("signup-card").style.display = "block";

}





/* -------------------- Navigation -------------------- */

function gotoPage(pageId) {

    const pageTitle = document.getElementById("page-title");



    /* Dashboard title visible only on home */

    if (pageId === "home") {

        pageTitle.style.display = "block";

        pageTitle.textContent = "Dashboard";

    } else {

        pageTitle.style.display = "none";

    }



    /* Access protection */

    if (["employees", "profile", "add-employee"].includes(pageId) && !currentUser) {

        alert("Please login to proceed");

        return;

    }



    if (pageId === "add-employee" && currentUser?.department !== "IT") {

        alert("Only IT department can add or edit employees");

        return;

    }



    if (pageId === "employee-edit" && currentUser?.department !== "IT") {

        alert("Only IT department can edit employees");

        return;

    }



    /* Activate page */

    document.querySelectorAll(".page").forEach(p => p.classList.remove("active"));

    document.getElementById(pageId)?.classList.add("active");



    if (pageId === "employees") loadEmployees();

    if (pageId === "profile") loadProfile();



     if (pageId === "add-employee") {

        isEditMode = false;   // RESET EDIT MODE

        editingEmployeeId = null;



        // Reset the form fields for adding new employee

        document.getElementById("add-employee-form")?.reset();

    }

}



function openEditPage(emp) {

    selectedEmployee = emp;

    editingEmployeeId = emp.id;

    isEditMode = true;   // CORRECT

}



function showOnlyHome() {

    document.querySelectorAll(".page").forEach(p => p.classList.remove("active"));

    document.getElementById("home").classList.add("active");

}



/* -------------------- Login State -------------------- */

async function restoreLoginState() {

    const saved = localStorage.getItem("erp_user");



    if (!saved) {

        /* Guest mode */

        currentUser = null;



        document.getElementById("user-mini-img").src = "images/default-user.jpg";

        document.getElementById("pop-img").src = "images/default-user.jpg";



        document.getElementById("pop-name").textContent = "Guest";

        document.getElementById("pop-employeeid").textContent = "Employee ID: -";

        document.getElementById("stat-role").textContent = "Guest";



        document.getElementById("top-signin-btn").style.display = "inline-block";

        document.getElementById("sidebar-auth-btn").textContent = "Sign In";



        renderSidebarAccess();

       

        return;

    }



    currentUser = JSON.parse(saved);

    updateUIAfterLogin();



    await loadEmployees();

}



function updateUIAfterLogin() {



    document.getElementById("top-signin-btn").style.display = "none";

    document.getElementById("sidebar-auth-btn").textContent = "Sign Out";



    document.getElementById("user-mini-img").src = formatUserImage(currentUser.image);

    document.getElementById("pop-img").src = formatUserImage(currentUser.image);



    document.getElementById("pop-name").textContent = "Username: " + currentUser.username;

    document.getElementById("pop-employeeid").textContent = "Employee ID: " + currentUser.employeeId;



    document.getElementById("stat-role").textContent = currentUser.department;



    renderSidebarAccess();

}



/* -------------------- LOGIN -------------------- */

async function login(e) {

    e.preventDefault();



    const payload = {

        username: document.getElementById("signin-username").value,

        employeeId: document.getElementById("signin-employeeId").value,

        password: document.getElementById("signin-password").value

    };



    try {

        const user = await employeeAPI.signIn(payload);

        currentUser = user;



        localStorage.setItem("erp_user", JSON.stringify(user));

        closeAuthPanel();

        updateUIAfterLogin();



        await loadEmployees();

        gotoPage("home");



    } catch (err) {

        alert(err.message);

    }

}



/* -------------------- LOGOUT -------------------- */

function logout() {



    localStorage.removeItem("erp_user");

    currentUser = null;



    document.getElementById("top-signin-btn").style.display = "inline-block";

    document.getElementById("sidebar-auth-btn").textContent = "Sign In";



    document.getElementById("stat-role").textContent = "Guest";



   

    document.getElementById("user-mini-img").src = "images/default-user.jpg";

    document.getElementById("pop-img").src = "images/default-user.jpg";





    document.getElementById("pop-name").textContent = "Guest";

    document.getElementById("pop-employeeid").textContent = "Employee ID: -";



    document.getElementById("profile-username").textContent = "-";

    document.getElementById("profile-employeeId").textContent = "-";

    document.getElementById("profile-email").textContent = "-";

    document.getElementById("profile-department").textContent = "-";



    renderSidebarAccess();

    showOnlyHome();

}



/* -------------------- SIGNUP -------------------- */

document.getElementById("signup-department").addEventListener("change", function () {

    const box = document.getElementById("custom-dept-box");



    if (this.value === "Others") {

        box.style.display = "block";

    } else {

        box.style.display = "none";

        document.getElementById("custom-dept").value = "";

    }

});



async function signup(e) {

    e.preventDefault();



    const f = document.getElementById("signup-form");

    const imgFile = f.image.files[0];



    if (!imgFile) return alert("Upload an image");



    if (f.password.value !== f.confirmPassword.value) {

        return alert("Passwords do not match");

    }



    const img64 = await fileToBase64(imgFile);



    const payload = {

        username: f.username.value,

        employeeId: f.employeeId.value,

        email: f.email.value,

        department:  f.department.value === "Others"

        ? document.getElementById("custom-dept").value

        : f.department.value,

        password: f.password.value,

        imageBase64: img64

    };



    try {

        const res = await employeeAPI.signUp(payload);

        currentUser = res.user ?? res;



        localStorage.setItem("erp_user", JSON.stringify(currentUser));

        closeAuthPanel();

        updateUIAfterLogin();



        await loadEmployees();

        gotoPage("home");

        f.reset();



    } catch (err) {

        alert(err.message);

    }

}



/* -------------------- Sidebar Access -------------------- */

function renderSidebarAccess() {

    document.getElementById("nav-add-employee").style.display =

        currentUser?.department === "IT" ? "block" : "none";

}



/* -------------------- User Popup -------------------- */

function toggleUserPopup() {

    if (!currentUser) return openAuth(true);



    const pop = document.getElementById("user-details-pop");

    pop.style.display = pop.style.display === "block" ? "none" : "block";

}



/* -------------------- Load Employees -------------------- */

async function loadEmployees() {



    try {

        allEmployees = await employeeAPI.getAllEmployees();

    } catch {

        allEmployees = [];

    }



    document.getElementById("stat-total").textContent = allEmployees.length;



    const deptCount = new Set(allEmployees.map(e => e.departmentName)).size;

    document.getElementById("stat-depts").textContent = deptCount;



    renderEmployeeList(allEmployees);

    renderEmployeeTable(allEmployees);



    if (!currentUser) return;

    loadProfile();

}



/* -------------------- Profile -------------------- */

function loadProfile() {



    if (!currentUser) return;



    document.getElementById("profile-img").src = formatUserImage(currentUser.image);



    document.getElementById("profile-username").textContent = currentUser.username;

    document.getElementById("profile-employeeId").textContent = currentUser.employeeId;

    document.getElementById("profile-email").textContent = currentUser.email;

    document.getElementById("profile-department").textContent = currentUser.department;

}



/* -------------------- Employee List -------------------- */

function renderEmployeeList(list) {

    const c = document.getElementById("employees-list");

    c.innerHTML = "";



    if (!list.length) {

        c.innerHTML = "<p>No employees found</p>";

        return;

    }



    list.forEach(emp => {

        const div = document.createElement("div");

        div.className = "emp-card";


// app.js, in function renderEmployeeList(list)
//display "none" if value is null or empty in employee list cards
div.innerHTML = `
<img src="${formatEmployeeImage(emp.image)}" class="emp-card-img">
<div>
    <h4>${safeDisplay(emp.employeeName)}</h4>
    <p>${safeDisplay(emp.employeeCode)} — ${safeDisplay(emp.departmentName)}</p>
</div>
`;

// div.innerHTML = `
//                 <img src="${formatEmployeeImage(emp.image)}" class="emp-card-img">
//                 <div>
//                     <h4>${emp.employeeName || "-"}</h4>
//                     <p>${emp.employeeCode || "-"} — ${emp.departmentName || "-"}</p>
//                 </div>
//             `;

        div.addEventListener("click", () => showEmployeeDetail(emp));

        c.appendChild(div);

    });

}



/* -------------------- Employee Table -------------------- */

function renderEmployeeTable(list) {



    const tbody = document.getElementById("employees-tbody");

    tbody.innerHTML = "";



    if (!list.length) {

        tbody.innerHTML = `<tr><td colspan="6">No employees found</td></tr>`;

        return;

    }



    list.forEach(emp => {

        const tr = document.createElement("tr");



 // app.js, in function renderEmployeeTable(list)
//to display "none in the table if value is null or empty"
tr.innerHTML = `
<td><img src="${formatEmployeeImage(emp.image)}"
             class="emp-table-img"></td>
<td>${safeDisplay(emp.employeeCode)}</td>
<td>${safeDisplay(emp.employeeName)}</td>
<td>${safeDisplay(emp.departmentName)}</td>
<td>${safeDisplay(emp.designationName)}</td>
<td><button class="btn view-btn">View</button></td>
`;
// app.js, around line 390
// tr.innerHTML = `
//                 <td><img src="${formatEmployeeImage(emp.image)}"
//                          class="emp-table-img"></td>
//                 <td>${emp.employeeCode || "-"}</td>
//                 <td>${emp.employeeName || "-"}</td>
//                 <td>${emp.departmentName || "-"}</td>
//                 <td>${emp.designationName || "-"}</td>
//                 <td><button class="btn view-btn">View</button></td>
//             `;


        tr.querySelector(".view-btn").addEventListener("click", () => showEmployeeDetail(emp));

        tbody.appendChild(tr);

    });

}



/* =====================================================

   EMPLOYEE DETAIL VIEW

===================================================== */

function showEmployeeDetail(emp) {



    selectedEmployee = emp;

    gotoPage("employee-detail");



    document.getElementById("emp-detail-img").src = formatEmployeeImage(emp.image);

   // app.js, around line 430
document.getElementById("emp-detail-name").textContent = safeDisplay(emp.employeeName);
document.getElementById("emp-detail-code").textContent = "Employee Code: " + safeDisplay(emp.employeeCode);
document.getElementById("emp-detail-dept").textContent = "Department: " + safeDisplay(emp.departmentName);


    document.getElementById("btn-edit").onclick = () => openEditPage(emp);



    const block = [

        ["Date of Joining", emp.dateOfJoining],

        ["Reporting To", emp.reportingTo],

        ["Division Name", emp.divisionName],

        ["Designation Name", emp.designationName],

        ["Location Name", emp.locationName],

        ["Active Status", emp.activeStatus],

        ["Email ID", emp.emailId],

        ["Personal Email ID", emp.personalEmailId],

        ["Phone No", emp.phoneNo],

        ["Official No", emp.officialNo],

        ["Present User", emp.presentUser],

        ["Laptop Type", emp.laptopType],

        ["Old User 1", emp.oldUser1],

        ["Old User 2", emp.oldUser2],

        ["Supplier", emp.supplier],

        ["Object", emp.systemObject],

        ["Anti Glare Glass", emp.antiGlareGlass],

        ["Service Tag", emp.serviceTag],

        ["Brand Model", emp.brandModel],

        ["Date of Purchase", emp.dateOfPurchase],

        ["Warranty End", emp.warrantyEnd],

        ["OS", emp.os],

        ["OS Type", emp.osType],

        ["AutoCad", emp.autoCad],

        ["AutoCad Type", emp.autoCadType],

        ["MS Office", emp.msOffice],

        ["Outlook Mail", emp.outlookMail],

        ["MS Office Type", emp.msOfficeType],

        ["Warranty", emp.warranty],

        ["Laptop/PC Name", emp.laptopNamePcName],

        ["Windows Key", emp.windowsKey],

        ["Anti Virus", emp.antiVirus],

        ["Host", emp.host],

        ["IP Address", emp.ipAddress]

    ];



    // app.js, around line 500
document.getElementById("emp-detail-fields").innerHTML =
    block.map(([label, val]) => `
        <div class="emp-detail-item">
            <label>${label}</label>
            <span>${safeDisplay(val)}</span>
        </div>
    `).join("");

}



/* =====================================================

   OPEN EDIT PAGE

===================================================== */

function openEditPage(emp) {



    selectedEmployee = emp;

    editingEmployeeId = emp.id;

    isEditMode = true;



    gotoPage("employee-edit");



    const f = document.getElementById("edit-employee-form");



    for (let key in emp) {

        if (f[key]) f[key].value = emp[key] ?? "";

    }



    document.querySelector("#employee-edit .page-title").textContent = "Edit Employee";

}



/* =====================================================

   SUBMIT EDIT EMPLOYEE

===================================================== */

async function updateEmployee(e) {

    e.preventDefault();



    const f = document.getElementById("edit-employee-form");

    const fd = new FormData(f);



    const img = f.querySelector("input[name=image]");

    if (img && img.files.length > 0) {

        fd.set("image", img.files[0]);

    }



    try {

        await employeeAPI.updateEmployee(editingEmployeeId, fd);

        alert("Employee updated successfully!");

        gotoPage("employees");

        loadEmployees();



    } catch (err) {

        alert(err.message);

    }

}



/* =====================================================

   ADD EMPLOYEE

===================================================== */

async function submitEmployeeForm(e) {

    e.preventDefault();



    const f = document.getElementById("add-employee-form");

    const fd = new FormData(f);



    const img = f.querySelector("input[name=image]");

    if (img?.files?.length) fd.set("image", img.files[0]);



    try {

        if (isEditMode) {

            await employeeAPI.updateEmployee(editingEmployeeId, fd);

            alert("Employee updated!");

        } else {

            await employeeAPI.addEmployeeFormData(fd);

            alert("Employee added!");

        }



        f.reset();

        isEditMode = false;

        editingEmployeeId = null;



        gotoPage("employees");

        loadEmployees();



    } catch (err) {

        alert(err.message);

    }

}



/* -------------------- Helpers -------------------- */

function fileToBase64(file) {

    return new Promise((resolve) => {

        const r = new FileReader();

        r.onload = () => resolve(r.result);

        r.readAsDataURL(file);

    });

}
function safeDisplay(value) {
    if (value === null || value === undefined || (typeof value === 'string' && value.trim() === "")) {
        return "none";
    }
    return value;
}



function formatUserImage(path) {

    if (!path || path === "null" || path === "undefined" || path.trim() === "") {

        return "images/default-user.jpg";

    }

    return `${config.BACKEND_URL}/uploads/users/${path}`;

}



function formatEmployeeImage(path) {

    if (!path) return "images/no-employee.png";

    return `${config.BACKEND_URL}/uploads/employee-images/${path}`;

}