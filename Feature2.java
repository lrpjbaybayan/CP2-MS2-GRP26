import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Feature2 extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private CSVHandler csvHandler;
    private List<Employee> employeeList;

    private JTextField txtId, txtName, txtRate, txtHours, txtDeductions;
    private JButton btnAdd, btnUpdate, btnDelete;

    public Feature2() {
        setTitle("MotorPH Employee Management System");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        csvHandler = new CSVHandler();
        employeeList = csvHandler.loadEmployees();

        // Table Layout
        String[] columnNames = {"Emp ID", "Name", "Hourly Rate", "Hours Worked", "Deductions", "Gross Pay", "Net Pay"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        // Form Layout
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details Form"));
        formPanel.add(new JLabel(" Employee ID:"));  txtId = new JTextField(); formPanel.add(txtId);
        formPanel.add(new JLabel(" Full Name:"));    txtName = new JTextField(); formPanel.add(txtName);
        formPanel.add(new JLabel(" Hourly Rate:")); txtRate = new JTextField(); formPanel.add(txtRate);
        formPanel.add(new JLabel(" Hours Worked:"));txtHours = new JTextField(); formPanel.add(txtHours);
        formPanel.add(new JLabel(" Deductions:"));  txtDeductions = new JTextField(); formPanel.add(txtDeductions);
        add(formPanel, BorderLayout.SOUTH);

        // Buttons Layout
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnAdd = new JButton("Add Record");
        btnUpdate = new JButton("Update Record");
        btnDelete = new JButton("Delete Record");
        buttonPanel.add(btnAdd); buttonPanel.add(btnUpdate);buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.EAST);

        // Button Action Listeners
        btnAdd.addActionListener(e -> actionAddEmployee());
        btnUpdate.addActionListener(e -> actionUpdateEmployee());
        btnDelete.addActionListener(e -> actionDeleteEmployee());

        // Table Selection Listener to autofill fields
        employeeTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtRate.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtHours.setText(tableModel.getValueAt(selectedRow, 3).toString());
                txtDeductions.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });
         refreshTableDisplay();
    }

    private void refreshTableDisplay() {
        tableModel.setRowCount(0);
        for (Employee emp : employeeList) {
            tableModel.addRow(new Object[]{
                emp.getEmployeeNumber(), emp.getName(),
                String.format("%.2f", emp.getHourlyRate()), String.format("%.2f", emp.getHoursWorked()),
                String.format("%.2f", emp.getDeductions()), String.format("%.2f", emp.getGrossPay()),
                String.format("%.2f", emp.getNetPay())
            });
        }
    }

    private void actionAddEmployee() {
        try {
            if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID and Name cannot be empty!");
            }
            for (Employee emp : employeeList) {
                if (emp.getEmployeeNumber().equals(txtId.getText().trim())) {
                    throw new IllegalArgumentException("Employee ID already exists.");
                }
            }

            Employee newEmp = new Employee(
                txtId.getText().trim(), txtName.getText().trim(),
                Double.parseDouble(txtRate.getText().trim()), Double.parseDouble(txtHours.getText().trim()),
                Double.parseDouble(txtDeductions.getText().trim())
            );

            employeeList.add(newEmp);
            csvHandler.saveEmployees(employeeList);
            refreshTableDisplay();
            clearForm();
            JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Input Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actionUpdateEmployee() {
    int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String targetId = tableModel.getValueAt(selectedRow, 0).toString();
            for (Employee emp : employeeList) {
                if (emp.getEmployeeNumber().equals(targetId)) {
                    emp.setName(txtName.getText().trim());
                    emp.setHourlyRate(Double.parseDouble(txtRate.getText().trim()));
                    emp.setHoursWorked(Double.parseDouble(txtHours.getText().trim()));
                    emp.setDeductions(Double.parseDouble(txtDeductions.getText().trim()));
                    break;
                }
            }
            csvHandler.saveEmployees(employeeList);
            refreshTableDisplay();
            clearForm();
            JOptionPane.showMessageDialog(this, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid entry data formats.", "Input Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actionDeleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String targetId = tableModel.getValueAt(selectedRow, 0).toString();
            employeeList.removeIf(emp -> emp.getEmployeeNumber().equals(targetId));
            csvHandler.saveEmployees(employeeList);
            refreshTableDisplay();
            clearForm();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

 private void clearForm() {
        txtId.setText(""); txtName.setText(""); txtRate.setText(""); txtHours.setText(""); txtDeductions.setText("");
        employeeTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Feature2().setVisible(true));
    }
}
