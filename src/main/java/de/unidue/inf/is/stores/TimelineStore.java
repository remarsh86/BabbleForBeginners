package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.utils.DBUtil;


import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimelineStore implements Closeable {

    private Connection connection;
    private boolean complete;
     private String username;


    public TimelineStore(String username) throws StoreException {
        this.username = username;
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public List<Babble> createTimeline(){

        List<Babble> list = new ArrayList<>();
        String sqlstring = "SELECT * FROM (SELECT b2.id, b2.text, b2.created, b2.creator " +
                "FROM dbp72.babble b2 WHERE b2.creator = ? " +
                "UNION ALL SELECT b1.id, b1.text, b1.created, b1.creator " +
                "FROM dbp72.babble b1 JOIN dbp72.follows f1 ON b1.creator = f1.followee " +
                "WHERE f1.follower = ? " +
                "UNION ALL SELECT b1.id, b1.text, lb.created, b1.creator " +
                "FROM dbp72.babble b1 JOIN dbp72.LikesBabble lb ON lb.babble = b1.id " +
                "WHERE lb.username = ? UNION ALL SELECT b1.id, b1.text, rb.created, b1.creator " +
                "FROM dbp72.babble b1 JOIN dbp72.ReBabble rb ON rb.babble = b1.id WHERE rb.username = ? ) AS tbl " +
                "WHERE tbl.id NOT IN (select b.id from dbp72.babble b " +
                "join dbp72.follows f on b.creator = f.followee JOIN dbp72.blocks bk " +
                "ON f.followee = bk.blocker AND f.follower = bk.blockee where f.follower = ?)" +
                " " +
                " ORDER BY tbl.created ASC ";
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, username );
            preparedStatement.setString(2, username );
            preparedStatement.setString(3, username );
            preparedStatement.setString(4, username );
            preparedStatement.setString(5, username );
            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    Babble b = new Babble();
                    b.setId(rs.getInt(1));
                    b.setText(rs.getString(2));
                    b.setCreated(rs.getTimestamp(3));
                    b.setCreator(rs.getString(4));
                    list.add(b);
                }
            }

            return list;

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }

    public List<Babble> createTimeline2(){

        List<Babble> list = new ArrayList<>();
        String sqlstring = "  SELECT  * FROM " +
                " (SELECT b2.id, b2.text, b2.created, b2.creator FROM dbp72.babble b2 WHERE b2.creator = ? " +
                " UNION ALL " +
                " SELECT b1.id, b1.text, b1.created, b1.creator FROM dbp72.babble b1 JOIN dbp72.follows f1 ON b1.creator = f1.followee" +
                " WHERE f1.follower = ? " +
                " UNION ALL " +
                " SELECT b1.id, b1.text, lb.created, b1.creator FROM dbp72.babble b1 JOIN dbp72.LikesBabble lb ON lb.babble = b1.id " +
                " WHERE lb.username = ?  " +
                " UNION ALL " +
                "  SELECT b1.id, b1.text, rb.created, b1.creator FROM dbp72.babble b1 JOIN dbp72.ReBabble rb ON rb.babble = b1.id " +
                " WHERE rb.username = ? ) AS tbl" +
                "  WHERE tbl.id  NOT IN (select b.id  " +
                "  from dbp72.babble b join dbp72.follows f on b.creator = f.followee  " +
                "  JOIN dbp72.blocks bk ON  (f.followee = bk.blocker AND f.follower = bk.blockee) " +
                "  where f.follower = ? ) " +
                "  AND tbl.id NOT IN (select b.id " +
                "  from dbp72.babble b JOIN dbp72.likesbabble Likes ON b.id = likes.babble " +
                "  JOIN dbp72.blocks block ON (b.creator = block.blocker AND likes.username = block.blockee) " +
                "  WHERE likes.username = ? ) " +
                "  AND tbl.id NOT IN ( " +
                "  select b.id from dbp72.babble b JOIN dbp72.rebabble reb ON b.id = reb.babble  " +
                "  JOIN dbp72.blocks blk ON (b.creator = blk.blocker AND reb.username = blk.blockee) " +
                "  WHERE reb.username= ?) " +
                "  ORDER BY tbl.created ASC";
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, username );
            preparedStatement.setString(2, username );
            preparedStatement.setString(3, username );
            preparedStatement.setString(4, username );
            preparedStatement.setString(5, username );
            preparedStatement.setString(6, username );
            preparedStatement.setString(7, username );
            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    Babble b = new Babble();
                    b.setId(rs.getInt(1));
                    b.setText(rs.getString(2));
                    b.setCreated(rs.getTimestamp(3));
                    b.setCreator(rs.getString(4));
                    list.add(b);
                }
            }

            return list;

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }



    public void complete() {
        complete = true;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } catch (SQLException e) {
                throw new StoreException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }

    }
}
