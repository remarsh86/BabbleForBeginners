package de.unidue.inf.is.stores;


import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BabbleStore implements Closeable {

    private Connection connection;
    private boolean complete;

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

    public void addBabble(Babble babbleToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into babbleuser (text, creator) values (?, ?)");
            preparedStatement.setString(1, babbleToAdd.getText());
            preparedStatement.setString(2, babbleToAdd.getCreator());
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