package lostandfoundsystem.components;

import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.constants.Icons;

import lostandfoundsystem.domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import lostandfoundsystem.windows.Dashboard;
import lostandfoundsystem.windows.NotificationsWindow;
import lostandfoundsystem.windows.ViewAllPostsWindow;

public class PageHeaderPanel extends JPanel {

    private User currentUser;
    
    private JLabel lblHeader;

    private JButton btnHome;
    private JButton btnSearch;
    private JButton btnNotification;

    private JTextField txtSearch;

    private JPanel titlePanel;
    private JPanel navigationPanel;

    public PageHeaderPanel(String title, User currentUser) {
        
        this.currentUser = currentUser;

        setLayout(new BorderLayout(0, 10));
        setOpaque(false);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setPreferredSize(new Dimension(0, 55));

        lblHeader = new JLabel(title);
        lblHeader.setFont(Fonts.Bold.deriveFont(30f));
        lblHeader.setForeground(Colors.DARK_BLUE_TEXT_COLOR);

        titlePanel.add(lblHeader);

        navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(Color.WHITE);
        navigationPanel.setPreferredSize(new Dimension(0, 55));

        btnHome = new JButton(Icons.Home);
        btnHome.setContentAreaFilled(false);
        btnHome.setBorderPainted(false);
        btnHome.setFocusPainted(false);
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSearch = new JButton(Icons.Search);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnNotification = new JButton(Icons.Bell);
        btnNotification.setContentAreaFilled(false);
        btnNotification.setBorderPainted(false);
        btnNotification.setFocusPainted(false);
        btnNotification.setCursor(new Cursor(Cursor.HAND_CURSOR));

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(250, 35));
        txtSearch.setFont(Fonts.Medium.deriveFont(14f));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);

        leftPanel.add(btnHome);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        rightPanel.add(txtSearch);
        rightPanel.add(btnSearch);
        rightPanel.add(btnNotification);

        navigationPanel.add(leftPanel, BorderLayout.WEST);
        navigationPanel.add(rightPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
        add(navigationPanel, BorderLayout.CENTER);

        addButtonActions();
    }


        private void addButtonActions() {

    btnHome.addActionListener(e -> {

        new Dashboard(currentUser);

        java.awt.Window window =
                SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            window.dispose();
        }
    });

    btnSearch.addActionListener(e -> {

        new ViewAllPostsWindow(currentUser);

        java.awt.Window window =
                SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            window.dispose();
        }
    });

    btnNotification.addActionListener(e -> {

        new NotificationsWindow(currentUser);

        java.awt.Window window = SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            window.dispose();
        }
    });

    txtSearch.addActionListener(e -> {

        new ViewAllPostsWindow(currentUser);

        java.awt.Window window = SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            window.dispose();
        }
    });

    }

    public JButton getBtnHome() {
        return btnHome;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnNotification() {
        return btnNotification;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JLabel getLblHeader() {
        return lblHeader;
    }
}