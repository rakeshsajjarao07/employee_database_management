package com.empmanagement.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empmanagement.entity.Employee;
import com.empmanagement.service.EmployeeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



//import org.springframework.http.MediaType;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.Paragraph;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFSheet;

//
////Spring
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;

//Excel (Apache POI)
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//PDF (iText)
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.Paragraph;

////Java I/O
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /* ==========================================================
       ADD EMPLOYEE (FormData → Map<String,String> + MultipartFile)
    ========================================================== */
    @PostMapping(path = "/form", consumes = "multipart/form-data")
    public ResponseEntity<?> addEmployeeForm(
            @RequestParam Map<String, String> fields,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            Employee saved = employeeService.addEmployee(fields, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    /* ==========================================================
       UPDATE EMPLOYEE
    ========================================================== */
    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestParam Map<String, String> fields,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            Employee updated = employeeService.updateEmployee(id, fields, image);
            return ResponseEntity.ok(updated);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    /* ==========================================================
       GET ALL EMPLOYEES
    ========================================================== */
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /* ==========================================================
       GET BY PRIMARY KEY (ID)
    ========================================================== */
    @GetMapping("/{id}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Long id) {

        Optional<Employee> emp = employeeService.getById(id);

        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Employee not found"));
        }

        return ResponseEntity.ok(emp);
    }

    
    /* ==========================================================
       SEARCH BY NAME
    ========================================================== */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Employee>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.findByName(name));
    }
    
//    @GetMapping("/download/excel")
//    public ResponseEntity<byte[]> downloadExcel() throws IOException {
//
//        ByteArrayOutputStream out = employeeService.exportEmployeesToExcel();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=employees.xlsx")
//                .body(out.toByteArray());
//    }
// 
    
    
//    @GetMapping("/download/excel")
//    public ResponseEntity<byte[]> downloadExcel() throws IOException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Employees");
//
//        Row header = sheet.createRow(0);
//        String[] columns = { "ID", "Employee Code", "Name", "Department", "Email" };
//
//        for (int i = 0; i < columns.length; i++) {
//            header.createCell(i).setCellValue(columns[i]);
//        }
//
//        int rowIndex = 1;
//        for (Employee emp : employeeService.getAllEmployees()) {
//            Row row = sheet.createRow(rowIndex++);
//            row.createCell(0).setCellValue(emp.getId());
//            row.createCell(1).setCellValue(emp.getEmployeeCode());
//            row.createCell(2).setCellValue(emp.getEmployeeName());
//            row.createCell(3).setCellValue(emp.getDepartmentName());
//            row.createCell(4).setCellValue(emp.getEmailId());
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        workbook.close();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=employee-data.xlsx")
//                .contentType(MediaType.parseMediaType(
//                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//                ))
//                .body(out.toByteArray());
//    }

    

//  @GetMapping("/download/excel")
//    public ResponseEntity<byte[]> downloadExcel() throws IOException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Employees");
//
//        Row header = sheet.createRow(0);
//        String[] columns = { "ID", "Employee Code", "Name", "Department", "Email" };
//
//        for (int i = 0; i < columns.length; i++) {
//            header.createCell(i).setCellValue(columns[i]);
//        }
//
//        int rowIndex = 1;
//        for (Employee emp : employeeService.getAllEmployees()) { // <--- This calls the service
//            Row row = sheet.createRow(rowIndex++);
//            row.createCell(0).setCellValue(emp.getId());
//            row.createCell(1).setCellValue(emp.getEmployeeCode());
//            row.createCell(2).setCellValue(emp.getEmployeeName());
//            row.createCell(3).setCellValue(emp.getDepartmentName());
//            row.createCell(4).setCellValue(emp.getEmailId());
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        workbook.close();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=employee-data.xlsx")
//                .contentType(MediaType.parseMediaType(
//                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
//                ))
//                .body(out.toByteArray());
//    }
    @GetMapping("/download/excel")
    public ResponseEntity<?> downloadExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Employees");

            // ... [Your code for creating header and rows] ...
            
//            int rowIndex = 1;
            List<Employee> employees = employeeService.getAllEmployees(); // Fetch data first
//           
//         

            // --- 1. DEFINE AND CREATE THE HEADER ROW ---
            Row header = sheet.createRow(0); // This is the FIRST row (index 0)

            // Define the column names in the exact order they appear in the data loop:
            String[] columns = { 
                "ID", "Employee Code", "Name", "Date of Joining", "Reporting To", 
                "Division Name", "Designation Name", "Location Name", "Department Name", 
                "Active Status", "Email ID", "Personal Email ID", "Phone No", 
                "Official No", "Present User", "Laptop Type", "Old User 1", 
                "Old User 2", "Supplier", "Object", "Anti Glare Glass", "Service Tag", 
                "Brand Model", "Date of Purchase", "Warranty End", "OS", 
                "OS Type", "AutoCad", "AutoCad Type", "MS Office", "Outlook Mail", 
                "MS Office Type", "Warranty", "Laptop/PC Name", "Windows Key", 
                "Anti Virus", "Host", "IP Address" 
            }; 

            // Populate the header row
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            // Ensure your data loop starts from the next index:
            int rowIndex = 1; // Start populating employee data from row index 1 (the second row)

            // for (Employee emp : employees) { ... (Your data loop starts here)
         // Assuming 'employees' is List<Employee> and 'rowIndex' starts at 1
            for (Employee emp : employees) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;

                // --- Basic Information & HR Details ---
                row.createCell(cellIndex++).setCellValue(emp.getId());
                row.createCell(cellIndex++).setCellValue(emp.getEmployeeCode());
                row.createCell(cellIndex++).setCellValue(emp.getEmployeeName());
                
                // Date fields (handle null or convert to string if necessary)
                row.createCell(cellIndex++).setCellValue(
                    emp.getDateOfJoining() != null ? emp.getDateOfJoining().toString() : ""
                ); 
                
                row.createCell(cellIndex++).setCellValue(emp.getReportingTo());
                row.createCell(cellIndex++).setCellValue(emp.getDivisionName());
                row.createCell(cellIndex++).setCellValue(emp.getDesignationName());
                row.createCell(cellIndex++).setCellValue(emp.getLocationName());
                row.createCell(cellIndex++).setCellValue(emp.getDepartmentName());
                row.createCell(cellIndex++).setCellValue(emp.getActiveStatus());

                // --- Contact Information ---
                row.createCell(cellIndex++).setCellValue(emp.getEmailId());
                row.createCell(cellIndex++).setCellValue(emp.getPersonalEmailId());
                row.createCell(cellIndex++).setCellValue(emp.getPhoneNo());
                row.createCell(cellIndex++).setCellValue(emp.getOfficialNo());

                // --- Asset/System Information ---
                row.createCell(cellIndex++).setCellValue(emp.getPresentUser());
                row.createCell(cellIndex++).setCellValue(emp.getLaptopType());
                row.createCell(cellIndex++).setCellValue(emp.getOldUser1());
                row.createCell(cellIndex++).setCellValue(emp.getOldUser2());
                row.createCell(cellIndex++).setCellValue(emp.getSupplier());
                row.createCell(cellIndex++).setCellValue(emp.getSystemObject());
                row.createCell(cellIndex++).setCellValue(emp.getAntiGlareGlass());
                row.createCell(cellIndex++).setCellValue(emp.getServiceTag());
                row.createCell(cellIndex++).setCellValue(emp.getBrandModel());
                
                // Date fields for purchase/warranty
                row.createCell(cellIndex++).setCellValue(
                    emp.getDateOfPurchase() != null ? emp.getDateOfPurchase().toString() : ""
                );
                row.createCell(cellIndex++).setCellValue(
                    emp.getWarrantyEnd() != null ? emp.getWarrantyEnd().toString() : ""
                );
                
                row.createCell(cellIndex++).setCellValue(emp.getOs());
                row.createCell(cellIndex++).setCellValue(emp.getOsType());
                row.createCell(cellIndex++).setCellValue(emp.getAutoCad());
                row.createCell(cellIndex++).setCellValue(emp.getAutoCadType());
                row.createCell(cellIndex++).setCellValue(emp.getMsOffice());
                row.createCell(cellIndex++).setCellValue(emp.getOutlookMail());
                row.createCell(cellIndex++).setCellValue(emp.getMsOfficeType());
                row.createCell(cellIndex++).setCellValue(emp.getWarranty());
                row.createCell(cellIndex++).setCellValue(emp.getLaptopNamePcName());
                row.createCell(cellIndex++).setCellValue(emp.getWindowsKey());
                row.createCell(cellIndex++).setCellValue(emp.getAntiVirus());
                row.createCell(cellIndex++).setCellValue(emp.getHost());
                row.createCell(cellIndex++).setCellValue(emp.getIpAddress());
                
                // Note: If you need to include a path/URL for the image, uncomment the next line.
                // row.createCell(cellIndex++).setCellValue(emp.getImageUrl()); 
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=employee-data.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    ))
                    .body(out.toByteArray());

        } catch (Exception ex) {
            // Log the exception to the console/server logs
            ex.printStackTrace(); 
            
            // Return a clear, non-Excel error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate Excel file: " + ex.getMessage()));
        }
    }
//    @GetMapping("/download/excel")
//    public ResponseEntity<byte[]> downloadExcel() throws IOException {
//
//        ByteArrayOutputStream out = employeeService.exportEmployeesToExcel();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=employees.xlsx")
//                .body(out.toByteArray());
//    }
    
//    @GetMapping("/{id}/download/pdf")
//    public ResponseEntity<byte[]> downloadEmployeePDF(@PathVariable Long id) throws Exception {
//
//        byte[] pdf = employeeService.generateEmployeePDF(id);
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=employee-details.pdf")
//                .body(pdf);
//    }

    @GetMapping("/{id}/download/pdf")
    public ResponseEntity<byte[]> downloadEmployeePDF(@PathVariable Long id) {

        Employee emp = employeeService.getById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            /* ============= EMPLOYEE IMAGE ============= */
            try {
                if (emp.getImage() != null && !emp.getImage().isEmpty()) {

                    // Build absolute path to the image
                    String fullImagePath = System.getProperty("user.dir")
                            + "/uploads/employee-images/"
                            + emp.getImage();

                    System.out.println("Loading image from: " + fullImagePath);

                    Image photo = Image.getInstance(fullImagePath);

                    // Passport-style size
                    photo.scaleAbsolute(120f, 140f);
                    photo.setAlignment(Image.ALIGN_CENTER);

                    document.add(photo);

                } else {
                    document.add(new Paragraph("[Image Not Available]"));
                }
            } catch (Exception imgEx) {
                document.add(new Paragraph("[Image Not Available]"));
            }

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("EMPLOYEE DETAILS", new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 16, com.itextpdf.text.Font.BOLD
            )));
            document.add(new Paragraph("------------------------------------------------------\n"));

            /* ============= TABLE WITH ALL FIELDS ============= */

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addRow(table, "Employee Code", emp.getEmployeeCode());
            addRow(table, "Employee Name", emp.getEmployeeName());
            addRow(table, "Date of Joining", emp.getDateOfJoining());
            addRow(table, "Reporting To", emp.getReportingTo());
            addRow(table, "Division", emp.getDivisionName());
            addRow(table, "Designation", emp.getDesignationName());
            addRow(table, "Location", emp.getLocationName());
            addRow(table, "Department", emp.getDepartmentName());
            addRow(table, "Active Status", emp.getActiveStatus());
            addRow(table, "Email ID", emp.getEmailId());
            addRow(table, "Personal Email ID", emp.getPersonalEmailId());
            addRow(table, "Phone No", emp.getPhoneNo());
            addRow(table, "Official No", emp.getOfficialNo());
            addRow(table, "Present User", emp.getPresentUser());
            addRow(table, "Laptop Type", emp.getLaptopType());
            addRow(table, "Old User 1", emp.getOldUser1());
            addRow(table, "Old User 2", emp.getOldUser2());
            addRow(table, "Supplier", emp.getSupplier());
            addRow(table, "System Object", emp.getSystemObject());
            addRow(table, "Anti Glare Glass", emp.getAntiGlareGlass());
            addRow(table, "Service Tag", emp.getServiceTag());
            addRow(table, "Brand Model", emp.getBrandModel());
            addRow(table, "Date of Purchase", emp.getDateOfPurchase());
            addRow(table, "Warranty End", emp.getWarrantyEnd());
            addRow(table, "OS", emp.getOs());
            addRow(table, "OS Type", emp.getOsType());
            addRow(table, "AutoCad", emp.getAutoCad());
            addRow(table, "AutoCad Type", emp.getAutoCadType());
            addRow(table, "MS Office", emp.getMsOffice());
            addRow(table, "Outlook Mail", emp.getOutlookMail());
            addRow(table, "MS Office Type", emp.getMsOfficeType());
            addRow(table, "Warranty", emp.getWarranty());
            addRow(table, "Laptop/PC Name", emp.getLaptopNamePcName());
            addRow(table, "Windows Key", emp.getWindowsKey());
            addRow(table, "Anti Virus", emp.getAntiVirus());
            addRow(table, "Host", emp.getHost());
            addRow(table, "IP Address", emp.getIpAddress());

            document.add(table);

            document.close();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + emp.getEmployeeName() + "_details.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /* ========= Helper Method for Table ========== */
    private void addRow(PdfPTable table, String label, Object value) {
        table.addCell(label);
        table.addCell(value != null ? value.toString() : "-");
    }



}
