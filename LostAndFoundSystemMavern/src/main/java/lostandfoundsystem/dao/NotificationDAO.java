
package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Notification;
import lostandfoundsystem.domain.User;

public class NotificationDAO {

    private Connection connection;

    public NotificationDAO() {

        try {
            connection = DBConnection.derbyConnection();

        } catch (SQLException er) {
            System.out.println("ERROR: " + er);
        }
    }

    public void addNotification(
            User currentUser,
            String message) {

        String sql =
                "INSERT INTO NOTIFICATION "
                + "(PERSON_ID, MESSAGE, STATUS, DATE_CREATED) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setInt( 1,currentUser.getPersonId());

            ps.setString( 2, message);

            ps.setString( 3, "unread");

            ps.setString( 4, java.time.LocalDate.now().toString());

            ps.executeUpdate();

        } catch (SQLException er) {

            System.out.println("ERROR adding notification: " + er);
        }
    }

    public ArrayList<Notification> getNotifications(
            User currentUser) {

        ArrayList<Notification> notifications =
                new ArrayList<>();

        String sql =
                "SELECT NOTIFICATION_ID, PERSON_ID, "
                + "MESSAGE, STATUS, DATE_CREATED "
                + "FROM NOTIFICATION "
                + "WHERE PERSON_ID = ? "
                + "ORDER BY NOTIFICATION_ID DESC";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setInt( 1,currentUser.getPersonId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Notification notification = new Notification();

                notification.setNotificationId(
                        rs.getInt("NOTIFICATION_ID"));

                notification.setPersonId(
                        rs.getInt("PERSON_ID"));

                notification.setMessage(
                        rs.getString("MESSAGE"));

                notification.setStatus(
                        rs.getString("STATUS"));

                notification.setDateCreated(
                        rs.getString("DATE_CREATED"));

                notifications.add(notification);
            }

        } catch (SQLException er) {

            System.out.println( "ERROR fetching notifications: " + er);
        }

        return notifications;
    }

    public boolean updateStatus(
            int notificationId) {

        String sql =
                "UPDATE NOTIFICATION "
                + "SET STATUS = ? "
                + "WHERE NOTIFICATION_ID = ?";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setString(1, "read");

            ps.setInt( 2,  notificationId);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException er) {

            System.out.println( "ERROR updating notification: " + er );

            return false;
        }
    }

    public boolean deleteNotification(
            int notificationId) {

        String sql =
                "DELETE FROM NOTIFICATION "
                + "WHERE NOTIFICATION_ID = ?";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setInt( 1,notificationId);

            int rowsDeleted = ps.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException er) {

            System.out.println( "ERROR deleting notification: " + er);

            return false;
        }
    }
}