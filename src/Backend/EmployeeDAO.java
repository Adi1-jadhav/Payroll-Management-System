package Backend;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeePayroll", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ FIXED: authenticate is now non-static
    public String authenticate(String user, String pass) {
        String sql = "SELECT role FROM admin WHERE username=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (id, name, type, monthly_salary, hours_worked, hourly_rate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emp.getId());
            stmt.setString(2, emp.getName());
            if (emp instanceof FullTimeEmployee) {
                stmt.setString(3, "Full-Time");
                stmt.setDouble(4, ((FullTimeEmployee) emp).getMonthlySalary());
                stmt.setNull(5, Types.INTEGER);
                stmt.setNull(6, Types.DOUBLE);
            } else {
                stmt.setString(3, "Part-Time");
                stmt.setNull(4, Types.DOUBLE);
                stmt.setInt(5, ((PartTimeEmployee) emp).getHoursWorked());
                stmt.setDouble(6, ((PartTimeEmployee) emp).getHourlyRate());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name = ?, monthly_salary = ?, hours_worked = ?, hourly_rate = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getName());
            if (emp instanceof FullTimeEmployee) {
                stmt.setDouble(2, ((FullTimeEmployee) emp).getMonthlySalary());
                stmt.setNull(3, Types.INTEGER);
                stmt.setNull(4, Types.DOUBLE);
            } else {
                stmt.setNull(2, Types.DOUBLE);
                stmt.setInt(3, ((PartTimeEmployee) emp).getHoursWorked());
                stmt.setDouble(4, ((PartTimeEmployee) emp).getHourlyRate());
            }
            stmt.setInt(5, emp.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                if (type.equals("Full-Time")) {
                    double salary = rs.getDouble("monthly_salary");
                    return new FullTimeEmployee(id, name, salary);
                } else {
                    int hours = rs.getInt("hours_worked");
                    double rate = rs.getDouble("hourly_rate");
                    return new PartTimeEmployee(id, name, hours, rate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                if (type.equals("Full-Time")) {
                    double salary = rs.getDouble("monthly_salary");
                    list.add(new FullTimeEmployee(id, name, salary));
                } else {
                    int hours = rs.getInt("hours_worked");
                    double rate = rs.getDouble("hourly_rate");
                    list.add(new PartTimeEmployee(id, name, hours, rate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
