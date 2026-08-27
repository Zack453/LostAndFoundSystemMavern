// 230939023
package lostandfoundsystem.windows;

// CUSTOM IMPORTS
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.UserDAO;

import lostandfoundsystem.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lostandfoundsystem.dao.NotificationDAO;

public class LogInWindow extends JFrame implements ActionListener {

    private User currentUser;
    
    private JLabel title, lblUsername, lblPassword, lblUserType, lblForgotPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cboUserType;
    private JButton btnForgotPassword, btnLogIn, btnSignUp;
    private JPanel NorthPanel, DetailsPanel, CenterPanel, SouthPanel;

    public LogInWindow() {
        

        super("Log In");

        NorthPanel = new JPanel();
        DetailsPanel = new JPanel();
        CenterPanel = new JPanel();
        SouthPanel = new JPanel();
        
        title = new JLabel("Campus Findr");
        title.setFont(Fonts.Bold.deriveFont(24f));
        
        lblUsername = new JLabel("ID Number:");
        txtUsername = new JTextField(20);
        txtUsername.setFont(Fonts.Regular.deriveFont(16f));

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(Fonts.Regular.deriveFont(16f));

        lblUserType = new JLabel("Select User Type");

        cboUserType = new JComboBox<>(new String[]{
            "Student",
            "Lecturer",
            "Staff",
            "Admin"
        });

        cboUserType.setBackground(
                Colors.LOGIN_BACKGROUND_COLOR
        );

        // FORGOT PASSWORD
        lblForgotPassword = new JLabel("Forgot Password?");
        btnForgotPassword = new JButton("Reset");

        // SIGN UP
        btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(Fonts.Regular.deriveFont(16f));

        btnLogIn = new JButton("Log In");
        btnLogIn.setFont(Fonts.Regular.deriveFont(16f));

        GuiSetup();
    }

    public void GuiSetup() {

        NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        NorthPanel.add(title);
        NorthPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);
        
        DetailsPanel.setLayout(new GridLayout(8, 1, 10, 15));
        DetailsPanel.add(lblUsername);
        DetailsPanel.add(txtUsername);
        DetailsPanel.add(lblPassword);
        DetailsPanel.add(txtPassword);
        DetailsPanel.add(lblUserType);
        DetailsPanel.add(cboUserType);
        DetailsPanel.add(lblForgotPassword);
        DetailsPanel.add(btnForgotPassword);
        DetailsPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);
        
        CenterPanel.setLayout(new GridBagLayout());
        CenterPanel.add(DetailsPanel);
        CenterPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);
        
        SouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        SouthPanel.add(btnSignUp);
        SouthPanel.add(btnLogIn);
        SouthPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        btnForgotPassword.addActionListener(this);
        btnSignUp.addActionListener(this);
        btnLogIn.addActionListener(this);
        
        btnSignUp.setBackground(Colors.WHITE_TEXT_COLOR);
        btnSignUp.setForeground(Colors.BLACK_TEXT_COLOR);
        
        btnLogIn.setBackground(Colors.BLUE_BUTTON_COLOR);
        btnLogIn.setForeground(Colors.WHITE_TEXT_COLOR);
        
        this.setLayout(new BorderLayout());
        this.add(NorthPanel,BorderLayout.NORTH);
        this.add(CenterPanel,BorderLayout.CENTER);
        this.add(SouthPanel,BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSignUp) {
            new SignUpWindow(currentUser).setVisible(true);
            dispose();
            return;
        }
        
        if (e.getSource() == btnForgotPassword) {
            new ForgotPasswordWindow().setVisible(true);
            dispose();
            return;
        }
        
        if (e.getSource() == btnLogIn) {
            String idNum = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String userType = cboUserType.getSelectedItem().toString();
            // VALIDATION
            if (idNum.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your ID number and password.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // CONVERT ID NUMBER
            int personId;
            try {
                personId = Integer.parseInt(idNum);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "ID Number must contain numbers only.",
                        "Invalid ID Number",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // LOGIN
            UserDAO UserDAO = new UserDAO();
            currentUser = UserDAO.login(
                    personId,
                    password,
                    userType
            );

            // LOGIN RESULT
            if (currentUser != null) {
                NotificationDAO notificationDAO = new NotificationDAO();
                notificationDAO.addNotification(currentUser, "Welcome back! You logged in successfully.");
                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new Dashboard(currentUser).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid ID number or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}