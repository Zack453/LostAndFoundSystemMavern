//  230939023
package components;
import java.awt.Font;
import javax.swing.JTextField;
public class TextField extends JTextField{
    public TextField() {
        setFont(new Font("Arial",Font.PLAIN,16));
        setColumns(20);
    }
}
