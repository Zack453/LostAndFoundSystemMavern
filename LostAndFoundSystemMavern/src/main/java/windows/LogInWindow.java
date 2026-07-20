//230939023
package windows;

// CUSTOM IMPORTS
import constants.Colors;
import constants.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LogInWindow extends JFrame implements ActionListener, ItemListener {

    private JLabel title, lblUsername, lblPassword, lblUserType, lblForgotPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox cboUserType;
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

        lblUsername = new JLabel("Username :");
        lblPassword = new JLabel("Password :");

        txtUsername = new JTextField(20);
        txtUsername.setFont(Fonts.Regular.deriveFont(16f));

        txtPassword = new JPasswordField(20); // FIXED (better security + correct use)
        txtPassword.setFont(Fonts.Regular.deriveFont(16f));

        lblUserType = new JLabel("Select User Type");
        cboUserType = new JComboBox(new String[]{
            "Student",
            "Lecturer",
            "Staff",
            "Admin"
        });
        cboUserType.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        lblForgotPassword = new JLabel("Forgot Password?");
        btnForgotPassword = new JButton("Reset");

        btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(Fonts.Regular.deriveFont(16f));
//        btnSignUp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));

        btnLogIn = new JButton("Log In");
        btnLogIn.setFont(Fonts.Regular.deriveFont(16f));
//        btnLogIn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));

        txtUsername.requestFocusInWindow();

        GuiSetup();
    }

    public void GuiSetup() {

        // NORTH PANEL
        NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        NorthPanel.add(title);
        NorthPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        // DETAILS PANEL
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

        // CENTER PANEL (Centers the DetailsPanel)
        CenterPanel.setLayout(new GridBagLayout());
        CenterPanel.add(DetailsPanel);
        CenterPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        // SOUTH PANEL
        SouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        SouthPanel.add(btnSignUp);
        SouthPanel.add(btnLogIn);
        SouthPanel.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        cboUserType.addItemListener(this);

        //BUTTONS
        btnForgotPassword.addActionListener(this);
        btnSignUp.addActionListener(this);
        btnLogIn.addActionListener(this);

        btnSignUp.setBackground(Colors.WHITE_TEXT_COLOR);
        btnSignUp.setForeground(Colors.BLACK_TEXT_COLOR);
        btnLogIn.setBackground(Colors.BLUE_BUTTON_COLOR);
        btnLogIn.setForeground(Colors.WHITE_TEXT_COLOR);

        this.setLayout(new BorderLayout());
        this.add(NorthPanel, BorderLayout.NORTH);
        this.add(CenterPanel, BorderLayout.CENTER);
        this.add(SouthPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // SIGN UP
        if (e.getSource() == btnSignUp) {
            new SignUpWindow().setVisible(true);
            dispose();
            return;
        }

        // FORGOT PASSWORD
        if (e.getSource() == btnForgotPassword) {
            new ForgotPasswordWindow().setVisible(true);
            dispose();
            return;
        }

        // LOG IN
        if (e.getSource() == btnLogIn) {

            String username = txtUsername.getText().trim();
            String password = new String(((JPasswordField) txtPassword).getPassword());
            String userType = cboUserType.getSelectedItem().toString();

            // Validation
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your username and password.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // DATABASE LOGIN WILL GO HERE
            // Temporary login until database is connected
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new Dashboard().setVisible(true);
            dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
