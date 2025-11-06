package Frontend;

import javax.swing.*;
import java.awt.*; // ✅ For GridLayout
import Backend.EmployeeDAO; // ✅ Adjust this if your DAO is in a different package
import Backend.Frontend;    // ✅ Adjust this if your main GUI is in a different package

public class LoginScreen extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;

    public LoginScreen() {
        setTitle("Admin Login");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10)); // ✅ Added spacing for better layout

        userField = new JTextField();
        passField = new JPasswordField();
        loginBtn = new JButton("Login");

        add(new JLabel("Username:")); add(userField);
        add(new JLabel("Password:")); add(passField);
        add(new JLabel()); add(loginBtn);

        loginBtn.addActionListener(e -> authenticate());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // ✅ Center the window
        setVisible(true);
    }

    private void authenticate() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        EmployeeDAO dao = new EmployeeDAO();
        String role = dao.authenticate(user, pass);

        if (role != null) {
            dispose();
            new Frontend(role); // ✅ Make sure Frontend has a constructor that accepts role
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
