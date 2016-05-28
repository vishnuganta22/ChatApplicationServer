package com.vishnu.databases;

import com.vishnu.databases.models.Device;
import com.vishnu.databases.models.Message;

import java.sql.*;

/**
 * Created by vishnu on 2/4/16.
 */
public class DatabaseHelper {
    private static final String LOG_LABEL = "database.dao.DatabaseHelper";
    private static DatabaseHelper singleton;

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String DB_URL_WITH_DATABASE = "jdbc:mysql://localhost/chatApplication";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "mayee22vish";
    private static final String DATABASENAME = "chatApplication";

    private DatabaseHelper() {
    }

    public void checkForDatabase() {
        Connection connection = null;
        Connection tableConnection = null;
        Statement tableStatement = null;
        Statement statement = null;
        Boolean alreadyExists = false;
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("connecting to database...");
            connection = DriverManager.getConnection(DB_URL, MYSQL_USERNAME, MYSQL_PASSWORD);

            ResultSet resultSet = connection.getMetaData().getCatalogs();
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equals(DATABASENAME)) {
                    alreadyExists = true;
                    System.out.println("already database is created");
                    break;
                }
            }
            resultSet.close();
            if (!alreadyExists) {
                System.out.println("Creating database...");
                statement = connection.createStatement();
                String sql = "CREATE DATABASE " + DATABASENAME;
                statement.executeUpdate(sql);
                System.out.println("Database created successfully...");
                tableConnection = DriverManager.getConnection(DB_URL_WITH_DATABASE, MYSQL_USERNAME, MYSQL_PASSWORD);
                tableStatement = tableConnection.createStatement();
                tableStatement.execute(Device.CREATE_TABLE);
                tableStatement.execute(Message.CREATE_TABLE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    statement.close();
                if (tableStatement != null) {
                    tableStatement.close();
                }
            } catch (SQLException ignored) {
            }// nothing we can do
            try {
                if (connection != null)
                    connection.close();
                if (tableConnection != null) {
                    tableConnection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static DatabaseHelper getInstance() {
        if (singleton == null) {
            singleton = new DatabaseHelper();
        }
        return singleton;
    }

    public Connection getConnectionWithDB() throws SQLException {
        return DriverManager.getConnection(DB_URL_WITH_DATABASE, MYSQL_USERNAME, MYSQL_PASSWORD);
    }
}
