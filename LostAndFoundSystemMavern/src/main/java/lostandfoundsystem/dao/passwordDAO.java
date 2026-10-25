package lostandfoundsystem.dao;

// 240822757

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import lostandfoundsystem.connection.DBConnection;

public class passwordDAO {
    
    private Connection con;
    private PreparedStatement pstmt;
    
    public passwordDAO() {
        
        try {
            con = DBConnection.derbyConnection();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(
                    Level.SEVERE,
                    null,
                    ex
            );
        }
    }
    
public boolean updatePassword(Connection con,int personId,String securityQuestion,String securityAnswer,String newPassword) {

    String[] tables = {"STUDENT", "STAFF", "LECTURER", "ADMIN"};

    try {

        for (String table : tables) {

            String findUser = "SELECT sec_question, sec_answer "
                    + "FROM " + table
                    + " WHERE person_id = ?";

            try (PreparedStatement findStmt = con.prepareStatement(findUser)) {

                findStmt.setInt(1, personId);

                ResultSet rs = findStmt.executeQuery();

                if (rs.next()) {

                    String storedQuestion = rs.getString("sec_question");
                    String storedAnswer = rs.getString("sec_answer");

                    if (storedQuestion.equals(securityQuestion)
                            && storedAnswer.equalsIgnoreCase(securityAnswer)) {

                        String updateSql = "UPDATE " + table
                                + " SET password = ? WHERE person_id = ?";

                        try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

                            updateStmt.setString(1, newPassword);
                            updateStmt.setInt(2, personId);

                            return updateStmt.executeUpdate() > 0;
                        }
                    }

                    // User exists in this role table but security details are incorrect.
                    return false;
                }

                rs.close();
            }
        }

    } catch (SQLException e) {

        System.err.println("Database error while updating password: " + e.getMessage());
        e.printStackTrace();
    }

    return false;
}  
    
    private void closeStatement() {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    ex.getMessage()
            );
        }
    }
    
}