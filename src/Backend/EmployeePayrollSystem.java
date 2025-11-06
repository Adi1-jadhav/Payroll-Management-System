package Backend;

import java.util.*;

public class EmployeePayrollSystem {
    private EmployeeDAO dao = new EmployeeDAO();

    public void addEmployee(Employee emp) {
        dao.addEmployee(emp);
    }

    public boolean deleteEmployee(int id) {
        return dao.deleteEmployee(id);
    }

    public boolean updateEmployee(Employee emp) {
        return dao.updateEmployee(emp);
    }
    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }

    public String showAllEmployees() {
        List<Employee> list = dao.getAllEmployees();
        StringBuilder sb = new StringBuilder("All Employees:\n");
        for (Employee emp : list) {
            sb.append("ID: ").append(emp.getId())
                    .append(", Name: ").append(emp.getName())
                    .append(", Salary: ₹").append(String.format("%.2f", emp.calculateSalary()))
                    .append("\n");
        }
        return sb.toString();
    }

    public String generateSlip(int id) {
        Employee emp = dao.getEmployee(id);
        return (emp != null) ? emp.generateSalarySlip() : "Employee not found.\n";
    }

    public Employee getEmployee(int id) {
        return dao.getEmployee(id);
    }
}
