package de.unidue.inf.is.stores;


import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class BabbleStore implements Closeable {

    private Connection connection;
    private boolean complete;
    private String username;


    public BabbleStore() throws StoreException {
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }



    public void test(){
        Statement stmt= null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Bad connection.");
        }
        ResultSet rs= null;
        try {
            rs = stmt.executeQuery("select * from dbp72.babble");
        } catch (SQLException e) {
            System.out.println("Problem with execute");
        }
        try {
            if(rs == null) System.out.println("No result set.");
            else{
                while(rs.next())
                    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("Problem reading data");
        }

    }

    public boolean addBabble(Babble babble) throws StoreException {


        System.out.println("try prepared statement");
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO dbp72.babble (text, creator) VALUES (?, ?)");
            preparedStatement.setString(1, babble.getText());
            preparedStatement.setString(2, babble.getCreator());
            preparedStatement.executeUpdate();
            connection.commit();

            return true;
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
