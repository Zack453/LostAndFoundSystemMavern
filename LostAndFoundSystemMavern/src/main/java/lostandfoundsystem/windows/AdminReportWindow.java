package lostandfoundsystem.windows;

import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.domain.User;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminReportWindow extends JFrame {

    private User currentUser;

    public AdminReportWindow(User currentUser) {
        this.currentUser = currentUser;
        initUI();
    }

    private void initUI() {
        setTitle("Admin - Check Logged Issues");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(73, 107, 145));
        setLayout(new BorderLayout(15, 15));

        // Sidebar
        JPanel sidebarPanel = new SideBarPanel(currentUser);
        add(sidebarPanel, BorderLayout.WEST);

        // Content Panel for Issues
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(220, 220, 220));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setLayout(new BorderLayout());

        // Header / Title
        JLabel titleLabel = new JLabel("User Logged Issues & Support Tickets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // TODO: Populate table or cards with issues fetched from a DAO (e.g., IssueDAO)
        JTextArea placeholderText = new JTextArea("List of user-reported issues will appear here.\n(Integrate your IssueDAO / model to display list entries.)");
        placeholderText.setEditable(false);
        placeholderText.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(new JScrollPane(placeholderText), BorderLayout.CENTER);

        // Back / Return Button
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.addActionListener(e -> {
            Dashboard dashboard = new Dashboard(currentUser);
            dashboard.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnBack);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}