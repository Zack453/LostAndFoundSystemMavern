<<<<<<< HEAD

package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import lostandfoundsystem.domain.ReportCard;
import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Item;
import lostandfoundsystem.domain.Report;
import lostandfoundsystem.domain.User;


public class LostItemDAO {
  
    private Connection connection;
    private PreparedStatement ps,repStatememt;
    
    
    public LostItemDAO(){
        try{
            connection = DBConnection.derbyConnection();
        }
        
        catch(SQLException er){
            System.out.println("ERROR: "+ er);
        }
    }
    
    
    public void submitReport(Item item, Report report, User currentUser){
        
        String insert_details = "insert into ITEM(ITEM_NAME, CATEGORY, DESCRIPTION, STATUS)"+
                "values(?,?,?,?)";
        
        String reportDetails = "insert into REPORTS(PERSON_ID, ITEM_ID, DATE_LOST,LOCATION, ITEM_IMAGE, ITEM_TYPE)"+
                "values(?,?,?,?,?,?)";
        
        int currentUserId = currentUser.getPersonId();
        report.setPersonID(currentUserId);
        
        try{
            
            ps = connection.prepareStatement(insert_details, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getStatus());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            int generatedKey =0;
            if(rs!= null){
                while(rs.next()){
                    generatedKey = rs.getInt(1);
                    item.setItem_id(generatedKey);
                }
                rs.close();
            }
            
            repStatememt = connection.prepareStatement(reportDetails, Statement.RETURN_GENERATED_KEYS);
            
            repStatememt.setInt(1, currentUserId);
            repStatememt.setInt(2, generatedKey);
            repStatememt.setString(3, report.getDateLost());
            repStatememt.setString(4, report.getLocation());
            repStatememt.setBytes(5, report.getImageData());
            repStatememt.setString(6, report.getItemType());
            repStatememt.executeUpdate();
            
            ResultSet result = repStatememt.getGeneratedKeys();
            
            if(result != null){
                while(result.next()){
                    int report_key = result.getInt(1);
                    report.setReportID(report_key);
                }
                result.close();
            }
            
            
           } 
        
        catch(SQLException er){
            System.out.println("ERROR: " + er);
        } 
            
            
       
        
        finally{
            try{
                if(ps!= null)
                ps.close();
            }
            
            catch(SQLException er){
            System.out.println("ERROR: " + er);
        }
            
        }
    }
    
    
    public ArrayList<ReportCard> getAllItems() {

    ArrayList<ReportCard> reports = new ArrayList<>();

    String sql = "SELECT i.ITEM_ID, i.ITEM_NAME, i.CATEGORY, i.DESCRIPTION, i.STATUS, "
            + "r.REPORT_ID, r.PERSON_ID, r.ITEM_ID, r.DATE_LOST, "
            + "r.LOCATION, r.ITEM_TYPE, r.ITEM_IMAGE "
            + "FROM ITEM i "
            + "JOIN REPORTS r ON i.ITEM_ID = r.ITEM_ID";

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
    
        
       } 
=======
package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Item;
import lostandfoundsystem.domain.Report;
import lostandfoundsystem.domain.ReportCard;
import lostandfoundsystem.domain.User;

public class LostItemDAO {

    private Connection connection;

    private PreparedStatement ps;
    private PreparedStatement repStatememt;

    public LostItemDAO() {

        try {

            connection =
                    DBConnection.derbyConnection();

        } catch (SQLException er) {

            System.out.println(
                    "ERROR: " + er
            );
        }
    }

    /*
     * SUBMIT LOST ITEM REPORT
     */

    public void submitReport(
            Item item,
            Report report,
            User currentUser) {

        String insert_details =
                "INSERT INTO ITEM "
                + "(ITEM_NAME, CATEGORY, DESCRIPTION, STATUS) "
                + "VALUES (?, ?, ?, ?)";

        String reportDetails =
                "INSERT INTO REPORTS "
                + "(PERSON_ID, ITEM_ID, DATE_LOST, LOCATION, ITEM_IMAGE, ITEM_TYPE) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        int currentUserId =
                currentUser.getPersonId();

        report.setPersonID(
                currentUserId
        );

        try {

            ps =
                    connection.prepareStatement(
                            insert_details,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(
                    1,
                    item.getItemName()
            );

            ps.setString(
                    2,
                    item.getCategory()
            );

            ps.setString(
                    3,
                    item.getDescription()
            );

            ps.setString(
                    4,
                    item.getStatus()
            );

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            int generatedKey = 0;

            if (rs != null) {

                while (rs.next()) {

                    generatedKey =
                            rs.getInt(1);

                    item.setItem_id(
                            generatedKey
                    );
                }

                rs.close();
            }

            repStatememt =
                    connection.prepareStatement(
                            reportDetails,
                            Statement.RETURN_GENERATED_KEYS
                    );

            repStatememt.setInt(
                    1,
                    currentUserId
            );

            repStatememt.setInt(
                    2,
                    generatedKey
            );

            repStatememt.setString(
                    3,
                    report.getDateLost()
            );

            repStatememt.setString(
                    4,
                    report.getLocation()
            );

            repStatememt.setBytes(
                    5,
                    report.getImageData()
            );

            repStatememt.setString(
                    6,
                    report.getItemType()
            );

            repStatememt.executeUpdate();

            ResultSet result =
                    repStatememt.getGeneratedKeys();

            if (result != null) {

                while (result.next()) {

                    int report_key =
                            result.getInt(1);

                    report.setReportID(
                            report_key
                    );
                }

                result.close();
            }

        } catch (SQLException er) {

            System.out.println(
                    "ERROR: " + er
            );

        } finally {

            try {

                if (ps != null) {

                    ps.close();
                }

            } catch (SQLException er) {

                System.out.println(
                        "ERROR: " + er
                );
            }
        }
    }

    /*
     * GET ALL LOST ITEMS
     */

    public ArrayList<ReportCard> getAllItems() {

        ArrayList<ReportCard> reports =
                new ArrayList<>();

        String sql =
                "SELECT i.ITEM_ID, "
                + "i.ITEM_NAME, "
                + "i.CATEGORY, "
                + "i.DESCRIPTION, "
                + "i.STATUS, "
                + "r.REPORT_ID, "
                + "r.PERSON_ID, "
                + "r.ITEM_ID, "
                + "r.DATE_LOST, "
                + "r.LOCATION, "
                + "r.ITEM_TYPE, "
                + "r.ITEM_IMAGE "
                + "FROM ITEM i "
                + "JOIN REPORTS r "
                + "ON i.ITEM_ID = r.ITEM_ID";

        try (
                Connection con =
                        DBConnection.derbyConnection();

                PreparedStatement pr =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pr.executeQuery()
        ) {

            while (rs.next()) {

                ReportCard report =
                        new ReportCard(
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

                reports.add(
                        report
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR fetching items: "
                    + e
            );
        }

        return reports;
    }

    /*
     * GET ONE ITEM BY ID
     *
     * Used by ItemDetailsWindow
     */

    public Item getItemById(
            int itemId) {

        Item item = null;

        String sql =
                "SELECT ITEM_ID, "
                + "ITEM_NAME, "
                + "CATEGORY, "
                + "DESCRIPTION, "
                + "STATUS "
                + "FROM ITEM "
                + "WHERE ITEM_ID = ?";

        try (
                Connection con =
                        DBConnection.derbyConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    itemId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                item =
                        new Item();

                item.setItem_id(
                        rs.getInt(
                                "ITEM_ID"
                        )
                );

                item.setItemName(
                        rs.getString(
                                "ITEM_NAME"
                        )
                );

                item.setCategory(
                        rs.getString(
                                "CATEGORY"
                        )
                );

                item.setDescription(
                        rs.getString(
                                "DESCRIPTION"
                        )
                );

                item.setStatus(
                        rs.getString(
                                "STATUS"
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR fetching item: "
                    + e
            );
        }

        return item;
    }

    /*
     * UPDATE ITEM
     *
     * Used by ItemDetailsWindow
     */

    public boolean updateItem(
            Item item) {

        String sql =
                "UPDATE ITEM "
                + "SET ITEM_NAME = ?, "
                + "CATEGORY = ?, "
                + "DESCRIPTION = ?, "
                + "STATUS = ? "
                + "WHERE ITEM_ID = ?";

        try (
                Connection con =
                        DBConnection.derbyConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    item.getItemName()
            );

            ps.setString(
                    2,
                    item.getCategory()
            );

            ps.setString(
                    3,
                    item.getDescription()
            );

            ps.setString(
                    4,
                    item.getStatus()
            );

            ps.setInt(
                    5,
                    item.getItem_id()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "ERROR updating item: "
                    + e
            );

            return false;
        }
    }

    /*
     * UPDATE ITEM STATUS
     *
     * Used when claim status changes.
     */

    public boolean updateItemStatus(
            int itemId,
            String status) {

        String sql =
                "UPDATE ITEM "
                + "SET STATUS = ? "
                + "WHERE ITEM_ID = ?";

        try (
                Connection con =
                        DBConnection.derbyConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    status
            );

            ps.setInt(
                    2,
                    itemId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "ERROR updating item status: "
                    + e
            );

            return false;
        }
    }

    /*
     * DELETE ITEM / POST
     *
     * Deletes:
     *
     * 1. Claims connected to the item
     * 2. Report connected to the item
     * 3. The item itself
     *
     * This is done in a transaction so that
     * everything succeeds together.
     */

    public boolean deleteItem(
            int itemId) {

        String deleteClaims =
                "DELETE FROM CLAIM "
                + "WHERE ITEM_ID = ?";

        String deleteReports =
                "DELETE FROM REPORTS "
                + "WHERE ITEM_ID = ?";

        String deleteItem =
                "DELETE FROM ITEM "
                + "WHERE ITEM_ID = ?";

        Connection con = null;

        try {

            con =
                    DBConnection.derbyConnection();

            /*
             * Start transaction
             */

            con.setAutoCommit(false);

            /*
             * DELETE CLAIMS
             */

            try (PreparedStatement ps =
                    con.prepareStatement(
                            deleteClaims)) {

                ps.setInt(
                        1,
                        itemId
                );

                ps.executeUpdate();
            }

            /*
             * DELETE REPORT
             */

            try (PreparedStatement ps =
                    con.prepareStatement(
                            deleteReports)) {

                ps.setInt(
                        1,
                        itemId
                );

                ps.executeUpdate();
            }

            /*
             * DELETE ITEM
             */

            int rowsDeleted;

            try (PreparedStatement ps =
                    con.prepareStatement(
                            deleteItem)) {

                ps.setInt(
                        1,
                        itemId
                );

                rowsDeleted =
                        ps.executeUpdate();
            }

            /*
             * If item was successfully deleted,
             * commit everything.
             */

            if (rowsDeleted > 0) {

                con.commit();

                return true;

            } else {

                /*
                 * Item did not exist.
                 * Undo the previous deletes.
                 */

                con.rollback();

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR deleting item: "
                    + e
            );

            /*
             * Undo changes if something failed.
             */

            if (con != null) {

                try {

                    con.rollback();

                } catch (SQLException rollbackError) {

                    System.out.println(
                            "ERROR rolling back delete: "
                            + rollbackError
                    );
                }
            }

            return false;

        } finally {

            if (con != null) {

                try {

                    con.setAutoCommit(true);
                    con.close();

                } catch (SQLException e) {

                    System.out.println(
                            "ERROR closing connection: "
                            + e
                    );
                }
            }
        }
    }
}
>>>>>>> origin/main
