//230939023
package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.domain.User;

import lostandfoundsystem.dao.ClaimDAO;
import lostandfoundsystem.domain.Claim;
import lostandfoundsystem.dao.LostItemDAO;
import lostandfoundsystem.domain.ReportCard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Dashboard extends JFrame implements ActionListener, ItemListener {

    private User currentUser;

    private JPanel sidebarPanel;
    private JPanel topPanel;
    private JPanel contentPanel;

    private ClaimDAO claimDAO;
    private LostItemDAO lostItemDAO;

    // Now able to access current user obj   
    public Dashboard(User currentUser) {
        this.currentUser = currentUser;
        claimDAO = new ClaimDAO();
        lostItemDAO = new LostItemDAO();
        guiSetUp();
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

        PageHeaderPanel header = new PageHeaderPanel(" ", currentUser);

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

        JPanel panel = new JPanel();
        panel.setBackground(new Color(220, 220, 220));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel claims = new JLabel("View All Claims");
        claims.setOpaque(true);
        claims.setBackground(new Color(40, 90, 255));
        claims.setForeground(Color.WHITE);
        claims.setBorder(new EmptyBorder(8, 15, 8, 15));

        panel.add(claims);
        panel.add(Box.createVerticalStrut(15));

        JPanel claimCards = new JPanel(new GridLayout(1, 3, 20, 20));
        claimCards.setOpaque(false);

        ArrayList<Claim> dashclaims = claimDAO.getLatestClaims();

        for (Claim claim : dashclaims) {
            Color statusColor;
            if (claim.getStatus().equalsIgnoreCase("Approved")) {
                statusColor = new Color(30, 180, 60);
            } else if (claim.getStatus().equalsIgnoreCase("Rejected")) {
                statusColor = Color.RED;
            } else {
                statusColor = Colors.YELLOW_STATUS_COLOR;
            }
            claimCards.add(
                    createClaimCard(
                            claim.getItemName(),
                            claim.getDate(),
                            claim.getStatus(),
                            statusColor
                    )
            );
        }

        panel.add(claimCards);
        panel.add(Box.createVerticalStrut(30));

        JLabel posts = new JLabel("View All Posts");
        posts.setOpaque(true);
        posts.setBackground(new Color(40, 90, 255));
        posts.setForeground(Color.WHITE);
        posts.setBorder(new EmptyBorder(8, 15, 8, 15));

        panel.add(posts);
        panel.add(Box.createVerticalStrut(15));

        JPanel cards = new JPanel(new GridLayout(2, 2, 20, 20));
        cards.setOpaque(false);

        ArrayList<ReportCard> dashPosts = lostItemDAO.getLatestItems();

        for (ReportCard post : dashPosts) {
            cards.add(
                    createPostCard(
                            post.getItemName(),
                            post.getDateLost(),
                            post.getLocation(),
                            post.getStatus()
                    )
            );
        }

        panel.add(cards);

        return panel;
    }

    private JPanel createClaimCard(String item, String date, String status, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(300, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JPanel info = new JPanel(new GridLayout(2, 2, 10, 10));
        info.setBorder(new EmptyBorder(15, 15, 15, 15));
        info.setOpaque(false);

        info.add(new JLabel("Item : " + item));

        JLabel st = new JLabel("Status : " + status);
        st.setForeground(color);
        info.add(st);

        info.add(new JLabel("Date Claimed : " + date));
        info.add(new JLabel());

        card.add(info, BorderLayout.CENTER);

        return card;
    }

    private JPanel createPostCard(String item, String date, String location, String status) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(300, 180));
        card.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(15, 15, 5, 15));

        JLabel lblItem = new JLabel("Item Name : " + item);
        lblItem.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel lblDate = new JLabel("Date Lost : " + date);
        lblDate.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblLocation = new JLabel("Location : " + location);
        lblLocation.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblStatus = new JLabel("Status : " + status);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        if (status.equalsIgnoreCase("Approved")) {
            lblStatus.setForeground(new Color(30, 180, 60));
        } else if (status.equalsIgnoreCase("Rejected")) {
            lblStatus.setForeground(Color.RED);
        } else {
            lblStatus.setForeground(Color.ORANGE);
        }

        info.add(lblItem);
        info.add(Box.createVerticalStrut(10));
        info.add(lblDate);
        info.add(Box.createVerticalStrut(10));
        info.add(lblLocation);
        info.add(Box.createVerticalStrut(10));
        info.add(lblStatus);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);

        JButton btnClaim = new JButton("Claim");
        JButton btnView = new JButton("View");

        buttons.add(btnClaim);
        buttons.add(btnView);

        card.add(info, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
}
