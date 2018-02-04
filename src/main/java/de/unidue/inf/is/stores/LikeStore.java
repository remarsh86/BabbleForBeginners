package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.domain.BabbleUser;
import de.unidue.inf.is.domain.LikesBabble;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeStore implements Closeable{


    private Connection connection;
    private boolean complete;
    private String username;


    public LikeStore() throws StoreException {

        try {
            //connection = DBUtil.getConnection("testdb");
            connection = DBUtil.getExternalConnection("babble"); //war dbtest
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public List<LikesBabble> userLikedBabble(String name, Babble b) {
        int id = b.getId();
        List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "SELECT likes.username, likes.babble, likes.type, likes.created" +
                " FROM dbp72.likesbabble likes  " +
                " WHERE likes.username = ? and likes.babble = ?  and likes.type = 'like'";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    LikesBabble l = new LikesBabble();
                    l.setUsername(rs.getString(1));
                    l.setBabble(rs.getInt(2));
                    l.setType(rs.getString(3));
                    l.setCreated(rs.getTimestamp(4));

                    list.add(l);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LikesBabble> userDislikedBabble(String name, Babble b) {
        int id = b.getId();
        List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "SELECT likes.username, likes.babble, likes.type, likes.created" +
                " FROM dbp72.likesbabble likes  " +
                " WHERE likes.username = ? and likes.babble = ?  and likes.type = 'dislike'";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    LikesBabble l = new LikesBabble();
                    l.setUsername(rs.getString(1));
                    l.setBabble(rs.getInt(2));
                    l.setType(rs.getString(3));
                    l.setCreated(rs.getTimestamp(4));

                    list.add(l);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<LikesBabble> userDislikedorLikedBabble(String name, Babble b) {
        int id = b.getId();
        List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "SELECT likes.username, likes.babble, likes.type, likes.created" +
                " FROM dbp72.likesbabble likes  " +
                " WHERE likes.username = ? and likes.babble = ?  ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    LikesBabble l = new LikesBabble();
                    l.setUsername(rs.getString(1));
                    l.setBabble(rs.getInt(2));
                    l.setType(rs.getString(3));
                    l.setCreated(rs.getTimestamp(4));

                    list.add(l);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LikesBabble> userLiked(String name) {
        List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "SELECT likes.username, likes.babble, likes.type, likes.created" +
                " FROM dbp72.likesbabble likes  " +
                " WHERE likes.username = ? ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    LikesBabble l = new LikesBabble();
                    l.setUsername(rs.getString(1));
                    l.setBabble(rs.getInt(2));
                    l.setType(rs.getString(3));
                    l.setCreated(rs.getTimestamp(4));

                    list.add(l);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateLike(String user, int babbleid, String type){
        //List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "update dbp72.likesbabble likes set likes.type = ? " +
                " WHERE likes.username = ? and likes.babble = ?  ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, user);
            preparedStatement.setInt(3, babbleid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return list;
    }

    public void removeLike(String user, int babbleid){
        //List<LikesBabble> list = new ArrayList<>();
        String sqlstring = "delete from dbp72.likesbabble likes  " +
                " WHERE likes.username = ? and likes.babble = ? ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, user);
            preparedStatement.setInt(2, babbleid);
           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return list;
    }

    public void insertLike(BabbleUser user, int bid, String type){
        System.out.println("try prepared statement");
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO dbp72. LikesBabble (username, babble, type) values " +
                            " (?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, bid);
            preparedStatement.setString(3, type);
            preparedStatement.executeUpdate();

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
