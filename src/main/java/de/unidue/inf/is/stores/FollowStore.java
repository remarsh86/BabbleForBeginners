package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.Follows;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowStore implements Closeable {
    private Connection connection;
    private boolean complete;


    public FollowStore() throws StoreException {
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public void addFollows(Follows follows){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO dbp72.follows (follower, followee) VALUES (?, ?)");
            preparedStatement.setString(1, follows.getFollower());
            preparedStatement.setString(2, follows.getFolowee());
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }

    }

    public boolean doTheyFollow(String follower, String followee){
        boolean booleanfollow = false;
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.follows  WHERE follower = ? " +
                            " AND followee = ?  ");
            preparedStatement.setString(1, follower);
            preparedStatement.setString(2, followee);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else{
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(2));
                    booleanfollow = true;
                }
            }

        }
        catch (SQLException e) {
            throw new StoreException(e);
        }

    return booleanfollow;

    }

    public void deleteFromFollow(String follower, String followee){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE from dbp72.follows  WHERE follower = ? " +
                            "AND followee = ?");
            preparedStatement.setString(1, follower);
            preparedStatement.setString(2, followee);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }

    public void printFollowTable(){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.follows  ");
            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else{
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    System.out.println(rs.getString(2));

                }
            }

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
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }
}
