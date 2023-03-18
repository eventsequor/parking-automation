package com.parking.database.mysql;

import java.sql.*;

public class MySQLAccess {
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String user;
    private String password;
    private String port;
    private String dbName;


    public MySQLAccess(String user, String password, String port, String dbName) {
        this.user = user;
        this.password = password;
        this.port = port;
        this.dbName = dbName;
        connectToDataBase();
    }

    private void connectToDataBase() {
        try {
            String urlConnection = String.format("jdbc:mysql://localhost:%s/%s", port, dbName);
            connect = DriverManager.getConnection(urlConnection, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            preparedStatement = connect.prepareStatement(query);
            return preparedStatement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connect != null)
                connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
