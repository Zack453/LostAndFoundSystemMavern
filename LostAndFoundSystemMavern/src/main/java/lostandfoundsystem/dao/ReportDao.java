package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Item;
import lostandfoundsystem.domain.Report;
import lostandfoundsystem.domain.ReportCard;
import lostandfoundsystem.domain.User;

public class ReportDao {

    public ReportDao() {
    }

    /*
     * SUBMIT LOST ITEM REPORT
     */
    public void submitReport(Item item, Report report, User currentUser) {
        String insert_details = "INSERT INTO ITEM (ITEM_NAME, CATEGORY, DESCRIPTION, STATUS) VALUES (?, ?, ?, ?)";
        String reportDetails = "INSERT INTO REPORTS (PERSON_ID, ITEM_ID, DATE_LOST, LOCATION, ITEM_IMAGE, ITEM_TYPE) VALUES (?, ?, ?, ?, ?, ?)";

        int currentUserId = currentUser.getPersonId();
        report.setPersonID(currentUserId);

        try (Connection connection = DBConnection.derbyConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insert_details, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, item.getItemName());
                ps.setString(2, item.getCategory());
                ps.setString(3, item.getDescription());
                ps.setString(4, item.getStatus());
                ps.executeUpdate();

                int generatedKey = 0;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        generatedKey = rs.getInt(1);
                        item.setItem_id(generatedKey);
                    }
                }

                try (PreparedStatement repStatement = connection.prepareStatement(reportDetails, Statement.RETURN_GENERATED_KEYS)) {
                    repStatement.setInt(1, currentUserId);
                    repStatement.setInt(2, generatedKey);
                    repStatement.setString(3, report.getDateLost());
                    repStatement.setString(4, report.getLocation());
                    repStatement.setBytes(5, report.getImageData());
                    repStatement.setString(6, report.getItemType());
                    repStatement.executeUpdate();

                    try (ResultSet result = repStatement.getGeneratedKeys()) {
                        if (result != null && result.next()) {
                            report.setReportID(result.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException er) {
            System.out.println("ERROR submitting report: " + er);
        }
    }

    /*
     * GET ALL LOST ITEMS
     */
    public ArrayList<ReportCard> getAllItems() {
        ArrayList<ReportCard> reports = new ArrayList<>();
        String sql = "SELECT i.ITEM_ID, i.ITEM_NAME, i.CATEGORY, i.DESCRIPTION, i.STATUS, "
                + "r.REPORT_ID, r.PERSON_ID, r.ITEM_ID, r.DATE_LOST, r.LOCATION, r.ITEM_TYPE, r.ITEM_IMAGE "
                + "FROM ITEM i JOIN REPORTS r ON i.ITEM_ID = r.ITEM_ID";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement pr = con.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {

            while (rs.next()) {
                ReportCard report = new ReportCard(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getBytes(12)
                );
                reports.add(report);
            }

        } catch (SQLException e) {
            System.out.println("ERROR fetching items: " + e);
        }

        return reports;
    }

    /*
     * GET LATEST 4 LOST ITEMS
     * Used by Dashboard.
     */
    public ArrayList<ReportCard> getLatestItems() {
        ArrayList<ReportCard> reports = new ArrayList<>();
        String sql = "SELECT i.ITEM_ID, i.ITEM_NAME, i.CATEGORY, i.DESCRIPTION, i.STATUS, "
                + "r.REPORT_ID, r.PERSON_ID, r.ITEM_ID, r.DATE_LOST, r.LOCATION, r.ITEM_TYPE, r.ITEM_IMAGE "
                + "FROM ITEM i JOIN REPORTS r ON i.ITEM_ID = r.ITEM_ID "
                + "ORDER BY r.DATE_LOST DESC "
                + "FETCH FIRST 4 ROWS ONLY";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReportCard report = new ReportCard(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getBytes(12)
                );
                reports.add(report);
            }

        } catch (SQLException e) {
            System.out.println("ERROR fetching latest items: " + e);
        }

        return reports;
    }

    /*
     * UPDATE ADMIN MESSAGE FOR A REPORT
     * Used by AdminReportWindow
     */
    public boolean updateAdminMessage(int reportId, String message) {
        String sql = "UPDATE REPORTS SET ADMIN_MESSAGE = ? WHERE REPORT_ID = ?";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, message);
            ps.setInt(2, reportId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR updating admin message: " + e);
            return false;
        }
    }
}