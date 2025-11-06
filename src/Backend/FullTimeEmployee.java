package Backend;

public class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(int id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        double pf = 0.04 * monthlySalary;
        double tax = 0.06 * monthlySalary;
        return monthlySalary - pf - tax;
    }

    @Override
    public String generateSalarySlip() {
        double pf = 0.04 * monthlySalary;
        double tax = 0.06 * monthlySalary;
        double net = calculateSalary();
        return String.format(
                "Name: %s\nType: Full-Time\nBase Salary: ₹%.2f\nDeductions:\n  - PF: ₹%.2f\n  - Tax: ₹%.2f\nNet Salary: ₹%.2f",
                name, monthlySalary, pf, tax, net
        );
    }

    public double getMonthlySalary() { return monthlySalary; }
    public void setMonthlySalary(double salary) { this.monthlySalary = salary; }
}
