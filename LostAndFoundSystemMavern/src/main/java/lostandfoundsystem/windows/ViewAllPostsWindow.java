//230236901
package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.components.UIComponents;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.ArrayList;

import lostandfoundsystem.dao.LostItemDAO;
import lostandfoundsystem.domain.ReportCard;
import lostandfoundsystem.domain.User;

public class ViewAllPostsWindow extends JFrame {

    private User currentUser;
    private LostItemDAO lostItemDAO;

    private JPanel sidebarPanel;
    private JPanel headerPanel;
    private JPanel filterPanel;
    private JPanel centerPanel;
    private JPanel itemsPanel;
    private JComboBox<String> cmbFilter;

    private ArrayList<ReportCard> allReports;

    public ViewAllPostsWindow(
            User currentUser) {

        this.currentUser = currentUser;

        this.lostItemDAO =
                new LostItemDAO();

        guiSetUp();
    }

    private void guiSetUp() {

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        getContentPane().setBackground(
                new Color(73, 107, 145)
        );

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        sidebarPanel =
                new SideBarPanel(
                        currentUser
                );

        headerPanel =
                createHeaderPanel();

        centerPanel =
                createCenterPanel();

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        center.setOpaque(false);

        center.add(
                headerPanel,
                BorderLayout.NORTH
        );

        center.add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(
                sidebarPanel,
                BorderLayout.WEST
        );

        add(
                center,
                BorderLayout.CENTER
        );

        setVisible(true);
    }

    private JPanel createHeaderPanel() {

        return new PageHeaderPanel(
                "VIEW ALL POSTS",
                currentUser
        );
    }

    private JPanel createCenterPanel() {

        centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        centerPanel.setBackground(
                new Color(73, 107, 145)
        );

        centerPanel.add(
                createContentPanel(),
                BorderLayout.CENTER
        );

        return centerPanel;
    }

    private JPanel createContentPanel() {

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        contentPanel.setBackground(
                new Color(220, 220, 220)
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        contentPanel.add(
                createFilterPanel(),
                BorderLayout.NORTH
        );

        contentPanel.add(
                createItemsPanel(),
                BorderLayout.CENTER
        );

        return contentPanel;
    }

    private JPanel createFilterPanel() {

        filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        filterPanel.setBackground(
                Color.WHITE
        );

        filterPanel.setPreferredSize(
                new Dimension(
                        0,
                        60
                )
        );

        JLabel lblFilter =
                new JLabel(
                        "Filter Items"
                );

        lblFilter.setFont(
                Fonts.Bold.deriveFont(
                        16f
                )
        );

        lblFilter.setForeground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        cmbFilter =
                new JComboBox<>();

        cmbFilter.addItem("All");
        cmbFilter.addItem("Lost");
        cmbFilter.addItem("Found");
        cmbFilter.addItem("Claimed");

        cmbFilter.setPreferredSize(
                new Dimension(
                        170,
                        35
                )
        );

        cmbFilter.setFont(
                Fonts.Medium.deriveFont(
                        14f
                )
        );

        cmbFilter.addActionListener(e -> {

            String selectedFilter =
                    (String)
                    cmbFilter.getSelectedItem();

            filterItems(
                    selectedFilter
            );
        });

        filterPanel.add(
                lblFilter
        );

        filterPanel.add(
                Box.createHorizontalStrut(
                        15
                )
        );

        filterPanel.add(
                cmbFilter
        );

        return filterPanel;
    }

    private JScrollPane createItemsPanel() {

        itemsPanel =
                new JPanel();

        itemsPanel.setLayout(
                new BoxLayout(
                        itemsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        itemsPanel.setBackground(
                new Color(240, 240, 240)
        );

        allReports =
                lostItemDAO.getAllItems();

        displayReports(
                allReports
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        itemsPanel
                );

        scrollPane.setBorder(null);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        12
                );

        return scrollPane;
    }

    private void displayReports(
            ArrayList<ReportCard> reports) {

        itemsPanel.removeAll();

        for (ReportCard report :
                reports) {

            addItemCard(
                    report.getItem_id(),
                    report.getItemName(),
                    report.getDateLost(),
                    report.getLocation(),
                    report.getStatus()
            );
        }

        itemsPanel.revalidate();

        itemsPanel.repaint();
    }

    private void filterItems(
            String selectedFilter) {

        ArrayList<ReportCard>
                filteredReports =
                new ArrayList<>();

        for (ReportCard report :
                allReports) {

            if (selectedFilter.equals(
                    "All")) {

                filteredReports.add(
                        report
                );

            } else if (
                    selectedFilter.equalsIgnoreCase(
                            "Lost"
                    )
                    && report.getItemType() != null
                    && report.getItemType()
                            .equalsIgnoreCase(
                                    "Lost"
                            )) {

                filteredReports.add(
                        report
                );

            } else if (
                    selectedFilter.equalsIgnoreCase(
                            "Found"
                    )
                    && report.getItemType() != null
                    && report.getItemType()
                            .equalsIgnoreCase(
                                    "Found"
                            )) {

                filteredReports.add(
                        report
                );

            } else if (
                    selectedFilter.equalsIgnoreCase(
                            "Claimed"
                    )
                    && report.getStatus() != null
                    && report.getStatus()
                            .equalsIgnoreCase(
                                    "Claimed"
                            )) {

                filteredReports.add(
                        report
                );
            }
        }

        displayReports(
                filteredReports
        );
    }

    private void addItemCard(
            int itemId,
            String itemName,
            String dateLost,
            String location,
            String status) {

        JPanel cardWrapper =
                new JPanel();

        cardWrapper.setLayout(
                new BoxLayout(
                        cardWrapper,
                        BoxLayout.Y_AXIS
                )
        );

        cardWrapper.setOpaque(false);

        cardWrapper.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        UIComponents.RoundedPanel card =
                new UIComponents.RoundedPanel(
                        15,
                        Color.WHITE
                );

        card.setLayout(
                new BorderLayout(
                        10,
                        10
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                Colors.BORDER_GRAY,
                                1
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        1000,
                        150
                )
        );

        card.setPreferredSize(
                new Dimension(
                        700,
                        150
                )
        );

        JPanel detailsPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                0,
                                5
                        )
                );

        detailsPanel.setOpaque(false);

        JLabel lblItem =
                new JLabel(
                        "Item Name : "
                        + itemName
                );

        lblItem.setFont(
                Fonts.Bold.deriveFont(
                        15f
                )
        );

        lblItem.setForeground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        JLabel lblDate =
                new JLabel(
                        "Date Lost : "
                        + dateLost
                );

        lblDate.setFont(
                Fonts.Medium.deriveFont(
                        14f
                )
        );

        JLabel lblLocation =
                new JLabel(
                        "Location : "
                        + location
                );

        lblLocation.setFont(
                Fonts.Medium.deriveFont(
                        14f
                )
        );

        JLabel lblStatus =
                new JLabel(
                        "Status : "
                        + status
                );

        lblStatus.setFont(
                Fonts.Medium.deriveFont(
                        14f
                )
        );

        if (status != null
                && status.equalsIgnoreCase(
                        "Pending")) {

            lblStatus.setForeground(
                    Colors.YELLOW_STATUS_COLOR
            );

        } else if (
                status != null
                && (
                        status.equalsIgnoreCase(
                                "Approved"
                        )
                        || status.equalsIgnoreCase(
                                "Found"
                        )
                )) {

            lblStatus.setForeground(
                    Colors.GREEN_STATUS_COLOR
            );

        } else if (
                status != null
                && status.equalsIgnoreCase(
                        "Rejected"
                )) {

            lblStatus.setForeground(
                    Colors.RED_STATUS_COLOR
            );

        } else {

            lblStatus.setForeground(
                    Colors.BLACK_TEXT_COLOR
            );
        }

        detailsPanel.add(
                lblItem
        );

        detailsPanel.add(
                lblDate
        );

        detailsPanel.add(
                lblLocation
        );

        detailsPanel.add(
                lblStatus
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setOpaque(false);

        JButton btnView =
                new JButton(
                        "View"
                );

        JButton btnDelete =
                new JButton(
                        "Delete"
                );

        btnView.setFont(
                Fonts.Bold.deriveFont(
                        13f
                )
        );

        btnDelete.setFont(
                Fonts.Bold.deriveFont(
                        13f
                )
        );

        btnView.setBackground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        btnDelete.setBackground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        btnView.setForeground(
                Color.WHITE
        );

        btnDelete.setForeground(
                Color.WHITE
        );

        btnView.setFocusPainted(false);

        btnDelete.setFocusPainted(false);

        btnView.setPreferredSize(
                new Dimension(
                        100,
                        30
                )
        );

        btnDelete.setPreferredSize(
                new Dimension(
                        100,
                        30
                )
        );

        /*
         * VIEW BUTTON
         */

        btnView.addActionListener(e -> {

            new ItemDetailsWindow(
                    currentUser,
                    itemId
            );

            dispose();
        });

        /*
         * DELETE BUTTON
         */

        btnDelete.addActionListener(e -> {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this post?",
                            "Delete Post",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (result !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            /*
             * DELETE FROM DATABASE
             */

            boolean deleted =
                    lostItemDAO.deleteItem(
                            itemId
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Post deleted successfully.",
                        "Delete Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                /*
                 * Reload the posts from
                 * the database.
                 */

                allReports =
                        lostItemDAO.getAllItems();

                String selectedFilter =
                        (String)
                        cmbFilter.getSelectedItem();

                filterItems(
                        selectedFilter
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "The post could not be deleted.",
                        "Delete Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(
                btnView
        );

        buttonPanel.add(
                btnDelete
        );

        card.add(
                detailsPanel,
                BorderLayout.CENTER
        );

        card.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        cardWrapper.add(
                card
        );

        cardWrapper.add(
                Box.createVerticalStrut(
                        15
                )
        );

        itemsPanel.add(
                cardWrapper
        );
    }
}