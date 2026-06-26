public class Employee {
    private String employeeNumber;
    private String name;
    private double hourlyRate;
    private double hoursWorked;
    private double deductions;

    public Employee(String employeeNumber, String name, double hourlyRate, double hoursWorked, double deductions) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.deductions = deductions;
    }

    // Getters and Setters
    public String getEmployeeNumber() { return employeeNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }
    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }

    // Calculated fields (Feature 3 integration)
    public double getGrossPay() { return this.hourlyRate * this.hoursWorked; }
    public double getNetPay() { return this.getGrossPay() - this.deductions; }

    @Override
    public String toString() {
        return String.join(",", employeeNumber, name, String.valueOf(hourlyRate), String.valueOf(hoursWorked), String.valueOf(deductions));
    }
}
