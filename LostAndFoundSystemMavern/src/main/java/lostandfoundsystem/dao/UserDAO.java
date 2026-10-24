package lostandfoundsystem.dao;

// 230939023

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Admin;
import lostandfoundsystem.domain.Lecturer;
import lostandfoundsystem.domain.Staff;
import lostandfoundsystem.domain.Student;
import lostandfoundsystem.domain.User;

public class UserDAO {

    private Connection con;
    private PreparedStatement pstmt;

    public UserDAO() {
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

public boolean saveStudent(Student student) {
        String sql = "INSERT INTO STUDENT "
                + "(person_id, name, surname, password, sec_question, sec_answer, role, student_number) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // Fixed: 8 question marks for 8 columns

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, student.getPersonId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getSurname());
            pstmt.setString(4, student.getPassword());
            pstmt.setString(5, student.getSecQuestion());
            pstmt.setString(6, student.getSecAnswer());
            pstmt.setString(7, student.getRole());
            pstmt.setString(8, student.getStudentNumber());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            return false;
        } finally {
            closeStatement();
        }
    }

    public boolean saveStaff(Staff staff) {
        String sql = "INSERT INTO STAFF "
                + "(person_id, name, surname, password, sec_question, sec_answer, role, employee_id, department) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Fixed: 9 question marks

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, staff.getPersonId());
            pstmt.setString(2, staff.getName());
            pstmt.setString(3, staff.getSurname());
            pstmt.setString(4, staff.getPassword());
            pstmt.setString(5, staff.getSecQuestion());
            pstmt.setString(6, staff.getSecAnswer());
            pstmt.setString(7, staff.getRole());
            pstmt.setString(8, staff.getEmployeeId()); // Fixed: Index 8
            pstmt.setString(9, staff.getDepartment()); // Fixed: Index 9
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            return false;
        } finally {
            closeStatement();
        }
    }

    public boolean saveLecturer(Lecturer lecturer) {
        String sql = "INSERT INTO LECTURER "
                + "(person_id, name, surname, password, sec_question, sec_answer, role, employee_id, department) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Fixed: 9 question marks

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, lecturer.getPersonId());
            pstmt.setString(2, lecturer.getName());
            pstmt.setString(3, lecturer.getSurname());
            pstmt.setString(4, lecturer.getPassword());
            pstmt.setString(5, lecturer.getSecQuestion());
            pstmt.setString(6, lecturer.getSecAnswer());
            pstmt.setString(7, lecturer.getRole());
            pstmt.setString(8, lecturer.getEmployeeId());
            pstmt.setString(9, lecturer.getDepartment());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            return false;
        } finally {
            closeStatement();
        }
    }

    public boolean saveAdmin(Admin admin) {
        String sql = "INSERT INTO ADMIN "
                + "(person_id, name, surname, password, sec_question, sec_answer, role, employee_id, department, access_level) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Fixed: 10 question marks

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, admin.getPersonId());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getSurname());
            pstmt.setString(4, admin.getPassword());
            pstmt.setString(5, admin.getSecQuestion());
            pstmt.setString(6, admin.getSecAnswer());
            pstmt.setString(7, admin.getRole());
            pstmt.setString(8, admin.getEmployeeId());
            pstmt.setString(9, admin.getDepartment());
            pstmt.setInt(10, admin.getAccessLevel());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
            return false;
        } finally {
            closeStatement();
        }
    }

    public User login(int personId, String password, String userType) {

        String tableName = userType.toUpperCase();
        String sql = "SELECT * FROM " + tableName
                + " WHERE person_id = ? AND password = ?";

        try {

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, personId);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                if (userType.equalsIgnoreCase("Student")) {

                    return new Student(
                            rs.getInt("person_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("password"),
                            rs.getString("sec_question"),
                            rs.getString("sec_answer"),
                            rs.getString("student_number"),
                            rs.getString("role")
                    );
                }

                if (userType.equalsIgnoreCase("Lecturer")) {

                    return new Lecturer(
                            rs.getInt("person_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("password"),
                            rs.getString("sec_question"),
                            rs.getString("sec_answer"),
                            rs.getString("role"),
                            rs.getString("employee_id"),
                            rs.getString("department")
                    );
                }

                if (userType.equalsIgnoreCase("Staff")) {

                    return new Staff(
                            rs.getInt("person_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("password"),
                            rs.getString("sec_question"),
                            rs.getString("sec_answer"),
                            rs.getString("role"),
                            rs.getString("employee_id"),
                            rs.getString("department")
                    );
                }

                if (userType.equalsIgnoreCase("Admin")) {

                    return new Admin(
                            rs.getInt("person_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("password"),
                            rs.getString("sec_question"),
                            rs.getString("sec_answer"),
                            rs.getString("role"),
                            rs.getString("employee_id"),
                            rs.getString("department"),
                            rs.getInt("access_level")
                    );
                }

            }

            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "SQL Error: " + ex.getMessage()
            );

        } finally {
            closeStatement();
        }
        return null;
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