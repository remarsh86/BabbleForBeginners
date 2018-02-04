package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class BabbleUserStore implements Closeable{
    private Connection connection;
    private boolean complete;
    private String username;



    public BabbleUserStore() throws StoreException {
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public BabbleUserStore(String user) throws StoreException {
        this.username =  user;
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
            System.out.println("Good connection to db (BabbleUserStore)");
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
            rs = stmt.executeQuery("select * from dbp72.babbleuser");
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


    public BabbleUser getBabbleUser(){
        System.out.println("Retrieving BabbleUser in BabbleUserStore");
        BabbleUser user= null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.babbleuser WHERE username = ?");
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    user = new BabbleUser();
                    user.setUsername(rs.getString(1));
                    user.setName(rs.getString(2));
                    user.setStatus(rs.getString(3));
                }
                System.out.println("BabbleUserStore says username is " + user.getUsername());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean addBabbleUser(BabbleUser userToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into babbleuser (username, name, status, profilbild) values (?, ?, ?, ?)");
            preparedStatement.setString(1, userToAdd.getUsername());
            preparedStatement.setString(2, userToAdd.getName());
            preparedStatement.setString(3, userToAdd.getStatus());
            //preparedStatement.setString(4, userToAdd.getFoto());
            preparedStatement.executeUpdate();
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
