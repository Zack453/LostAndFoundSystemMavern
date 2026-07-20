// 23093023

package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//ADMINS COMPONENTS NEED TO BE CONDITIONALLY LOADED
// Example
// if (isAdmin) {
// JButton btnDelete = new JButton("Delete");
//     panel.add(btnDelete);
// }

public class Dashboard extends JFrame{

    private JPanel northPanel;
    private JLabel title;

    public Dashboard() {

        title = new JLabel("DashBoard");
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
