// 230939023
package lostandfoundsystem.windows;

// CUSTOM IMPORTS
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.UserDAO;
import lostandfoundsystem.domain.Admin;
import lostandfoundsystem.domain.Lecturer;
import lostandfoundsystem.domain.Staff;
import lostandfoundsystem.domain.Student;
import lostandfoundsystem.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame implements ActionListener {
    
    private User currentUser;

    private JLabel title;
    private JLabel lblName;
    private JLabel lblSurname;
    private JLabel lblIdNum;
    private JLabel lblPassword;
    private JLabel lblRoleSelection;
    private JLabel lblSecQuestion;
    private JLabel lblAnswer;

    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtIdNum;
    private JTextField txtAnswer;

    private JPasswordField txtPassword;

    private JComboBox<String> cboSecQuestion;

    private JRadioButton radStudent;
    private JRadioButton radLecturer;
    private JRadioButton radStaff;
    private JRadioButton radAdmin;

    private ButtonGroup roleGroup;

    private JButton btnCancel;
    private JButton btnConfirm;

    private JPanel NorthPanel;
    private JPanel CenterPanel;
    private JPanel SouthPanel;

    public SignUpWindow(User currentUser) {

        this.currentUser = currentUser;
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

        lblIdNum = new JLabel("ID Number:");
        txtIdNum = new JTextField(15);

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);

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

        NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        NorthPanel.add(title);
        NorthPanel.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        CenterPanel.setLayout(new GridLayout(7, 2, 15, 15));

        CenterPanel.add(lblName);
        CenterPanel.add(txtName);

        CenterPanel.add(lblSurname);
        CenterPanel.add(txtSurname);

        CenterPanel.add(lblIdNum);
        CenterPanel.add(txtIdNum);

        CenterPanel.add(lblPassword);
        CenterPanel.add(txtPassword);

        CenterPanel.add(lblRoleSelection);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        rolePanel.add(radStudent);
        rolePanel.add(radLecturer);
        rolePanel.add(radStaff);
        rolePanel.add(radAdmin);

        CenterPanel.add(rolePanel);

        CenterPanel.add(lblSecQuestion);
        CenterPanel.add(cboSecQuestion);

        CenterPanel.add(lblAnswer);
        CenterPanel.add(txtAnswer);

        CenterPanel.setBackground(Colors.LIGHT_GREY_BACKGROUND_COLOR);
        CenterPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel wrapper = new JPanel(new GridBagLayout());

        wrapper.add(CenterPanel);
        wrapper.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        SouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        SouthPanel.add(btnCancel);
        SouthPanel.add(btnConfirm);

        SouthPanel.setBackground(Colors.MAIN_BACKGROUND_COLOR);

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

        if (e.getSource() == btnCancel) {
            new LogInWindow().setVisible(true);
            dispose();
            return;
        }

        if (e.getSource() == btnConfirm) {

            String name = txtName.getText().trim();
            String surname = txtSurname.getText().trim();
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

            if (name.isEmpty()
                    || surname.isEmpty()
                    || idNum.isEmpty()
                    || password.isEmpty()
                    || answer.isEmpty()
                    || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int personId;
            try {
                personId = Integer.parseInt(idNum);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Number must contain numbers only.", "Invalid ID Number", JOptionPane.WARNING_MESSAGE);
                return;
            }

            UserDAO userDAO = new UserDAO();

            User currentUser = null;
            boolean saved = false;

            if (role.equals("Student")) {

                Student student = new Student(
                        personId,
                        name,
                        surname,
                        password,
                        securityQuestion,
                        answer,
                        idNum,
                        role
                );

                saved = userDAO.saveStudent(student);
                currentUser = student;

            } else if (role.equals("Lecturer")) {

                Lecturer lecturer = new Lecturer(
                        personId,
                        name,
                        surname,
                        password,
                        securityQuestion,
                        answer,
                        role,
                        idNum,
                        "Not Assigned"
                );

                saved = userDAO.saveLecturer(lecturer);
                currentUser = lecturer;

            } else if (role.equals("Staff")) {

                Staff staff = new Staff(
                        personId,
                        name,
                        surname,
                        password,
                        securityQuestion,
                        answer,
                        role,
                        idNum,
                        "Not Assigned"
                );
                saved = userDAO.saveStaff(staff);
                currentUser = staff;
                
            } else if (role.equals("Admin")) {
                
                Admin admin = new Admin(
                        personId,
                        name,
                        surname,
                        password,
                        securityQuestion,
                        role,
                        answer,
                        idNum,
                        "Not Assigned",
                        1
                );

                saved = userDAO.saveAdmin(admin);
                currentUser = admin;
            }

            if (saved) {
                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                new Dashboard(currentUser).setVisible(true);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Account could not be created.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
