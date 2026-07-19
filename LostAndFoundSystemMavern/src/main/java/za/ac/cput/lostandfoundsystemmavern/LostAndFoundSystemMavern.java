// 230939023
// WE EXPECT A USER TO LOG_IN OR SIGN_UP
// THEREFORE WE USE THE LogInWindow AS OUR ENTRY POINT FOR THE APP
// LOST AND FOUND PROJECT SETUP COMPLETE
package za.ac.cput.lostandfoundsystemmavern;
import javax.swing.JFrame;
import windows.LogInWindow;
public class LostAndFoundSystemMavern {
    public static void main(String[] args) {
        LogInWindow LogInGui = new LogInWindow();
        // FULLSCREEN             
        LogInGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // LogInGui.setSize(900, 600); 
        LogInGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        LogInGui.setLocationRelativeTo(null);
        LogInGui.setVisible(true);
    }
}