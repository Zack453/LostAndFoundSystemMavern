package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.dao.NotificationDAO;
import lostandfoundsystem.domain.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AdminReportWindow extends JFrame implements ActionListener {

    private User currentUser;

    private JPanel sidebarPanel;
    private JPanel topPanel;
    private JPanel contentPanel;

    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextArea messageTextArea;
    private JButton btnSendMessage;
    
    private int selectedReportId = -1;
    private int selectedPersonId = -1; // Tracks the owner of the selected report for notifications

    private NotificationDAO notificationDAO;

    public AdminReportWindow(User currentUser) {
        this.currentUser = currentUser;
        this.notificationDAO = new NotificationDAO();
        guiSetUp();
        loadReportData();
    }

    private void guiSetUp() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(73, 107, 145));
        setLayout(new BorderLayout(15, 15));

        sidebarPanel = new SideBarPanel(currentUser);
        topPanel = createTopPanel();
        contentPanel = createContentPanel();

        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setOpaque(false);
        center.add(topPanel, BorderLayout.NORTH);
        center.add(contentPanel, BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        PageHeaderPanel header = new PageHeaderPanel("ADMIN REPORT ", currentUser);

        header.getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(currentUser);
                dashboard.setVisible(true);
                dispose();
            }
        });

        header.getBtnNotification().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationsWindow notificationsWindow = new NotificationsWindow(currentUser);
                notificationsWindow.setVisible(true);
                dispose();
            }
        });

        top.add(header, BorderLayout.CENTER);

        return top;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Manage Reports & Send Messages");
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(40, 90, 255));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(8, 15, 8, 15));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center section containing table and messaging inputs
        JPanel centerContent = new JPanel(new BorderLayout(10, 10));
        centerContent.setOpaque(false);

        // Table setup matching your table format columns
        String[] columnNames = {"Report ID", "Person ID", "Item ID", "Date Lost", "Location", "Item Type", "Admin Message"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells read-only
            }
        };
        reportTable = new JTable(tableModel);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reportTable.setRowHeight(25);

        // Listen for row selections to target specific issues and fetch person ID
        reportTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
                    int selectedRow = reportTable.getSelectedRow();
                    selectedReportId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    selectedPersonId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
                    
                    Object existingMsg = tableModel.getValueAt(selectedRow, 6);
                    messageTextArea.setText(existingMsg != null ? existingMsg.toString() : "");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(reportTable);
        centerContent.add(scrollPane, BorderLayout.CENTER);

        // Bottom messaging panel
        JPanel messagePanel = new JPanel(new BorderLayout(5, 5));
        messagePanel.setOpaque(false);
        messagePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        messagePanel.setPreferredSize(new Dimension(0, 140));

        JLabel lblMessagePrompt = new JLabel("Type message for selected report:");
        lblMessagePrompt.setFont(new Font("Arial", Font.BOLD, 14));

        messageTextArea = new JTextArea(4, 20);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        JScrollPane messageScroll = new JScrollPane(messageTextArea);

        btnSendMessage = new JButton("Send Message");
        btnSendMessage.setBackground(new Color(40, 90, 255));
        btnSendMessage.setForeground(Color.WHITE);
        btnSendMessage.setFont(new Font("Arial", Font.BOLD, 14));
        btnSendMessage.setFocusPainted(false);
        btnSendMessage.setBorder(new EmptyBorder(8, 15, 8, 15));

        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedReportId == -1 || selectedPersonId == -1) {
                    JOptionPane.showMessageDialog(AdminReportWindow.this, "Please select a report from the table first.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String message = messageTextArea.getText().trim();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminReportWindow.this, "Message cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                updateReportMessageAndNotify(selectedReportId, selectedPersonId, message);
            }
        });

        JPanel bottomActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomActionPanel.setOpaque(false);
        bottomActionPanel.add(btnSendMessage);

        messagePanel.add(lblMessagePrompt, BorderLayout.NORTH);
        messagePanel.add(messageScroll, BorderLayout.CENTER);
        messagePanel.add(bottomActionPanel, BorderLayout.SOUTH);

        centerContent.add(messagePanel, BorderLayout.SOUTH);
        panel.add(centerContent, BorderLayout.CENTER);

        return panel;
    }

    private void loadReportData() {
        String query = "SELECT REPORT_ID, PERSON_ID, ITEM_ID, DATE_LOST, LOCATION, ITEM_TYPE, ADMIN_MESSAGE FROM reports";

        try (Connection conn = DBConnection.derbyConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("REPORT_ID"),
                    rs.getInt("PERSON_ID"),
                    rs.getInt("ITEM_ID"),
                    rs.getString("DATE_LOST"),
                    rs.getString("LOCATION"),
                    rs.getString("ITEM_TYPE"),
                    rs.getString("ADMIN_MESSAGE")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading reports: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateReportMessageAndNotify(int reportId, int personId, String message) {
        String query = "UPDATE reports SET ADMIN_MESSAGE = ? WHERE REPORT_ID = ?";

        try (Connection conn = DBConnection.derbyConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, message);
            pstmt.setInt(2, reportId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Create a temporary User object containing the target user's person ID for NotificationDAO
                User targetUser = new User();
                targetUser.setPersonId(personId);

                // Trigger notification addition using your NotificationDAO
                notificationDAO.addNotification(targetUser, "Admin update regarding Report ID #" + reportId + ": " + message);

                JOptionPane.showMessageDialog(this, "Message successfully sent and notification dispatched to User ID: " + personId);
                loadReportData(); // Refresh table contents
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating message: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}