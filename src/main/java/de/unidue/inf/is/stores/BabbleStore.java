package de.unidue.inf.is.stores;


import de.unidue.inf.is.domain.Babble;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BabbleStore implements Closeable {

    private Connection connection;
    private boolean complete;
   // private String username;


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

    public void deleteBabble(int id){
        String sqlstring = "delete from dbp72.babble b  " +
                " WHERE b.id = ?";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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

    public Babble getBabble(int clickedId){
        Babble b = new Babble();
        try{
            PreparedStatement pst = connection.prepareStatement("Select * from dbp72.babble where id = ?");
            pst.setInt(1, clickedId);
            ResultSet rs = pst.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else{
                while (rs.next()){
                    b = new Babble();
                    b.setId(rs.getInt(1));
                    b.setText(rs.getString(2));
                    b.setCreated(rs.getTimestamp(3));
                    b.setCreator(rs.getString(4));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public List<Babble> searchBabble(String substring){
        String string2 = "%" + substring + "%";
        List<Babble> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM dbp72.babble where text like ? ");
            preparedStatement.setString(1, string2);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    System.out.println("result set not null");
                    //System.out.println(rs.getInt(1) + " " + rs.getString(2) +" "+rs.getTimestamp(3)+" " +rs.getString(4));
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

    public List<Babble> searchBabble2(String substring){
        String string1 = substring.toUpperCase();
        String string2 = "%" + string1 + "%";
        List<Babble> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("create view nocasebabble as select b.id, upper(b.text), b.created, b.creator from dbp72.babble b");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlstring = "SELECT * FROM nocasebabble where text like ? ";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlstring);
            preparedStatement.setString(1, string2);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs == null) System.out.println("No result set.");
            else {
                while (rs.next()) {
                    System.out.println("result set not null");
                    //System.out.println(rs.getInt(1) + " " + rs.getString(2) +" "+rs.getTimestamp(3)+" " +rs.getString(4));
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
