package Backend;

public class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int id, String name, int hoursWorked, double hourlyRate) {
        super(id, name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        double gross = hoursWorked * hourlyRate;
        double tax = 0.05 * gross;
        return gross - tax;
    }

    @Override
    public String generateSalarySlip() {
        double gross = hoursWorked * hourlyRate;
        double tax = 0.05 * gross;
        double net = calculateSalary();
        return String.format(
                "Name: %s\nType: Part-Time\nHours Worked: %d\nRate: ₹%.2f\nGross Salary: ₹%.2f\nDeductions:\n  - Tax: ₹%.2f\nNet Salary: ₹%.2f",
                name, hoursWorked, hourlyRate, gross, tax, net
        );
    }

    public int getHoursWorked() { return hoursWorked; }
    public double getHourlyRate() { return hourlyRate; }

    public void setHoursWorked(int hours) { this.hoursWorked = hours; }
    public void setHourlyRate(double rate) { this.hourlyRate = rate; }
}
