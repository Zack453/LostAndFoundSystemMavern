package lostandfoundsystem.components;

import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.constants.Icons;

import lostandfoundsystem.domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import lostandfoundsystem.windows.AdminReportWindow; // Added import for the Admin Report Window
import lostandfoundsystem.windows.ClaimWindow;
import lostandfoundsystem.windows.HelpWindow;
import lostandfoundsystem.windows.ProfileWindow;
import lostandfoundsystem.windows.ReportFoundItemWindow;
import lostandfoundsystem.windows.ReportLostItemWindow;
import lostandfoundsystem.windows.ViewAllPostsWindow;

public class SideBarPanel extends JPanel {
    
    private User currentUser;

    private JButton btnReportLostItem, btnReportFoundItem, btnViewAllPosts, btnClaims, btnAdminReport, btnHelp, btnLogout;

    public SideBarPanel(User currentUser) {
        this.currentUser = currentUser;
        createSideBar();
    }

    private void createSideBar() {

        setPreferredSize(new Dimension(240, 0));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 5, 20, 20));

        JLabel profile = new JLabel(currentUser.getName());
        profile.setFont(Fonts.Bold.deriveFont(24f));
        profile.setForeground(Colors.DARK_BLUE_TEXT_COLOR);
        profile.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        btnReportLostItem = createMenuButton(
                "Report Lost Item",
                Icons.LostItem
        );

        btnReportFoundItem = createMenuButton(
                "Report Found Item",
                Icons.FoundItem
        );

        btnViewAllPosts = createMenuButton(
                "View All Posts",
                Icons.ViewAllPosts
        );

        btnClaims = createMenuButton(
                "Claims",
                Icons.Claim
        );

        btnHelp = createMenuButton(
                "Help",
                Icons.Help
        );

        // Add standard menu buttons
        menu.add(btnReportLostItem);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnReportFoundItem);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnViewAllPosts);
        menu.add(Box.createVerticalStrut(10));

        menu.add(btnClaims);
        menu.add(Box.createVerticalStrut(10));

        // CONDITIONAL: Add Admin Report button ONLY if the current user is an admin
        // Note: Change `currentUser.isAdmin()` to match your User model method if needed (e.g., currentUser.getRole().equals("Admin"))
        if (currentUser != null && currentUser.getRole().equalsIgnoreCase("Admin")) {
            btnAdminReport = createMenuButton(
                    "Admin Report",
                    Icons.ViewAllPosts // Replace with Icons.AdminReport if available in your Icons class
            );

            btnAdminReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AdminReportWindow window = new AdminReportWindow(currentUser);
                    window.setVisible(true);
                    closeCurrentWindow();
                }
            });

            menu.add(btnAdminReport);
            menu.add(Box.createVerticalStrut(10));
        }

        menu.add(btnHelp);

        // Action Listeners for standard buttons
        btnReportLostItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportLostItemWindow window = new ReportLostItemWindow(currentUser);
                window.setVisible(true);
                closeCurrentWindow();
            }
        });

        btnReportFoundItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportFoundItemWindow window = new ReportFoundItemWindow(currentUser);
                window.setVisible(true);
                closeCurrentWindow();
            }
        });

        btnViewAllPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAllPostsWindow window = new ViewAllPostsWindow(currentUser);
                window.setVisible(true);
                closeCurrentWindow();
            }
        });

        btnClaims.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClaimWindow window = new ClaimWindow(currentUser);
                window.setVisible(true);
                closeCurrentWindow();
            }
        });

        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpWindow window = new HelpWindow(currentUser);
                window.setVisible(true);
                closeCurrentWindow();
            }
        });

        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ProfileWindow profileWindow = new ProfileWindow(currentUser);
                profileWindow.setVisible(true);
                closeCurrentWindow();
            }
        });

        btnLogout = createMenuButton(
                "Logout",
                Icons.Exit
        );

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(
                        SideBarPanel.this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    closeCurrentWindow();
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnLogout, BorderLayout.SOUTH);

        add(profile, BorderLayout.NORTH);
        add(menu, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, javax.swing.Icon icon) {

        JButton button = new JButton(text, icon);

        button.setFont(Fonts.Medium.deriveFont(15f));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(8);

        button.setMaximumSize(new Dimension(220, 40));
        button.setPreferredSize(new Dimension(220, 40));
        button.setAlignmentX(LEFT_ALIGNMENT);

        return button;
    }

    private void closeCurrentWindow() {

        JFrame currentWindow = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        if (currentWindow != null) {
            currentWindow.dispose();
        }
    }
}