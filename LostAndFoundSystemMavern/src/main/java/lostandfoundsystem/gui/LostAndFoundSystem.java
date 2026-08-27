package lostandfoundsystem.gui;
// 230939023
// WE EXPECT A USER TO LOG_IN OR SIGN_UP
// THEREFORE WE USE THE LogInWindow AS OUR ENTRY POINT FOR THE APP
// LOST AND FOUND PROJECT SETUP COMPLETE
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.windows.LogInWindow;
public class LostAndFoundSystem {
    public static void main(String[] args) {
        // Entry point of the application
        LogInWindow logInGui = new LogInWindow();
        // FULLSCREEN
        logInGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInGui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        logInGui.setLocationRelativeTo(null);
        logInGui.setVisible(true);
        
       

        try {
            Connection connection = DBConnection.derbyConnection();
            System.out.println("Connected to database.");

            Statement st = connection.createStatement();

           

            String createTable = "CREATE TABLE ITEM (" +
                    "ITEM_ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "ITEM_NAME VARCHAR(100), " +
                    "CATEGORY VARCHAR(50), " +
                    "DESCRIPTION VARCHAR(500) ,"+
                   "STATUS VARCHAR(40))";
                    
            createTable = "CREATE TABLE REPORTS( " +
                    "REPORT_ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "+
                    "PERSON_ID INT, "+
                    "ITEM_ID INT, "+
                    "DATE_LOST VARCHAR(255), "+
                    "LOCATION VARCHAR(255), "+
                    "ITEM_IMAGE BLOB,"+
                    "ITEM_TYPE VARCHAR(255))";
            
            
                  createTable = "CREATE TABLE NOTIFICATION ("
                    + "NOTIFICATION_ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                    + "PERSON_ID INT, "
                    + "MESSAGE VARCHAR(500), "
                    + "STATUS VARCHAR(20), "
                    + "DATE_CREATED VARCHAR(20))";
                    
                    
                    
            st.executeUpdate(createTable);
            System.out.println("ITEM table created successfully.");

            st.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
    }
}
    
