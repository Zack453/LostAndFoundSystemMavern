// 230939023
package components;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
public class Buttom {
    public class CustomButton extends JButton {
        public CustomButton(String text) {
            super(text);
            setBackground(new Color(33, 150, 243));
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 16));
            setFocusPainted(false);
        }
    }
}
