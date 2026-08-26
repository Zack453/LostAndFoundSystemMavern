//231323573

package lostandfoundsystem.windows;

import lostandfoundsystem.domain.User;
import lostandfoundsystem.connection.DBConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminSupportWindow extends JFrame {

    private User currentUser;
    
    private JPanel mainPanel;
    private JPanel formPanel;

    private JLabel lblTitle;
    private JLabel lblName;
    private JLabel lblStudentNo;
    private JLabel lblIssue;

    private JTextField txtName;
    private JTextField txtStudentNo;
    private JTextArea txtIssue;

    private JButton btnCancel;
    private JButton btnSubmit;

    public AdminSupportWindow(User currentUser) {
        this.currentUser = currentUser;
        guiSetUp();
    }

    private void guiSetUp() {

        Color background = new Color(79, 113, 146);

        setTitle("Campus Finder - Admin Support");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(background);
        setLayout(new GridBagLayout());

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitle = new JLabel("Admin Support");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        formPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;

        lblName = new JLabel("Name:");
        lblName.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(lblName, gbc);

        txtName = new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(txtName, gbc);

        lblStudentNo = new JLabel("Student/Staff Number:");
        lblStudentNo.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(lblStudentNo, gbc);

        txtStudentNo = new JTextField(15);

        gbc.gridx = 1;

        formPanel.add(txtStudentNo, gbc);

        lblIssue = new JLabel("Issue Description:");
        lblIssue.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        formPanel.add(lblIssue, gbc);

        txtIssue = new JTextArea(5, 15);
        txtIssue.setLineWrap(true);
        txtIssue.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtIssue);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        formPanel.add(scroll, gbc);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        0
                )
        );

        buttonPanel.setOpaque(false);

        btnCancel = new JButton("Cancel");
        btnSubmit = new JButton("Submit");

        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);

        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setForeground(Color.BLACK);
        btnSubmit.setFocusPainted(false);

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSubmit);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        formPanel.add(buttonPanel, gbc);

        add(formPanel);

        btnCancel.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel?",
                    "Cancel Support Request",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {

                dispose();

                new HelpWindow(currentUser);
            }
        });

        btnSubmit.addActionListener(e -> {

            String name = txtName.getText().trim();
            String studentNumber = txtStudentNo.getText().trim();
            String issue = txtIssue.getText().trim();

            if (name.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your name.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                txtName.requestFocus();

                return;
            }

            if (studentNumber.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your Student/Staff Number.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                txtStudentNo.requestFocus();

                return;
            }

            if (issue.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please describe your issue.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                txtIssue.requestFocus();

                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to submit this support request?",
                    "Submit Support Request",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                
                String sql = "INSERT INTO ADMIN_SUPPORT "
                        + "(name, student_number, issue_description) "
                        + "VALUES (?, ?, ?)";
                
                try (Connection connection = DBConnection.derbyConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

                        statement.setString(1, name);
                        statement.setString(2, studentNumber);
                        statement.setString(3, issue);

                        statement.executeUpdate();

                    JOptionPane.showMessageDialog(
                        this,
                        "Your support request has been submitted successfully.\n\n"
                        + "An administrator will review your request.",
                        "Support Request Submitted",
                        JOptionPane.INFORMATION_MESSAGE
                );

                txtName.setText("");
                txtStudentNo.setText("");
                txtIssue.setText("");

                txtName.requestFocus();
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                this,
                "Failed to submit support request.\n\n"
                + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
                );
            }
        }  
        
    });

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
    }
}