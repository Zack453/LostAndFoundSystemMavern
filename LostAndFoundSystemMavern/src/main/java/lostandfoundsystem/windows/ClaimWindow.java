// 221106901

package lostandfoundsystem.windows;

import lostandfoundsystem.components.PageHeaderPanel;
import lostandfoundsystem.components.SideBarPanel;
import lostandfoundsystem.components.UIComponents;
import lostandfoundsystem.constants.Colors;
import lostandfoundsystem.constants.Fonts;
import lostandfoundsystem.dao.ClaimDAO;
import lostandfoundsystem.domain.Claim;
import lostandfoundsystem.domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ClaimWindow extends JFrame {

    private User currentUser;
    private JPanel claimsContainer;

    private ClaimDAO claimDAO;

    public ClaimWindow(User currentUser) {

        this.currentUser = currentUser;

        claimDAO =
                new ClaimDAO();

        super("Campus Finder - Claims");

        guiSetUp();
    }

    private void guiSetUp() {

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        getContentPane().setBackground(
                new Color(73, 107, 145)
        );

        setLayout(
                new BorderLayout(15, 15)
        );

        SideBarPanel sidebarPanel =
                new SideBarPanel(currentUser);

        JPanel center =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        center.setOpaque(false);

        PageHeaderPanel headerPanel =
                new PageHeaderPanel(
                        "CLAIMS",
                        currentUser
                );

        center.add(
                headerPanel,
                BorderLayout.NORTH
        );

        center.add(
                createContentPanel(),
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

        JLabel lblTitle =
                new JLabel(
                        "View All Claims"
                );

        lblTitle.setOpaque(true);

        lblTitle.setBackground(
                new Color(40, 90, 255)
        );

        lblTitle.setForeground(
                Color.WHITE
        );

        lblTitle.setBorder(
                new EmptyBorder(
                        8,
                        15,
                        8,
                        15
                )
        );

        lblTitle.setFont(
                Fonts.Bold.deriveFont(16f)
        );

        contentPanel.add(
                lblTitle,
                BorderLayout.NORTH
        );

        claimsContainer =
                new JPanel();

        claimsContainer.setLayout(
                new BoxLayout(
                        claimsContainer,
                        BoxLayout.Y_AXIS
                )
        );

        claimsContainer.setBackground(
                new Color(240, 240, 240)
        );

        loadClaims();

        JScrollPane scrollPane =
                new JScrollPane(
                        claimsContainer
                );

        scrollPane.setBorder(null);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);

        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return contentPanel;
    }

    private void loadClaims() {

        claimsContainer.removeAll();

        ArrayList<Claim> claims =
                claimDAO.getClaimsByPerson(
                        currentUser.getPersonId()
                );

        if (claims.isEmpty()) {

            JLabel emptyLabel =
                    new JLabel(
                            "You have no claims."
                    );

            emptyLabel.setFont(
                    Fonts.Bold.deriveFont(16f)
            );

            emptyLabel.setBorder(
                    new EmptyBorder(
                            30,
                            30,
                            30,
                            30
                    )
            );

            claimsContainer.add(
                    emptyLabel
            );

        } else {

            for (Claim claim : claims) {

                addClaimCard(claim);
            }
        }

        claimsContainer.revalidate();
        claimsContainer.repaint();
    }

    private void addClaimCard(
            Claim claim) {

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
                LEFT_ALIGNMENT
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
                        Integer.MAX_VALUE,
                        180
                )
        );

        card.setPreferredSize(
                new Dimension(
                        700,
                        180
                )
        );

        JPanel infoPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                0,
                                5
                        )
                );

        infoPanel.setOpaque(false);

        JLabel lblClaimId =
                new JLabel(
                        "Claim ID : "
                        + claim.getClaimId()
                );

        lblClaimId.setFont(
                Fonts.Bold.deriveFont(15f)
        );

        lblClaimId.setForeground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        JLabel lblItem =
                new JLabel(
                        "Item ID : "
                        + claim.getItemId()
                );

        lblItem.setFont(
                Fonts.Bold.deriveFont(15f)
        );

        JLabel lblDate =
                new JLabel(
                        "Date : "
                        + claim.getDate()
                );

        lblDate.setFont(
                Fonts.Medium.deriveFont(14f)
        );

        JLabel lblStatus =
                new JLabel(
                        "Status : "
                        + claim.getStatus()
                );

        lblStatus.setFont(
                Fonts.Medium.deriveFont(14f)
        );

        setStatusColour(
                lblStatus,
                claim.getStatus()
        );

        JLabel lblDetails =
                new JLabel(
                        "Claim Details : "
                        + getShortProof(
                                claim.getProof()
                        )
                );

        lblDetails.setFont(
                Fonts.Medium.deriveFont(13f)
        );

        infoPanel.add(
                lblClaimId
        );

        infoPanel.add(
                lblItem
        );

        infoPanel.add(
                lblDate
        );

        infoPanel.add(
                lblStatus
        );

        infoPanel.add(
                lblDetails
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setOpaque(false);

        JButton btnCancel =
                new JButton(
                        "Cancel"
                );

        JButton btnEdit =
                new JButton(
                        "Edit"
                );

        JButton btnView =
                new JButton(
                        "View"
                );

        styleButton(btnCancel);
        styleButton(btnEdit);
        styleButton(btnView);

        /*
         * CANCEL CLAIM
         */

        btnCancel.addActionListener(e -> {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to cancel this claim?",
                            "Cancel Claim",
                            JOptionPane.YES_NO_OPTION
                    );

            if (result ==
                    JOptionPane.YES_OPTION) {

                boolean deleted =
                        claimDAO.deleteClaim(
                                claim.getClaimId()
                        );

                if (deleted) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Claim cancelled successfully."
                    );

                    loadClaims();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Could not cancel claim.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        /*
         * EDIT CLAIM
         */

        btnEdit.addActionListener(e -> {

            new ClaimFormWindow(
                    currentUser,
                    claim
            );

            dispose();
        });

        /*
         * VIEW ITEM
         *
         * FALSE means the ItemDetailsWindow
         * is opened from the Claims window.
         *
         * Therefore the Claim button will NOT
         * be shown.
         */

        btnView.addActionListener(e -> {

            new ItemDetailsWindow(
                    currentUser,
                    claim.getItemId(),
                    false
            );

            dispose();
        });

        buttonPanel.add(
                btnCancel
        );

        buttonPanel.add(
                btnEdit
        );

        buttonPanel.add(
                btnView
        );

        card.add(
                infoPanel,
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
                Box.createVerticalStrut(15)
        );

        claimsContainer.add(
                cardWrapper
        );
    }

    private String getShortProof(
            String proof) {

        if (proof == null
                || proof.trim().isEmpty()) {

            return "None";
        }

        String clean =
                proof.replace(
                        "\n",
                        " "
                );

        if (clean.length() > 70) {

            return clean.substring(
                    0,
                    70
            ) + "...";
        }

        return clean;
    }

    private void styleButton(
            JButton button) {

        button.setFont(
                Fonts.Bold.deriveFont(13f)
        );

        button.setBackground(
                Colors.DARK_BLUE_TEXT_COLOR
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setPreferredSize(
                new Dimension(
                        100,
                        30
                )
        );
    }

    private void setStatusColour(
            JLabel label,
            String status) {

        if (status == null) {

            label.setForeground(
                    Colors.BLACK_TEXT_COLOR
            );

            return;
        }

        if (status.equalsIgnoreCase(
                "Pending")) {

            label.setForeground(
                    Colors.YELLOW_STATUS_COLOR
            );

        } else if (
                status.equalsIgnoreCase(
                        "Approved")) {

            label.setForeground(
                    Colors.GREEN_STATUS_COLOR
            );

        } else if (
                status.equalsIgnoreCase(
                        "Rejected")) {

            label.setForeground(
                    Colors.RED_STATUS_COLOR
            );

        } else {

            label.setForeground(
                    Colors.BLACK_TEXT_COLOR
            );
        }
    }
}