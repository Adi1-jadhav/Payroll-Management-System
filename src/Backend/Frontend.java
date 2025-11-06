package Backend;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.io.IOException;

import Frontend.RoleManager;
import Frontend.PDFExporter;
import Frontend.ReportGenerator;

public class Frontend extends JFrame {
    private JTextField idField, nameField, salaryField, hoursField, rateField;
    private JTextArea outputArea;
    private JComboBox<String> typeBox;
    private EmployeePayrollSystem payroll = new EmployeePayrollSystem();

    private JButton addBtn, showBtn, deleteBtn, updateBtn, slipBtn, exportPDFBtn, exportCSVBtn;

    public Frontend(String role) {
        setTitle("Employee Payroll System");
        setSize(800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(15);
        nameField = new JTextField(15);
        salaryField = new JTextField(15);
        hoursField = new JTextField(15);
        rateField = new JTextField(15);
        typeBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});

        String[] labels = {"ID:", "Name:", "Type:", "Monthly Salary:", "Hours Worked:", "Hourly Rate:"};
        JTextField[] fields = {idField, nameField, null, salaryField, hoursField, rateField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            inputPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            if (fields[i] != null) {
                inputPanel.add(fields[i], gbc);
            } else {
                inputPanel.add(typeBox, gbc);
            }
        }

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        addBtn = new JButton("Add");
        showBtn = new JButton("Show All");
        deleteBtn = new JButton("Delete");
        updateBtn = new JButton("Update");
        slipBtn = new JButton("Generate Slip");
        exportPDFBtn = new JButton("Export PDF");
        exportCSVBtn = new JButton("Export Report");

        buttonPanel.add(addBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(slipBtn);
        buttonPanel.add(exportPDFBtn);
        buttonPanel.add(exportCSVBtn);

        // Output Area
        outputArea = new JTextArea(10, 50);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Role-based access control
        if (!RoleManager.canEdit(role)) {
            addBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
            updateBtn.setEnabled(false);
        }

        // Button Actions
        addBtn.addActionListener(e -> addEmployee());
        showBtn.addActionListener(e -> outputArea.setText(payroll.showAllEmployees()));
        deleteBtn.addActionListener(e -> deleteEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        slipBtn.addActionListener(e -> generateSlip());
        exportPDFBtn.addActionListener(e -> exportPDF());
        exportCSVBtn.addActionListener(e -> exportCSV());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String type = (String) typeBox.getSelectedItem();

        if (type.equals("Full-Time")) {
            double salary = Double.parseDouble(salaryField.getText());
            payroll.addEmployee(new FullTimeEmployee(id, name, salary));
        } else {
            int hours = Integer.parseInt(hoursField.getText());
            double rate = Double.parseDouble(rateField.getText());
            payroll.addEmployee(new PartTimeEmployee(id, name, hours, rate));
        }

        outputArea.setText("✅ Employee added successfully.\n");
    }

    private void deleteEmployee() {
        int id = Integer.parseInt(idField.getText());
        boolean success = payroll.deleteEmployee(id);
        outputArea.setText(success ? "🗑️ Employee deleted.\n" : "⚠️ Employee not found.\n");
    }

    private void updateEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String type = (String) typeBox.getSelectedItem();

        Employee emp;
        if (type.equals("Full-Time")) {
            double salary = Double.parseDouble(salaryField.getText());
            emp = new FullTimeEmployee(id, name, salary);
        } else {
            int hours = Integer.parseInt(hoursField.getText());
            double rate = Double.parseDouble(rateField.getText());
            emp = new PartTimeEmployee(id, name, hours, rate);
        }

        boolean success = payroll.updateEmployee(emp);
        outputArea.setText(success ? "✏️ Employee updated.\n" : "⚠️ Employee not found.\n");
    }

    private void generateSlip() {
        int id = Integer.parseInt(idField.getText());
        outputArea.setText(payroll.generateSlip(id));
    }

    private void exportPDF() {
        int id = Integer.parseInt(idField.getText());
        Employee emp = payroll.getEmployee(id);
        if (emp != null) {
            try {
                String path = System.getProperty("user.home") + "/Desktop/SalarySlip_" + emp.getId() + ".pdf";
                new PDFExporter().exportToPDF(emp, path);
                outputArea.setText("📄 PDF exported to Desktop: " + path);

                // ✅ Open the file automatically
                java.awt.Desktop.getDesktop().open(new java.io.File(path));
            } catch (Exception ex) {
                outputArea.setText("❌ Error exporting or opening PDF.");
                ex.printStackTrace();
            }
        } else {
            outputArea.setText("⚠️ Employee not found.");
        }
    }

    private void exportCSV() {
        try {
            List<Employee> all = payroll.getAllEmployees();
            String path = System.getProperty("user.home") + "/Desktop/PayrollReport.csv";
            new ReportGenerator().exportReport(all, path);
            outputArea.setText("📊 Report exported to Desktop: " + path);

            // ✅ Open the file automatically
            java.awt.Desktop.getDesktop().open(new java.io.File(path));
        } catch (IOException ex) {
            outputArea.setText("❌ Error exporting or opening report.");
            ex.printStackTrace();
        }
    }


}
