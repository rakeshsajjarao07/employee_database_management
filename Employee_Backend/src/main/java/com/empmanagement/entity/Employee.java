package com.empmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    private String employeeName;
    private String dateOfJoining;
    private String reportingTo;
    private String divisionName;
    private String designationName;
    private String locationName;
    private String departmentName;
    private String activeStatus;

    private String emailId;
    private String personalEmailId;
    private String phoneNo;
    private String officialNo;

    private String presentUser;
    private String laptopType;
    private String oldUser1;
    private String oldUser2;
    private String supplier;

    private String systemObject;
    private String antiGlareGlass;
    private String serviceTag;
    private String brandModel;

    private String dateOfPurchase;
    private String warrantyEnd;

    private String os;
    private String osType;

    private String autoCad;
    private String autoCadType;

    private String msOffice;
    private String outlookMail;
    private String msOfficeType;

    private String warranty;
    private String laptopNamePcName;
    private String windowsKey;
    private String antiVirus;

    private String host;
    private String ipAddress;

    private String image; // stored filename

    public Employee() {
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
    
    /* ================== GETTERS & SETTERS ================== */
    // (Generated fully — no truncation)

    public Employee(String employeeCode, String employeeName, String dateOfJoining, String reportingTo,
			String divisionName, String designationName, String locationName, String departmentName,
			String activeStatus, String emailId, String personalEmailId, String phoneNo, String officialNo,
			String presentUser, String laptopType, String oldUser1, String oldUser2, String supplier,
			String systemObject, String antiGlareGlass, String serviceTag, String brandModel, String dateOfPurchase,
			String warrantyEnd, String os, String osType, String autoCad, String autoCadType, String msOffice,
			String outlookMail, String msOfficeType, String warranty, String laptopNamePcName, String windowsKey,
			String antiVirus, String host, String ipAddress, String image) {
		super();
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.dateOfJoining = dateOfJoining;
		this.reportingTo = reportingTo;
		this.divisionName = divisionName;
		this.designationName = designationName;
		this.locationName = locationName;
		this.departmentName = departmentName;
		this.activeStatus = activeStatus;
		this.emailId = emailId;
		this.personalEmailId = personalEmailId;
		this.phoneNo = phoneNo;
		this.officialNo = officialNo;
		this.presentUser = presentUser;
		this.laptopType = laptopType;
		this.oldUser1 = oldUser1;
		this.oldUser2 = oldUser2;
		this.supplier = supplier;
		this.systemObject = systemObject;
		this.antiGlareGlass = antiGlareGlass;
		this.serviceTag = serviceTag;
		this.brandModel = brandModel;
		this.dateOfPurchase = dateOfPurchase;
		this.warrantyEnd = warrantyEnd;
		this.os = os;
		this.osType = osType;
		this.autoCad = autoCad;
		this.autoCadType = autoCadType;
		this.msOffice = msOffice;
		this.outlookMail = outlookMail;
		this.msOfficeType = msOfficeType;
		this.warranty = warranty;
		this.laptopNamePcName = laptopNamePcName;
		this.windowsKey = windowsKey;
		this.antiVirus = antiVirus;
		this.host = host;
		this.ipAddress = ipAddress;
		this.image = image;
	}






	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(String dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    public String getReportingTo() { return reportingTo; }
    public void setReportingTo(String reportingTo) { this.reportingTo = reportingTo; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public String getDesignationName() { return designationName; }
    public void setDesignationName(String designationName) { this.designationName = designationName; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getActiveStatus() { return activeStatus; }
    public void setActiveStatus(String activeStatus) { this.activeStatus = activeStatus; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPersonalEmailId() { return personalEmailId; }
    public void setPersonalEmailId(String personalEmailId) { this.personalEmailId = personalEmailId; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getOfficialNo() { return officialNo; }
    public void setOfficialNo(String officialNo) { this.officialNo = officialNo; }

    public String getPresentUser() { return presentUser; }
    public void setPresentUser(String presentUser) { this.presentUser = presentUser; }

    public String getLaptopType() { return laptopType; }
    public void setLaptopType(String laptopType) { this.laptopType = laptopType; }

    public String getOldUser1() { return oldUser1; }
    public void setOldUser1(String oldUser1) { this.oldUser1 = oldUser1; }

    public String getOldUser2() { return oldUser2; }
    public void setOldUser2(String oldUser2) { this.oldUser2 = oldUser2; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public String getSystemObject() { return systemObject; }
    public void setSystemObject(String systemObject) { this.systemObject = systemObject; }

    public String getAntiGlareGlass() { return antiGlareGlass; }
    public void setAntiGlareGlass(String antiGlareGlass) { this.antiGlareGlass = antiGlareGlass; }

    public String getServiceTag() { return serviceTag; }
    public void setServiceTag(String serviceTag) { this.serviceTag = serviceTag; }

    public String getBrandModel() { return brandModel; }
    public void setBrandModel(String brandModel) { this.brandModel = brandModel; }

    public String getDateOfPurchase() { return dateOfPurchase; }
    public void setDateOfPurchase(String dateOfPurchase) { this.dateOfPurchase = dateOfPurchase; }

    public String getWarrantyEnd() { return warrantyEnd; }
    public void setWarrantyEnd(String warrantyEnd) { this.warrantyEnd = warrantyEnd; }

    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }

    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }

    public String getAutoCad() { return autoCad; }
    public void setAutoCad(String autoCad) { this.autoCad = autoCad; }

    public String getAutoCadType() { return autoCadType; }
    public void setAutoCadType(String autoCadType) { this.autoCadType = autoCadType; }

    public String getMsOffice() { return msOffice; }
    public void setMsOffice(String msOffice) { this.msOffice = msOffice; }

    public String getOutlookMail() { return outlookMail; }
    public void setOutlookMail(String outlookMail) { this.outlookMail = outlookMail; }

    public String getMsOfficeType() { return msOfficeType; }
    public void setMsOfficeType(String msOfficeType) { this.msOfficeType = msOfficeType; }

    public String getWarranty() { return warranty; }
    public void setWarranty(String warranty) { this.warranty = warranty; }

    public String getLaptopNamePcName() { return laptopNamePcName; }
    public void setLaptopNamePcName(String laptopNamePcName) { this.laptopNamePcName = laptopNamePcName; }

    public String getWindowsKey() { return windowsKey; }
    public void setWindowsKey(String windowsKey) { this.windowsKey = windowsKey; }

    public String getAntiVirus() { return antiVirus; }
    public void setAntiVirus(String antiVirus) { this.antiVirus = antiVirus; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
