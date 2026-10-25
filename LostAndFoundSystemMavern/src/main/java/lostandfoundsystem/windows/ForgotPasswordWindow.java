package lostandfoundsystem.windows;

//240822757

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

import lostandfoundsystem.components.UIComponents;
import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.passwordDAO;

public class ForgotPasswordWindow extends JFrame {

    private UIComponents.RoundedTextField txtPersonId;
    private JComboBox<String> cboSecurityQuestion;
    private UIComponents.RoundedTextField txtSecurityAnswer;
    private UIComponents.RoundedPasswordField txtNewPassword;
    private UIComponents.RoundedPasswordField txtConfirmPassword;

    public ForgotPasswordWindow() {

        setTitle("Forgot Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Colors.MAIN_BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Forgot Password");
        lblTitle.setFont(Fonts.Bold.deriveFont(26f));
        lblTitle.setForeground(Colors.WHITE_TEXT_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;

        // Person ID
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(createLabel("Person ID:"), gbc);

        txtPersonId = new UIComponents.RoundedTextField(18);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtPersonId, gbc);

        // Security Question
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(createLabel("Security Question:"), gbc);

        cboSecurityQuestion = new JComboBox<>(new String[]{
            "What is your pet's name?",
            "What is your favourite colour?",
            "What is your mother's maiden name?",
            "What city were you born in?"
        });

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(cboSecurityQuestion, gbc);

        // Security Answer
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(createLabel("Security Answer:"), gbc);

        txtSecurityAnswer = new UIComponents.RoundedTextField(18);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtSecurityAnswer, gbc);

        // New Password
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(createLabel("New Password:"), gbc);

        txtNewPassword = new UIComponents.RoundedPasswordField(18);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtNewPassword, gbc);

        // Confirm Password
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(createLabel("Confirm Password:"), gbc);

        txtConfirmPassword = new UIComponents.RoundedPasswordField(18);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtConfirmPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        UIComponents.RoundedButton btnCancel =
                new UIComponents.RoundedButton(
                        "Cancel",
                        Colors.BLACK_BUTTON_COLOR,
                        Colors.WHITE_TEXT_COLOR,
                        20);

        btnCancel.setPreferredSize(new Dimension(110, 35));

        btnCancel.addActionListener(e -> {
            new LogInWindow().setVisible(true);
            dispose();
        });

        UIComponents.RoundedButton btnSave =
                new UIComponents.RoundedButton(
                        "Save Changes",
                        Colors.WHITE_TEXT_COLOR,
                        Colors.BLACK_TEXT_COLOR,
                        20);

        btnSave.setPreferredSize(new Dimension(140, 35));

        btnSave.addActionListener(e -> validateAndSave());

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 10, 10, 10);

        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(Fonts.Medium.deriveFont(14f));
        label.setForeground(Colors.WHITE_TEXT_COLOR);

        return label;
    }

    private void validateAndSave() {

        String id = txtPersonId.getText().trim();
        String question = cboSecurityQuestion.getSelectedItem().toString();
        String answer = txtSecurityAnswer.getText().trim();
        String password = new String(txtNewPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());

        if (id.isEmpty()
                || answer.isEmpty()
                || password.isEmpty()
                || confirm.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!password.equals(confirm)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 6 characters long.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try (Connection conn = DBConnection.derbyConnection()) {

            passwordDAO dao = new passwordDAO();

            boolean success = dao.updatePassword(
                    conn,
                    Integer.parseInt(id),
                    question,
                    answer,
                    password
            );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password updated successfully!"
                );

                dispose();
                new LogInWindow().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Person ID, security question or answer is incorrect.",
                        "Reset Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Person ID must be a number."
            );

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }
}