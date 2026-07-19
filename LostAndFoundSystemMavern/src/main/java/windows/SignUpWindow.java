//230939023
package windows;

import constants.Fonts;
import constants.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignUpWindow extends JFrame implements ActionListener, ItemListener {

    private JLabel title;

    private JLabel lblName;
    private JTextField txtName;

    private JLabel lblSurname;
    private JTextField txtSurname;

    private JLabel lblIdNum;
    private JTextField txtIdNum;

    private JLabel lblEmail;
    private JTextField txtEmail;

    private JLabel lblPassword;
    private JTextField txtPassword;

    private JLabel lblSecQuestion;
    private JComboBox cboSecQuestion;

    private JLabel lblRoleSelection;
    private JRadioButton radStudent, radLecturer, radStaff, radAdmin;
    private ButtonGroup roleGroup;

    private JButton btnCancel, btnSignUp;

    private JPanel NorthPanel, CenterPanel, SouthPanel;

    public SignUpWindow() {
        super("Sign Up Window");

        CenterPanel = new JPanel();
        NorthPanel = new JPanel();
        SouthPanel = new JPanel();

        // NORTH PANEL        
        title = new JLabel("SIGN UP");
        title.setFont(Fonts.Bold.deriveFont(36f));

        // SOUTH PANEL        
        lblName = new JLabel("Name:");
        txtName = new JTextField(15);

        lblSurname = new JLabel("Surname:");
        txtSurname = new JTextField(15);

        lblIdNum = new JLabel("Student/Staff Number:");
        txtIdNum = new JTextField(15);

        lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(15);

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(15);

        lblSecQuestion = new JLabel("Security Question:");
        cboSecQuestion = new JComboBox(new String[]{
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What was your first school?"
        });
        cboSecQuestion.setBackground(Colors.LOGIN_BACKGROUND_COLOR);

        lblRoleSelection = new JLabel("Role selection:");

        radStudent = new JRadioButton("Student");
        radLecturer = new JRadioButton("Lecturer");
        radStaff = new JRadioButton("Staff");
        radAdmin = new JRadioButton("Admin");

        roleGroup = new ButtonGroup();
        roleGroup.add(radStudent);
        roleGroup.add(radLecturer);
        roleGroup.add(radStaff);
        roleGroup.add(radAdmin);

        btnCancel = new JButton("Cancel");
        btnSignUp = new JButton("Sign Up");

        guiSetUp();
    }

    public void guiSetUp() {

        NorthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        NorthPanel.add(title);

        CenterPanel.setLayout(new GridLayout(6, 2));
        CenterPanel.add(lblName);
        CenterPanel.add(txtName);
        CenterPanel.add(lblSurname);
        CenterPanel.add(txtSurname);
        CenterPanel.add(lblEmail);
        CenterPanel.add(txtEmail);
        CenterPanel.add(lblIdNum);
        CenterPanel.add(txtIdNum);

        SouthPanel.setLayout(new GridLayout(1,1));
        
        
        
//        this.add(mainPanel);

//        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
}
