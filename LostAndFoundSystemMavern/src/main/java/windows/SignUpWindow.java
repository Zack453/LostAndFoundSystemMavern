package windows;

// CUSTOM IMPORTS
import constants.Fonts;
import constants.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame implements ActionListener {

    private JLabel title, lblName, lblSurname, lblIdNum, lblEmail, lblPassword, lblRoleSelection, lblSecQuestion, lblAnswer;
    private JTextField txtName, txtSurname, txtIdNum, txtEmail, txtAnswer;
    private JPasswordField txtPassword;
    private JComboBox<String> cboSecQuestion;
    private JRadioButton radStudent, radLecturer, radStaff, radAdmin;
    private ButtonGroup roleGroup;
    private JButton btnCancel, btnConfirm;
    private JPanel NorthPanel, CenterPanel, SouthPanel;

    public SignUpWindow() {

        super("Sign Up");

        NorthPanel = new JPanel();
        CenterPanel = new JPanel();
        SouthPanel = new JPanel();

        title = new JLabel("SIGN UP");
        title.setFont(Fonts.Bold.deriveFont(36f));
        title.setForeground(Color.WHITE);
        lblName = new JLabel("Name:");
        txtName = new JTextField(15);
        lblSurname = new JLabel("Surname:");
        txtSurname = new JTextField(15);
        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(15);
        lblIdNum = new JLabel("Student/Staff Number:");
        txtIdNum = new JTextField(15);
        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15); //HIDES VALUES

        lblRoleSelection = new JLabel("Role Selection:");

        radStudent = new JRadioButton("Student");
        radLecturer = new JRadioButton("Lecturer");
        radStaff = new JRadioButton("Staff");
        radAdmin = new JRadioButton("Admin");

        roleGroup = new ButtonGroup();
        roleGroup.add(radStudent);
        roleGroup.add(radLecturer);
        roleGroup.add(radStaff);
        roleGroup.add(radAdmin);

        // SECURITY QUESTION        
        lblSecQuestion = new JLabel("Security Question:");
        cboSecQuestion = new JComboBox<>(new String[]{
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What was your first school?"
        });
        cboSecQuestion.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        lblAnswer = new JLabel("Answer:");
        txtAnswer = new JTextField(15);

        btnCancel = new JButton("Cancel");
        btnConfirm = new JButton("Confirm");

        GuiSetUp();
    }

    public void GuiSetUp() {

        // NORTH PANEL
        NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        NorthPanel.add(title);
        NorthPanel.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        // CENTER PANEL
        CenterPanel.setLayout(new GridLayout(8, 2, 15, 15));
        CenterPanel.add(lblName);
        CenterPanel.add(txtName);
        CenterPanel.add(lblSurname);
        CenterPanel.add(txtSurname);
        CenterPanel.add(lblEmail);
        CenterPanel.add(txtEmail);
        CenterPanel.add(lblIdNum);
        CenterPanel.add(txtIdNum);
        CenterPanel.add(lblPassword);
        CenterPanel.add(txtPassword);
        // ROLE SELECTION
        CenterPanel.add(lblRoleSelection);
        CenterPanel.setBackground(Colors.LIGHT_GREY_BACKGROUND_COLOR);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        rolePanel.setBackground(Colors,.);
        rolePanel.add(radStudent);
        rolePanel.add(radLecturer);
        rolePanel.add(radStaff);
        rolePanel.add(radAdmin);

        CenterPanel.add(rolePanel);
        CenterPanel.add(lblSecQuestion);
        CenterPanel.add(cboSecQuestion);
        CenterPanel.add(lblAnswer);
        CenterPanel.add(txtAnswer);
        CenterPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(CenterPanel);
        wrapper.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        // SOUTH PANEL
        SouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        SouthPanel.add(btnCancel);
        SouthPanel.add(btnConfirm);
        SouthPanel.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        // BUTTONS
        btnCancel.addActionListener(this);
        btnConfirm.addActionListener(this);

        btnCancel.setBackground(Colors.WHITE_TEXT_COLOR);
        btnCancel.setForeground(Colors.BLACK_BUTTON_COLOR);
        btnConfirm.setBackground(Colors.BLUE_BUTTON_COLOR);
        btnConfirm.setForeground(Colors.WHITE_TEXT_COLOR);

        this.setLayout(new BorderLayout());
        this.add(NorthPanel, BorderLayout.NORTH);
        this.add(wrapper, BorderLayout.CENTER);
        this.add(SouthPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // CANCEL BUTTON
        if (e.getSource() == btnCancel) {
            new LogInWindow().setVisible(true);
            dispose();
            return;
        }

        // CONFIRM BUTTON
        if (e.getSource() == btnConfirm) {

            String name = txtName.getText().trim();
            String surname = txtSurname.getText().trim();
            String email = txtEmail.getText().trim();
            String idNum = txtIdNum.getText().trim();
            String password = new String(txtPassword.getPassword());
            String answer = txtAnswer.getText().trim();
            String securityQuestion = cboSecQuestion.getSelectedItem().toString();
            String role = "";

            if (radStudent.isSelected()) {
                role = "Student";
            } else if (radLecturer.isSelected()) {
                role = "Lecturer";
            } else if (radStaff.isSelected()) {
                role = "Staff";
            } else if (radAdmin.isSelected()) {
                role = "Admin";
            }

            // Validation
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || idNum.isEmpty() || password.isEmpty() || answer.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Display information (replacing with database logic later)
            System.out.println("========== NEW USER ==========");
            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Email: " + email);
            System.out.println("Student/Staff Number: " + idNum);
            System.out.println("Password: " + password);
            System.out.println("Role: " + role);
            System.out.println("Security Question: " + securityQuestion);
            System.out.println("Answer: " + answer);
            System.out.println("==============================");

            JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // TODO:
            // Create User object
            // Save to database/file
            // Navigate to login window if desired
            
            // CLEAR ALL FIELDS            
            txtName.setText("");
            txtSurname.setText("");
            txtEmail.setText("");
            txtIdNum.setText("");
            txtPassword.setText("");
            roleGroup.clearSelection();
            cboSecQuestion.setSelectedItem("select");
            txtAnswer.setText("");
        }
    }
}
