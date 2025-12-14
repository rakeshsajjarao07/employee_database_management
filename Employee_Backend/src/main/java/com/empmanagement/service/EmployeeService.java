package com.empmanagement.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.empmanagement.entity.Employee;
import com.empmanagement.repository.EmployeeRepository;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class EmployeeService {

    @Value("${file.upload-dir}")
    private String employeeUploadFolder;   // uploads/employee-images

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /* ==========================================================
       ADD EMPLOYEE
    ========================================================== */
    public Employee addEmployee(Map<String, String> fields, MultipartFile image) throws Exception {

        Employee emp = new Employee();
        mapFieldsToEmployee(emp, fields);

        if (image != null && !image.isEmpty()) {
            String saved = fileStorageService.saveMultipart(image);
            emp.setImage(saved);
        }

        return employeeRepository.save(emp);
    }

    /* ==========================================================
       UPDATE EMPLOYEE
    ========================================================== */
    public Employee updateEmployee(Long id, Map<String, String> fields, MultipartFile image) throws Exception {

        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        mapFieldsToEmployee(emp, fields);

        if (image != null && !image.isEmpty()) {
            String saved = fileStorageService.saveMultipart(image);
            emp.setImage(saved);
        }

        return employeeRepository.save(emp);
    }

    /* ==========================================================
       MAP FORM FIELDS TO ENTITY
    ========================================================== */
    private void mapFieldsToEmployee(Employee emp, Map<String, String> fields) {

        fields.forEach((key, value) -> {
            if (value == null || value.trim().isEmpty()) return;

            switch (key) {
                case "employeeCode": emp.setEmployeeCode(value); break;
                case "employeeName": emp.setEmployeeName(value); break;
                case "dateOfJoining": emp.setDateOfJoining(value); break;
                case "reportingTo": emp.setReportingTo(value); break;
                case "divisionName": emp.setDivisionName(value); break;
                case "designationName": emp.setDesignationName(value); break;
                case "locationName": emp.setLocationName(value); break;
                case "departmentName": emp.setDepartmentName(value); break;
                case "activeStatus": emp.setActiveStatus(value); break;
                case "emailId": emp.setEmailId(value); break;
                case "personalEmailId": emp.setPersonalEmailId(value); break;
                case "phoneNo": emp.setPhoneNo(value); break;
                case "officialNo": emp.setOfficialNo(value); break;
                case "presentUser": emp.setPresentUser(value); break;
                case "laptopType": emp.setLaptopType(value); break;
                case "oldUser1": emp.setOldUser1(value); break;
                case "oldUser2": emp.setOldUser2(value); break;
                case "supplier": emp.setSupplier(value); break;
                case "systemObject": emp.setSystemObject(value); break;
                case "antiGlareGlass": emp.setAntiGlareGlass(value); break;
                case "serviceTag": emp.setServiceTag(value); break;
                case "brandModel": emp.setBrandModel(value); break;
                case "dateOfPurchase": emp.setDateOfPurchase(value); break;
                case "warrantyEnd": emp.setWarrantyEnd(value); break;
                case "os": emp.setOs(value); break;
                case "osType": emp.setOsType(value); break;
                case "autoCad": emp.setAutoCad(value); break;
                case "autoCadType": emp.setAutoCadType(value); break;
                case "msOffice": emp.setMsOffice(value); break;
                case "outlookMail": emp.setOutlookMail(value); break;
                case "msOfficeType": emp.setMsOfficeType(value); break;
                case "warranty": emp.setWarranty(value); break;
                case "laptopNamePcName": emp.setLaptopNamePcName(value); break;
                case "windowsKey": emp.setWindowsKey(value); break;
                case "antiVirus": emp.setAntiVirus(value); break;
                case "host": emp.setHost(value); break;
                case "ipAddress": emp.setIpAddress(value); break;
            }
        });
    }

    /* ==========================================================
       PDF GENERATION WITH IMAGE
    ========================================================== */
    public byte[] generateEmployeePDF(Long id) throws Exception {

    	System.out.println("hi");
        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, out);

        doc.open();

        /* -------- IMAGE -------- */
        /* -------- IMAGE -------- */

        String fullPath = employeeUploadFolder + "/" + e.getImage();

        System.out.println("PDF trying to load image = " + fullPath);

        File imgFile = new File(fullPath);

        if (imgFile.exists()) {
            String uri = imgFile.toURI().toString();
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(uri);
            img.scaleToFit(150, 180);
            img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            doc.add(img);
        } else {
            System.out.println("IMAGE NOT FOUND at: " + fullPath);
            Paragraph noImg = new Paragraph("[Image Not Available]");
            noImg.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(noImg);
        }


        doc.add(new Paragraph("\nEmployee Details"));
        doc.add(new Paragraph("-----------------------"));

        doc.add(new Paragraph("Employee Code: " + safe(e.getEmployeeCode())));
        doc.add(new Paragraph("Name: " + safe(e.getEmployeeName())));
        doc.add(new Paragraph("Department: " + safe(e.getDepartmentName())));
        doc.add(new Paragraph("Designation: " + safe(e.getDesignationName())));
        doc.add(new Paragraph("Email: " + safe(e.getEmailId())));
        doc.add(new Paragraph("Phone: " + safe(e.getPhoneNo())));
        doc.add(new Paragraph("Laptop Type: " + safe(e.getLaptopType())));
        doc.add(new Paragraph("Supplier: " + safe(e.getSupplier())));
        doc.add(new Paragraph("Windows Key: " + safe(e.getWindowsKey())));
        doc.add(new Paragraph("IP Address: " + safe(e.getIpAddress())));

        doc.close();
        return out.toByteArray();
    }

    private String safe(String v) {
        return v == null ? "-" : v;
    }

    /* ==========================================================
       FETCH
    ========================================================== */
    public List<Employee> getAllEmployees() { return employeeRepository.findAll(); }

    public Optional<Employee> getById(Long id) { return employeeRepository.findById(id); }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
    }
}
