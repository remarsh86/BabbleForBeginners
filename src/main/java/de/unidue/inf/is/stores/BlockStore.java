package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.Blocks;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlockStore implements Closeable{
    private Connection connection;
    private boolean complete;

    public BlockStore() throws StoreException{
        try {
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public boolean isBlockingClickedUser(String sessionUser, String clickedUser){
        Blocks blocks = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.blocks b WHERE b.blocker = ? " +
                            " AND b.blockee = ?");
            preparedStatement.setString(1, sessionUser);
            preparedStatement.setString(2, clickedUser);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    //blocks = new Blocks(rs.getString(1), rs.getString(2), rs.getString(3));
                    System.out.println(rs.getString(1) + " blocks" + rs.getString(2));
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public void complete() {
        complete = true;
    }

    public void deleteBlock(String blocker, String blockee){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE from dbp72.blocks  WHERE blocker = ? " +
                            "AND blockee = ?");
            preparedStatement.setString(1, blocker);
            preparedStatement.setString(2, blockee);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }

    public void insertBlock(String blocker, String blockee){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO dbp72.blocks (blocker, blockee, reason) VALUES (?, ?, ?)");
            preparedStatement.setString(1, blocker);
            preparedStatement.setString(2, blockee);
            preparedStatement.setString(3, "");
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }

    public ArrayList<Blocks> getBlockTable(){
        ArrayList<Blocks> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.blocks ");


            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    Blocks blocks = new Blocks(rs.getString(1), rs.getString(2), rs.getString(3));
                    list.add(blocks);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
