//250055392
package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReportLostItemWindow extends JFrame{

    private JPanel northPanel;
    private JLabel title;

    public ReportLostItemWindow() {

        title = new JLabel("Report Lost Item");
        northPanel = new JPanel();

        guiSetUp();
    }

    private void guiSetUp() {

        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(title);

        setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
