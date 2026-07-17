//230939023
package windows;

// Custom imports

import constants.Colors;
import constants.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 * @230939023
 */

public class LogInWindow extends JFrame implements ActionListener, ItemListener {

    private JLabel title;

    private JLabel lblUsername;
    private JTextField txtUsername;

    private JLabel lblPassword;
    private JTextField txtPassword;

    private JLabel lblUserType;
    private JComboBox cboUserType;

    private JLabel lblForgotPassword;
    private JButton btnForgotPassword;

    private JButton btnLogIn, btnSignUp;

    private JPanel panelNorth, panelDetails, panelCenter, panelSouth;

    public LogInWindow() {

        super("Log In Window");

        panelNorth = new JPanel();
        panelDetails = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        title = new JLabel("Log In");
        title.setFont(Fonts.Bold.deriveFont(24f));

        txtUsername = new JTextField("Enter Username...", 20);
        txtUsername.setFont(Fonts.Regular.deriveFont(24f));

        txtPassword = new JTextField("Enter Password...", 20); // FIXED (better security + correct use)
        txtPassword.setFont(Fonts.Regular.deriveFont(24f));

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

        btnLogIn = new JButton("Log In");
        btnLogIn.setFont(Fonts.Regular.deriveFont(24f));
        btnLogIn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));

        btnSignUp = new JButton("Sign Up");
        btnSignUp.setFont(Fonts.Regular.deriveFont(24f));
        btnSignUp.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1, true));

        txtUsername.requestFocusInWindow();

        GuiSetup();
    }

    public void GuiSetup() {

        panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelNorth.add(title);
        panelNorth.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        panelDetails.setLayout(new GridLayout(8, 1, 10, 10));
        panelDetails.add(txtUsername);
        panelDetails.add(txtPassword);
        panelDetails.add(lblUserType);
        panelDetails.add(cboUserType);
        panelDetails.add(lblForgotPassword);
        panelDetails.add(btnForgotPassword);
        panelDetails.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        panelCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelCenter.add(panelDetails);
        panelCenter.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelSouth.add(btnLogIn);
        panelSouth.add(btnSignUp);
        panelSouth.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        cboUserType.addItemListener(this);

        //LOGIN BUTTON
        btnLogIn.addActionListener(this);
        btnLogIn.setBackground(Colors.BLUE_BUTTON_COLOR); // BACKGROUND COLOR
        btnLogIn.setForeground(Colors.WHITE_TEXT_COLOR); //TEXT COLOR

        //SIGNUP BUTTON
        btnSignUp.addActionListener(this);
        btnSignUp.setBackground(Colors.WHITE_TEXT_COLOR);
        btnSignUp.setForeground(Colors.BLACK_TEXT_COLOR);

        btnForgotPassword.addActionListener(this);

        this.setLayout(new BorderLayout(10, 0));
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSignUp) {
            SignUpWindow signUp = new SignUpWindow();
            signUp.setVisible(true);
            this.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}