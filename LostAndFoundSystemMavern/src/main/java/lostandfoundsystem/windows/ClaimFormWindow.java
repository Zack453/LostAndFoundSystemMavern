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
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ClaimFormWindow extends JFrame {

    private User currentUser;
    private int itemId;

    /*
     * Used when editing an existing claim.
     */
    private Claim existingClaim;

    /*
     * Constructor for creating a NEW claim.
     */
    public ClaimFormWindow(
            User currentUser,
            int itemId) {

        this.currentUser = currentUser;
        this.itemId = itemId;
        this.existingClaim = null;

        super(
                "Campus Finder - Claim Form"
        );

        guiSetUp();
    }

    /*
     * Constructor for EDITING an existing claim.
     */
    public ClaimFormWindow(
            User currentUser,
            Claim claim) {

        this.currentUser = currentUser;
        this.existingClaim = claim;
        this.itemId = claim.getItemId();

        super(
                "Campus Finder - Edit Claim"
        );

        guiSetUp();
    }

    private void guiSetUp() {

        setTitle(
                existingClaim == null
                        ? "Campus Finder - Claim Form"
                        : "Campus Finder - Edit Claim"
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        getContentPane().setBackground(
                new Color(
                        73,
                        107,
                        145
                )
        );

        setLayout(
                new BorderLayout(
                        15,
                        15
                )
        );

        /*
         * SIDEBAR
         */

        SideBarPanel sidebarPanel =
                new SideBarPanel(
                        currentUser
                );

        /*
         * CENTER
         */

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        center.setOpaque(
                false
        );

        /*
         * HEADER
         */

        PageHeaderPanel headerPanel =
                new PageHeaderPanel(
                        existingClaim == null
                                ? "CLAIM FORM"
                                : "EDIT CLAIM",
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

        setVisible(
                true
        );
    }

    private JPanel createContentPanel() {

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBackground(
                new Color(
                        220,
                        220,
                        220
                )
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        /*
         * TITLE
         */

        JLabel lblTitle =
                new JLabel(
                        existingClaim == null
                                ? "Submit a Claim"
                                : "Edit Claim"
                );

        lblTitle.setOpaque(
                true
        );

        lblTitle.setBackground(
                new Color(
                        40,
                        90,
                        255
                )
        );

        lblTitle.setForeground(
                Color.WHITE
        );

        lblTitle.setFont(
                Fonts.Bold.deriveFont(
                        16f
                )
        );

        lblTitle.setBorder(
                new EmptyBorder(
                        8,
                        15,
                        8,
                        15
                )
        );

        contentPanel.add(
                lblTitle,
                BorderLayout.NORTH
        );

        /*
         * FORM AREA
         */

        JPanel formArea =
                new JPanel(
                        new GridBagLayout()
                );

        formArea.setBackground(
                new Color(
                        220,
                        220,
                        220
                )
        );

        /*
         * FORM CARD
         */

        UIComponents.RoundedPanel formCard =
                new UIComponents.RoundedPanel(
                        25,
                        Colors.MOCKUP_FORM_CARD_BG
                );

        formCard.setLayout(
                new BoxLayout(
                        formCard,
                        BoxLayout.Y_AXIS
                )
        );

        formCard.setBorder(
                new EmptyBorder(
                        30,
                        45,
                        30,
                        45
                )
        );

        formCard.setPreferredSize(
                new Dimension(
                        600,
                        520
                )
        );

        formCard.setMaximumSize(
                new Dimension(
                        650,
                        550
                )
        );

        /*
         * PROOF / DESCRIPTION
         */

        JLabel lblProof =
                new JLabel(
                        "Claim Details / Proof :"
                );

        lblProof.setFont(
                Fonts.Bold.deriveFont(
                        14f
                )
        );

        lblProof.setForeground(
                Colors.BLACK_TEXT_COLOR
        );

        lblProof.setAlignmentX(
                LEFT_ALIGNMENT
        );

        JTextArea txtProof =
                new JTextArea();

        txtProof.setFont(
                Fonts.Regular.deriveFont(
                        13f
                )
        );

        txtProof.setForeground(
                Colors.DASHBOARD_BACKGROUND_COLOR
        );

        txtProof.setLineWrap(
                true
        );

        txtProof.setWrapStyleWord(
                true
        );

        txtProof.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        /*
         * Existing claim proof.
         */

        if (existingClaim != null) {

            if (existingClaim.getProof() != null) {

                txtProof.setText(
                        existingClaim.getProof()
                );
            }

        } else {

            txtProof.setText(
                    "Enter details that prove this item belongs to you."
            );
        }

        UIComponents.RoundedPanel proofWrapper =
                new UIComponents.RoundedPanel(
                        15,
                        Colors.LOGIN_BACKGROUND_COLOR
                );

        proofWrapper.setLayout(
                new BorderLayout()
        );

        proofWrapper.add(
                txtProof,
                BorderLayout.CENTER
        );

        proofWrapper.setMaximumSize(
                new Dimension(
                        500,
                        100
                )
        );

        proofWrapper.setAlignmentX(
                LEFT_ALIGNMENT
        );

        formCard.add(
                lblProof
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        formCard.add(
                proofWrapper
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );

        /*
         * DATE
         */

        JLabel lblDate =
                new JLabel(
                        "Date :"
                );

        lblDate.setFont(
                Fonts.Bold.deriveFont(
                        14f
                )
        );

        lblDate.setForeground(
                Colors.BLACK_TEXT_COLOR
        );

        lblDate.setAlignmentX(
                LEFT_ALIGNMENT
        );

        UIComponents.RoundedTextField txtDate =
                new UIComponents.RoundedTextField(
                        20
                );

        txtDate.setMaximumSize(
                new Dimension(
                        500,
                        38
                )
        );

        txtDate.setAlignmentX(
                LEFT_ALIGNMENT
        );

        if (existingClaim != null) {

            txtDate.setText(
                    existingClaim.getDate()
            );
        }

        formCard.add(
                lblDate
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        formCard.add(
                txtDate
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                15
                        )
                )
        );

        /*
         * STATUS
         */

        JLabel lblStatus =
                new JLabel(
                        "Status :"
                );

        lblStatus.setFont(
                Fonts.Bold.deriveFont(
                        14f
                )
        );

        lblStatus.setForeground(
                Colors.BLACK_TEXT_COLOR
        );

        lblStatus.setAlignmentX(
                LEFT_ALIGNMENT
        );

        /*
         * STATUS OPTIONS
         */

        String[] statusOptions = {
            "Pending",
            "Approved",
            "Rejected"
        };

        JComboBox<String> cmbStatus =
                new JComboBox<>(
                        statusOptions
                );

        cmbStatus.setFont(
                Fonts.Regular.deriveFont(
                        13f
                )
        );

        cmbStatus.setMaximumSize(
                new Dimension(
                        500,
                        38
                )
        );

        cmbStatus.setAlignmentX(
                LEFT_ALIGNMENT
        );

        /*
         * NEW CLAIM
         *
         * Always starts as Pending.
         */

        if (existingClaim == null) {

            cmbStatus.setSelectedItem(
                    "Pending"
            );

        } else {

            /*
             * EDIT EXISTING CLAIM
             *
             * Load current status.
             */

            String currentStatus =
                    existingClaim.getStatus();

            if (currentStatus != null
                    && !currentStatus.trim().isEmpty()) {

                cmbStatus.setSelectedItem(
                        currentStatus
                );
            }
        }

        formCard.add(
                lblStatus
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                6
                        )
                )
        );

        formCard.add(
                cmbStatus
        );

        formCard.add(
                Box.createRigidArea(
                        new Dimension(
                                0,
                                25
                        )
                )
        );

        /*
         * BUTTONS
         */

        JPanel buttonsRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                0
                        )
                );

        buttonsRow.setOpaque(
                false
        );

        buttonsRow.setAlignmentX(
                LEFT_ALIGNMENT
        );

        /*
         * BACK
         */

        UIComponents.RoundedButton btnBack =
                new UIComponents.RoundedButton(
                        "Back",
                        Colors.BLACK_BUTTON_COLOR,
                        Colors.WHITE_TEXT_COLOR,
                        20
                );

        /*
         * SUBMIT / UPDATE
         */

        UIComponents.RoundedButton btnSubmit =
                new UIComponents.RoundedButton(
                        existingClaim == null
                                ? "Claim"
                                : "Update Claim",
                        Colors.ACCENT_BLUE_BUTTON,
                        Colors.WHITE_TEXT_COLOR,
                        20
                );

        btnBack.setPreferredSize(
                new Dimension(
                        125,
                        38
                )
        );

        btnSubmit.setPreferredSize(
                new Dimension(
                        existingClaim == null
                                ? 125
                                : 150,
                        38
                )
        );

        /*
         * BACK BUTTON ACTION
         */

        btnBack.addActionListener(
                e -> {

                    new ClaimWindow(
                            currentUser
                    );

                    dispose();
                }
        );

        /*
         * SUBMIT / UPDATE ACTION
         */

        btnSubmit.addActionListener(
                e -> {

                    /*
                     * Check proof.
                     */

                    if (txtProof.getText()
                            .trim()
                            .isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter your claim details / proof.",
                                "Incomplete Form",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    /*
                     * Check date.
                     */

                    if (txtDate.getText()
                            .trim()
                            .isEmpty()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter the date.",
                                "Incomplete Form",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    /*
                     * Get selected status.
                     */

                    String selectedStatus =
                            (String) cmbStatus
                                    .getSelectedItem();

                    if (selectedStatus == null) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please select a status.",
                                "Status Error",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    ClaimDAO claimDAO =
                            new ClaimDAO();

                    /*
                     * EDIT EXISTING CLAIM
                     */

                    if (existingClaim != null) {

                        existingClaim.setDate(
                                txtDate.getText()
                                        .trim()
                        );

                        existingClaim.setStatus(
                                selectedStatus
                        );

                        existingClaim.setProof(
                                txtProof.getText()
                                        .trim()
                        );

                        boolean updated =
                                claimDAO.updateClaim(
                                        existingClaim.getClaimId(),
                                        existingClaim.getItemId(),
                                        existingClaim.getDate(),
                                        existingClaim.getStatus(),
                                        existingClaim.getProof()
                                );

                        if (updated) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Claim updated successfully!\n"
                                    + "Item status is now: "
                                    + selectedStatus,
                                    "Claim Updated",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            new ClaimWindow(
                                    currentUser
                            );

                            dispose();

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "The claim could not be updated.",
                                    "Update Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }

                        return;
                    }

                    /*
                     * CREATE NEW CLAIM
                     */

                    Claim claim =
                            new Claim();

                    claim.setPersonId(
                            currentUser.getPersonId()
                    );

                    claim.setItemId(
                            itemId
                    );

                    /*
                     * New claims are ALWAYS Pending.
                     */

                    claim.setStatus(
                            "Pending"
                    );

                    claim.setDate(
                            txtDate.getText()
                                    .trim()
                    );

                    claim.setProof(
                            txtProof.getText()
                                    .trim()
                    );

                    boolean saved =
                            claimDAO.saveClaim(
                                    claim
                            );

                    if (saved) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Claim submitted successfully!\n"
                                + "Status: Pending",
                                "Claim Submitted",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        new ClaimWindow(
                                currentUser
                        );

                        dispose();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "The claim could not be saved.\n"
                                + "Please check your database connection.",
                                "Claim Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
        );

        buttonsRow.add(
                btnBack
        );

        buttonsRow.add(
                btnSubmit
        );

        formCard.add(
                buttonsRow
        );

        formArea.add(
                formCard
        );

        contentPanel.add(
                formArea,
                BorderLayout.CENTER
        );

        return contentPanel;
    }
}