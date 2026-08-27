// 230236901
package lostandfoundsystem.windows;

import lostandfoundsystem.domain.User;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.NotificationDAO;
import lostandfoundsystem.domain.Notification;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;

public class NotificationsWindow extends JFrame {

    private JPanel northPanel;
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

        northPanel = new JPanel();

        centerPanel = new JPanel();

        notificationsPanel = new JPanel();

        loadNotifications();
        guiSetUp();
        
    }

    private void guiSetUp() {

        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(title);

        notificationsPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(notificationsPanel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(wrapperPanel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        centerPanel.setLayout(new BorderLayout());

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout());

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadNotifications() {

        System.out.println("Logged-in Person ID: " + currentUser.getPersonId());

        notificationsPanel.removeAll();

        ArrayList<Notification> notifications
                = notificationDAO.getNotifications(currentUser);

        if (notifications == null || notifications.isEmpty()) {

            JLabel emptyLabel = new JLabel("No notifications available.", SwingConstants.CENTER);

            notificationsPanel.add(emptyLabel);

        } else {

            for (Notification notification : notifications) {

                createNotificationCard(notification);
            }
        }

        notificationsPanel.revalidate();
        notificationsPanel.repaint();
    }

    private void createNotificationCard(
            Notification notification) {

        JPanel notificationPanel = new JPanel(new BorderLayout(10, 10));

        JTextArea txtMessage = new JTextArea(notification.getMessage());

        txtMessage.setRows(4);
        txtMessage.setEditable(false);
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);

        JLabel lblStatus = new JLabel("Status: " + notification.getStatus());

        JLabel lblDate = new JLabel("Date: " + notification.getDateCreated());

        JLabel lblPerson = new JLabel("Person ID: " + notification.getPersonId());

        JButton btnMarkAsRead = new JButton("Mark As Read");

        JButton btnDelete = new JButton("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

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
