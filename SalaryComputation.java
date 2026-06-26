import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalaryComputation extends JPanel {
    private final JTextField txtHourlyRate;
    private final JTextField txtHoursWorked;
    private final JTextField txtDeductions;
    private final JLabel lblGrossPay;
    private final JLabel lblNetPay;

    public SalaryComputation() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtHourlyRate = new JTextField();
        txtHoursWorked = new JTextField();
        txtDeductions = new JTextField();
        lblGrossPay = new JLabel("0.00");
        lblNetPay = new JLabel("0.00");

        addRow(0, "Hourly Rate:", txtHourlyRate, gbc);
        addRow(1, "Hours Worked:", txtHoursWorked, gbc);
        addRow(2, "Deductions:", txtDeductions, gbc);

        JButton btnCompute = new JButton("Compute Salary");
        btnCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnCompute, gbc);

        gbc.gridwidth = 1;
        addRow(4, "Gross Pay:", lblGrossPay, gbc);
        addRow(5, "Net Pay:", lblNetPay, gbc);
    }

    private void addRow(int row, String labelText, Component field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    private void computeSalary() {
        try {
            double hourlyRate = Double.parseDouble(txtHourlyRate.getText().trim());
            double hoursWorked = Double.parseDouble(txtHoursWorked.getText().trim());
            double deductions = Double.parseDouble(txtDeductions.getText().trim());

            if (hourlyRate < 0 || hoursWorked < 0 || deductions < 0) {
                throw new NumberFormatException();
            }

            double grossPay = hourlyRate * hoursWorked;
            double netPay = grossPay - deductions;

            lblGrossPay.setText(String.format("%.2f", grossPay));
            lblNetPay.setText(String.format("%.2f", netPay));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid non-negative numbers for rate, hours, and deductions.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Salary Computation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new SalaryComputation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
