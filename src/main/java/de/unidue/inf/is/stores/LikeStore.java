package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Babble;
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
            System.out.println("There is a good connection and username is " );
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
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
