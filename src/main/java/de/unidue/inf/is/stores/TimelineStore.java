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
            System.out.println("There is a good conenction and username is " + username);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public List<Babble> createTimeline(){

        List<Babble> list = new ArrayList<>();
        String sqlstring = "SELECT * FROM \n" +
                "\t(SELECT b2.id, b2.text, b2.created, b2.creator FROM babble b2 WHERE b2.creator = ? \n" +
                "\tUNION ALL\n" +
                "\tSELECT b1.id, b1.text, b1.created, b1.creator FROM babble b1 JOIN follows f1 ON b1.creator = f1.followee\n" +
                "\tWHERE f1.follower = ? \n" +
                "\tUNION ALL\n" +
                "\tSELECT b1.id, b1.text, lb.created, b1.creator FROM babble b1 JOIN LikesBabble lb ON lb.babble = b1.id\n" +
                "\tWHERE lb.username = ?  \n" +
                "\tUNION ALL \n" +
                "\tSELECT b1.id, b1.text, rb.created, b1.creator FROM babble b1 JOIN ReBabble rb ON rb.babble = b1.id\n" +
                "\tWHERE rb.username = ? ) AS tbl\n" +
                "WHERE tbl.id  NOT IN (select b.id \n" +
                "from babble b join follows f on b.creator = f.followee \n" +
                "JOIN blocks bk ON f.followee = bk.blocker AND f.follower = bk.blockee\n" +
                "where f.follower = ?)";
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
                 //   System.out.println(" " + rs.getInt(1)+
                  //          rs.getString(2) + rs.getTimestamp(3) + rs.getString(4));
                    Babble b = new Babble();
                    b.setId(rs.getInt(1));
                    b.setText(rs.getString(2));
                    b.setCreated(rs.getTimestamp(3));
                    b.setCreator(rs.getString(4));
                    list.add(b);
                }
            }

            connection.commit();
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
