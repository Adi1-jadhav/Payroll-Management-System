package Frontend;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import Backend.Employee;
import Backend.FullTimeEmployee;

public class ReportGenerator {
    public static void exportReport(List<Employee> employees, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.println("ID,Name,Type,Net Salary");
            for (Employee emp : employees) {
                String type = (emp instanceof FullTimeEmployee) ? "Full-Time" : "Part-Time";
                writer.printf("%d,%s,%s,%.2f\n", emp.getId(), emp.getName(), type, emp.calculateSalary());
            }
        }
    }
}
