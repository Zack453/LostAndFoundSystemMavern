package windows;

import constants.Fonts;
import constants.Icons;
//import 

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Dashboard extends JFrame implements ActionListener, ItemListener {

    private JPanel sidebarPanel;
    private JPanel topPanel;
    private JPanel contentPanel;

    // CHECK ROLE()
    // RENDER DIFFERENT COMPONENTS BASED ON ROLE
    
    public Dashboard() {
        guiSetUp();
    }

    private void guiSetUp() {

        setTitle("Dashboard");
        setSize(1400, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(61, 98, 133));
        setLayout(new BorderLayout(15, 15));

        sidebarPanel = createSidebar();
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

    private JPanel createSidebar() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(240, 0));
//        SPACING NEEDED
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel profile = new JLabel("PROFILE");
        profile.setFont(Fonts.Bold.deriveFont(24f));
        profile.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        String items[] = {
            "Report Lost Item",
            "Report Found Item",
            "View All Posts",
            "Claims",
            "Help"
        };

        for (String btnNames : items) {
            JButton btn = new JButton(btnNames);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setMaximumSize(new Dimension(220, 40));
            menu.add(btn);
        }

        JButton logout = new JButton("Logout");
        logout.setFocusPainted(false);

        panel.add(profile, BorderLayout.NORTH);
        panel.add(menu, BorderLayout.CENTER);
        panel.add(logout, BorderLayout.SOUTH);

        return panel;

    }

    private JPanel createTopPanel() {

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("DASHBOARD");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        titlePanel.add(title);

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setPreferredSize(new Dimension(0, 60));

        JButton btnHome = new JButton(Icons.Home);
        JButton btnSearch = new JButton(Icons.Search);
        JButton btnBell = new JButton(Icons.Bell);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.add(btnSearch);
        right.add(btnBell);

        nav.add(btnHome, BorderLayout.WEST);
        nav.add(right, BorderLayout.EAST);

        top.add(titlePanel);
        top.add(Box.createVerticalStrut(10));
        top.add(nav);

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashbaord = new Dashboard();
                dashbaord.setVisible(true);
                dispose();
            }
        });
        
        btnBell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationsWindow notificaionsWindow = new NotificationsWindow();
                notificaionsWindow.setVisible(true);
                dispose();
            }
        });

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

        JPanel claimCards = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        claimCards.setOpaque(false);

        claimCards.add(createClaimCard(
                "iPhone 11",
                "23-Jan-2026",
                "Pending",
                Color.ORANGE));

        claimCards.add(createClaimCard(
                "Student Card",
                "28-Jan-2026",
                "Approved",
                new Color(30, 180, 60)));

        panel.add(claimCards);

        panel.add(Box.createVerticalStrut(30));

        JLabel posts = new JLabel("View All Posts");
        posts.setOpaque(true);
        posts.setBackground(new Color(40, 90, 255));
        posts.setForeground(Color.WHITE);
        posts.setBorder(new EmptyBorder(8, 15, 8, 15));

        panel.add(posts);
        panel.add(Box.createVerticalStrut(15));

        JPanel cards = new JPanel(new GridLayout(1, 2, 20, 20));
        cards.setOpaque(false);

        cards.add(createPostCard(
                "Phone",
                "22-May-26",
                "Library",
                "Pending"));

        cards.add(createPostCard(
                "Wallet",
                "13-June-26",
                "Court Yard",
                "Pending"));

        panel.add(cards);

        return panel;
    }

    private JPanel createClaimCard(String item, String date, String status, Color color) {

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(260, 90));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(Color.LIGHT_GRAY));
        card.setLayout(new GridLayout(2, 2));

        card.add(new JLabel(item));
        JLabel st = new JLabel(status, SwingConstants.RIGHT);
        st.setForeground(color);
        card.add(st);

        card.add(new JLabel("Date Claimed: " + date));
        card.add(new JLabel());

        return card;
    }

    private JPanel createPostCard(String item, String date, String location, String status) {

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(Color.LIGHT_GRAY));
        card.setLayout(new BorderLayout());

        JPanel info = new JPanel(new GridLayout(4, 1));
        info.setOpaque(false);

        info.add(new JLabel("Item Name : " + item));
        info.add(new JLabel("Date Lost : " + date));
        info.add(new JLabel("Location : " + location));

        JLabel st = new JLabel("Status : " + status);
        st.setForeground(Color.ORANGE);

        info.add(st);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);

        buttons.add(new JButton("Claim"));
        buttons.add(new JButton("View"));

        card.add(info, BorderLayout.CENTER);
        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }

    
    // USING ANONYMOUS CLASSES    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
