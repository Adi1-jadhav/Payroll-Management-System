# Payroll-Management-System
Java-based payroll system with role-based access, PDF export, and MySQL integration

A Java-based desktop application for managing employee payroll with role-based access, salary slip generation, and report export.

---

## 🚀 Features

- Add, update, delete employee records
- Full-time and part-time salary logic
- Role-based access for HR and Finance
- PDF salary slip export using iText
- CSV report export (Excel-compatible)
- MySQL database integration
- GUI built with Java Swing

---

## 🧮 Salary Calculation Logic

- **Full-Time Employees**:
  - Monthly Salary
  - PF = 4% of base
  - Tax = 6% of base
  - Net Salary = Base - PF - Tax

- **Part-Time Employees**:
  - Salary = Hours Worked × Hourly Rate
  - PF and Tax same as above

---

## 📄 Salary Slip Generation

Each employee has a `generateSalarySlip()` method that returns a formatted string. This is passed to `PDFExporter`, which uses iText to create a downloadable PDF.

```java
Document doc = new Document();
PdfWriter.getInstance(doc, new FileOutputStream(path));
doc.open();
doc.add(new Paragraph(emp.generateSalarySlip()));
doc.close();


**Report Export**
All employees are exported to a .csv file using PrintWriter. The file opens automatically in Excel:

writer.println("ID,Name,Type,Net Salary");
writer.printf("%d,%s,%s,%.2f\n", ...);
Desktop.getDesktop().open(new File(path));


**Tech Stack**
Layer	         Technology
Language	      Java
GUI	            Swing
Database	      MySQL
PDF Export	    iText 5.5.13.3
Report Export 	Java IO (CSV)


🔐 Security
-Role-based access control
-Admin login via MySQL
-DAO pattern for DB isolation
-Passwords can be hashed for production


**Real-World Impact**
-Eliminates manual payroll errors
-Provides audit-ready salary documentation
-Enables secure HR/Finance workflows
-Makes payroll data exportable for accounting


**🔮 Future Scope**
Email integration for salary slips

Cloud-based backend (Firebase/AWS)

Attendance-based salary automation

Analytics dashboard for HR insights


📦 Setup Instructions
Clone the repo

Add MySQL connector and iText JARs to /lib

Create database using schema.sql

Run LoginScreen.java to start
