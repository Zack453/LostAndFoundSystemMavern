package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Item;

public class LostItemDAO {

    public LostItemDAO() {
    }

    /*
     * GET ONE ITEM BY ID
     * Used by ItemDetailsWindow
     */
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT ITEM_ID, ITEM_NAME, CATEGORY, DESCRIPTION, STATUS FROM ITEM WHERE ITEM_ID = ?";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new Item();
                    item.setItem_id(rs.getInt("ITEM_ID"));
                    item.setItemName(rs.getString("ITEM_NAME"));
                    item.setCategory(rs.getString("CATEGORY"));
                    item.setDescription(rs.getString("DESCRIPTION"));
                    item.setStatus(rs.getString("STATUS"));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR fetching item: " + e);
        }

        return item;
    }

    /*
     * UPDATE ITEM
     * Used by ItemDetailsWindow
     */
    public boolean updateItem(Item item) {
        String sql = "UPDATE ITEM SET ITEM_NAME = ?, CATEGORY = ?, DESCRIPTION = ?, STATUS = ? WHERE ITEM_ID = ?";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getStatus());
            ps.setInt(5, item.getItem_id());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR updating item: " + e);
            return false;
        }
    }

    /*
     * UPDATE ITEM STATUS
     * Used when claim status changes.
     */
    public boolean updateItemStatus(int itemId, String status) {
        String sql = "UPDATE ITEM SET STATUS = ? WHERE ITEM_ID = ?";

        try (Connection con = DBConnection.derbyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, itemId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("ERROR updating item status: " + e);
            return false;
        }
    }

    /*
     * DELETE ITEM / POST
     * Deletes claims, reports, and the item within a transaction.
     */
    public boolean deleteItem(int itemId) {
        String deleteClaims = "DELETE FROM CLAIM WHERE ITEM_ID = ?";
        String deleteReports = "DELETE FROM REPORTS WHERE ITEM_ID = ?";
        String deleteItem = "DELETE FROM ITEM WHERE ITEM_ID = ?";

        Connection con = null;

        try {
            con = DBConnection.derbyConnection();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(deleteClaims)) {
                ps.setInt(1, itemId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(deleteReports)) {
                ps.setInt(1, itemId);
                ps.executeUpdate();
            }

            int rowsDeleted;
            try (PreparedStatement ps = con.prepareStatement(deleteItem)) {
                ps.setInt(1, itemId);
                rowsDeleted = ps.executeUpdate();
            }

            if (rowsDeleted > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.out.println("ERROR deleting item: " + e);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    System.out.println("ERROR rolling back delete: " + rollbackError);
                }
            }
            return false;

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.out.println("ERROR closing connection: " + e);
                }
            }
        }
    }
}