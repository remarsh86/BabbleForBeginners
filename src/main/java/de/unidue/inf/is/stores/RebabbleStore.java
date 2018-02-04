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

public class RebabbleStore implements Closeable {


    private Connection connection;
    private boolean complete;



    public RebabbleStore() throws StoreException {
        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public List<Babble> userRebabbledBabble(String username, Babble babble) {
        int id = babble.getId();
        List<Babble> list = new ArrayList<>();
        String sqlstring = " SELECT b.id, b.text, reb.created, b.creator" +
                "  FROM dbp72.Rebabble reb JOIN dbp72.babble b ON reb.babble = b.id " +
                "  WHERE reb.username = ? and reb.babble = ?";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Babble> userRebabbled(String username) {
        List<Babble> list = new ArrayList<>();
        String sqlstring = " SELECT b.id, b.text, reb.created, b.creator" +
                "  FROM dbp72.Rebabble reb JOIN dbp72.babble b ON reb.babble = b.id " +
                "  WHERE reb.username = ? ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertRebabble(String user, int id){
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO dbp72.rebabble (username, babble) values " +
                            " (?, ?)");
            preparedStatement.setString(1, user);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {

            throw new StoreException(e);

        }
    }

    public void deleteRebabble(String user, int id){
        String sqlstring = "delete from dbp72.rebabble reb  " +
                " WHERE reb.username = ? and reb.babble = ? ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, user);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
