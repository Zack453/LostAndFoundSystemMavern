package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.NotificationDAO;
import lostandfoundsystem.domain.Notification;
import lostandfoundsystem.domain.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NotificationsWindow extends JFrame {

    private SideBarPanel sidebarPanel;
    private JPanel northPanel;
    private PageHeaderPanel headerPanel;
    private JLabel title;

    private JPanel centerPanel;
    private JPanel notificationsPanel;
    private JScrollPane scrollPane;
    
    private NotificationDAO notificationDAO;
    private User currentUser;

    public NotificationsWindow(User currentUser) {
        this.currentUser = currentUser;
        notificationDAO = new NotificationDAO();

        title = new JLabel("Notifications");
        title.setFont(Fonts.Bold.deriveFont(24f));
        title.setForeground(Colors.DARK_BLUE_TEXT_COLOR);

        northPanel = new JPanel();
        centerPanel = new JPanel();
        notificationsPanel = new JPanel();
        sidebarPanel = new SideBarPanel(currentUser);

        loadNotifications();
        guiSetUp();
    }

    private void guiSetUp() {
        // Setup North Panel (PageHeaderPanel layout matching Dashboard)
        northPanel.setLayout(new BorderLayout());
        northPanel.setOpaque(false);

        headerPanel = new PageHeaderPanel("Notifications", currentUser);

        headerPanel.getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(currentUser);
                dashboard.setVisible(true);
                dispose();
            }
        });

        headerPanel.getBtnNotification().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationsWindow notificationsWindow = new NotificationsWindow(currentUser);
                notificationsWindow.setVisible(true);
                dispose();
            }
        });

        northPanel.add(headerPanel, BorderLayout.CENTER);

        // Setup Notifications Content Panel matching Dashboard style
        notificationsPanel.setLayout(new GridLayout(0, 1, 15, 15));
        notificationsPanel.setBackground(new Color(220, 220, 220));
        notificationsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(220, 220, 220));
        wrapperPanel.add(notificationsPanel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        centerPanel.setLayout(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);
        centerPanel.add(northPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Main Frame Layout
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(73, 107, 145));

        add(sidebarPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadNotifications() {
        System.out.println("Logged-in Person ID: " + currentUser.getPersonId());

        notificationsPanel.removeAll();

        ArrayList<Notification> notifications = notificationDAO.getNotifications(currentUser);

        if (notifications == null || notifications.isEmpty()) {
            JLabel emptyLabel = new JLabel("No notifications available.", SwingConstants.CENTER);
            emptyLabel.setFont(Fonts.Regular.deriveFont(16f));
            notificationsPanel.add(emptyLabel);
        } else {
            for (Notification notification : notifications) {
                createNotificationCard(notification);
            }
        }

        notificationsPanel.revalidate();
        notificationsPanel.repaint();
    }

    private void createNotificationCard(Notification notification) {
        JPanel notificationPanel = new JPanel(new BorderLayout(10, 10));
        notificationPanel.setBackground(Color.WHITE);
        notificationPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        notificationPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JTextArea txtMessage = new JTextArea(notification.getMessage());
        txtMessage.setRows(3);
        txtMessage.setEditable(false);
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        txtMessage.setFont(Fonts.Regular.deriveFont(14f));
        txtMessage.setBackground(Color.WHITE);

        JLabel lblStatus = new JLabel("Status: " + notification.getStatus());
        JLabel lblDate = new JLabel("Date: " + notification.getDateCreated());
        JLabel lblPerson = new JLabel("Person ID: " + notification.getPersonId());

        JButton btnMarkAsRead = new JButton("Mark As Read");
        JButton btnDelete = new JButton("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(lblPerson);
        buttonPanel.add(lblStatus);
        buttonPanel.add(lblDate);
        buttonPanel.add(btnMarkAsRead);
        buttonPanel.add(btnDelete);

        notificationPanel.add(new JScrollPane(txtMessage), BorderLayout.CENTER);
        notificationPanel.add(buttonPanel, BorderLayout.SOUTH);

        notificationsPanel.add(notificationPanel);

        btnMarkAsRead.addActionListener(e -> {
            boolean updated = notificationDAO.updateStatus(notification.getNotificationId());
            if (updated) {
                loadNotifications();
            }
        });

        btnDelete.addActionListener(e -> {
            boolean deleted = notificationDAO.deleteNotification(notification.getNotificationId());
            if (deleted) {
                loadNotifications();
            }
        });
    }
}