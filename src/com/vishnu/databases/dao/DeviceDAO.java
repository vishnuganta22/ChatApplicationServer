package com.vishnu.databases.dao;


import com.vishnu.databases.DatabaseHelper;
import com.vishnu.databases.models.Device;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishnu on 10/9/15.
 */
public class DeviceDAO {
    private static final String LOG_LABEL = "database.dao.DeviceDAO";
    private static DeviceDAO singleton;
    private final DatabaseHelper dbHelper;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static Connection connection = null;
    private Device device = new Device();

    private DeviceDAO() {
        this.dbHelper = DatabaseHelper.getInstance();
    }

    public static DeviceDAO getInstance() {
        if (singleton == null) {
            singleton = new DeviceDAO();
        }
        return singleton;
    }

    public synchronized List<Device> getDeviceResultSet() {
        List<Device> deviceList = null;
        ResultSet resultSet = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Device.SELECT_STATEMENT);
            if (resultSet != null) {
                deviceList = new ArrayList<>();
                while (resultSet.next()) {
                    Device device = new Device(resultSet);
                    deviceList.add(device);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deviceList;
    }

    public synchronized List<Device> getDeviceById(String id) {
        ResultSet resultSet = null;
        List<Device> deviceList = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(Device.SELECT_STATEMENT_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                deviceList = new ArrayList<>();
                while (resultSet.next()) {
                    Device device = new Device(resultSet);
                    deviceList.add(device);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deviceList;
    }

    public synchronized Boolean putDevice(final Device device) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            if (device.id != null) {
                preparedStatement = connection.prepareStatement(device.getUpdateStatement());
                preparedStatement.setString(1, device.id);
                preparedStatement.setString(2, device.wallpaper);
                preparedStatement.setString(3, device.isOnline);
                preparedStatement.setString(4, device.email);
                result = preparedStatement.executeUpdate();
            }
            if (result > 0) {
                success = true;
            } else {
                statement = connection.createStatement();
                result = statement.executeUpdate(device.getInsertStatement());
            }
            if (result > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public synchronized Boolean deleteDevice(final Device device) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(device.getDeleteStatement());
            preparedStatement.setString(1, device.email);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public synchronized List<Device> getDeviceExceptId(String id) {
        List<Device> deviceList = null;
        ResultSet resultSet = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(Device.SELECT_STATEMENT_EXCEPT_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                deviceList = new ArrayList<>();
                while (resultSet.next()) {
                    Device device = new Device(resultSet);
                    deviceList.add(device);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deviceList;
    }
}
