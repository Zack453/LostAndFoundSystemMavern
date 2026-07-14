package windows;

import constants.Fonts;
import constants.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignUpWindow extends JFrame implements ActionListener, ItemListener {

    private JLabel title;
    private JPanel mainPanel;

    public SignUpWindow() {
        super("Sign Up Window");

        mainPanel = new JPanel();

        title = new JLabel("SIGN IN WINDOW");
        title.setFont(Fonts.Bold.deriveFont(36f));

        guiSetUp();
    }

    public void guiSetUp() {

        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(title);

        add(mainPanel);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
}