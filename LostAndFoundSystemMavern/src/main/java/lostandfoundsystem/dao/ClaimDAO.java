package lostandfoundsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lostandfoundsystem.connection.DBConnection;
import lostandfoundsystem.domain.Claim;

public class ClaimDAO {

    private Connection connection;

    public ClaimDAO() {

        try {

            connection =
                    DBConnection.derbyConnection();

        } catch (SQLException e) {

            System.out.println(
                    "ERROR connecting to database: " + e
            );
        }
    }

    /*
     * SAVE NEW CLAIM
     */

    public boolean saveClaim(
            Claim claim) {

        String sql =
                "INSERT INTO CLAIM "
                + "(PERSON_ID, ITEM_ID, DATE, STATUS, PROOF) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    claim.getPersonId()
            );

            ps.setInt(
                    2,
                    claim.getItemId()
            );

            ps.setString(
                    3,
                    claim.getDate()
            );

            ps.setString(
                    4,
                    claim.getStatus()
            );

            ps.setString(
                    5,
                    claim.getProof()
            );

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println(
                    "ERROR saving claim: " + e
            );

            return false;
        }
    }

    /*
     * GET CLAIMS FOR CURRENT USER
     */

    public ArrayList<Claim> getClaimsByPerson(
            int personId) {

        ArrayList<Claim> claims =
                new ArrayList<>();

        String sql =
                "SELECT CLAIM_ID, "
                + "PERSON_ID, "
                + "ITEM_ID, "
                + "DATE, "
                + "STATUS, "
                + "PROOF "
                + "FROM CLAIM "
                + "WHERE PERSON_ID = ?";

        try (
                PreparedStatement ps =
                        connection.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    personId
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Claim claim =
                        new Claim();

                claim.setClaimId(
                        rs.getInt(
                                "CLAIM_ID"
                        )
                );

                claim.setPersonId(
                        rs.getInt(
                                "PERSON_ID"
                        )
                );

                claim.setItemId(
                        rs.getInt(
                                "ITEM_ID"
                        )
                );

                claim.setDate(
                        rs.getString(
                                "DATE"
                        )
                );

                claim.setStatus(
                        rs.getString(
                                "STATUS"
                        )
                );

                claim.setProof(
                        rs.getString(
                                "PROOF"
                        )
                );

                claims.add(
                        claim
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "ERROR loading claims: " + e
            );
        }

        return claims;
    }

    /*
     * UPDATE CLAIM
     *
     * This updates BOTH:
     *
     * CLAIM.STATUS
     *
     * and
     *
     * ITEM.STATUS
     *
     * so that the status is the same
     * everywhere in the application.
     */

    public boolean updateClaim(
            int claimId,
            int itemId,
            String date,
            String status,
            String proof) {

        String claimSQL =
                "UPDATE CLAIM "
                + "SET DATE = ?, "
                + "STATUS = ?, "
                + "PROOF = ? "
                + "WHERE CLAIM_ID = ?";

        String itemSQL =
                "UPDATE ITEM "
                + "SET STATUS = ? "
                + "WHERE ITEM_ID = ?";

        try {

            /*
             * Turn off automatic commit so
             * both updates happen together.
             */

            connection.setAutoCommit(
                    false
            );

            /*
             * UPDATE CLAIM
             */

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    claimSQL
                            )
            ) {

                ps.setString(
                        1,
                        date
                );

                ps.setString(
                        2,
                        status
                );

                ps.setString(
                        3,
                        proof
                );

                ps.setInt(
                        4,
                        claimId
                );

                ps.executeUpdate();
            }

            /*
             * UPDATE ITEM
             */

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    itemSQL
                            )
            ) {

                ps.setString(
                        1,
                        status
                );

                ps.setInt(
                        2,
                        itemId
                );

                ps.executeUpdate();
            }

            /*
             * Save both changes.
             */

            connection.commit();

            connection.setAutoCommit(
                    true
            );

            return true;

        } catch (SQLException e) {

            /*
             * If something goes wrong,
             * undo both changes.
             */

            try {

                connection.rollback();

            } catch (SQLException rollbackError) {

                System.out.println(
                        "ERROR rolling back: "
                        + rollbackError
                );
            }

            try {

                connection.setAutoCommit(
                        true
                );

            } catch (SQLException autoCommitError) {

                System.out.println(
                        "ERROR resetting connection: "
                        + autoCommitError
                );
            }

            System.out.println(
                    "ERROR updating claim and item: "
                    + e
            );

            return false;
        }
    }

    /*
     * DELETE / CANCEL CLAIM
     */

    public boolean deleteClaim(
            int claimId) {

        String sql =
                "DELETE FROM CLAIM "
                + "WHERE CLAIM_ID = ?";

        try (
                PreparedStatement ps =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            ps.setInt(
                    1,
                    claimId
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "ERROR deleting claim: "
                    + e
            );

            return false;
        }
    }
}