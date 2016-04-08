package com.vishnu.databases.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vishnu on 10/9/15.
 */
public class Device {
    private static final String LOG_LABEL = "databases.model.Device";

    public static final String TABLE_NAME = "Device";

    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_WALLPAPER = "WALLPAPER";
    public static final String COL_ISONLINE = "ISONLINE";

    public static final String[] FIELDS = {COL_ID, COL_EMAIL, COL_WALLPAPER, COL_ISONLINE};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ("
            + COL_ID + " VARCHAR(255), " +
            COL_EMAIL + " VARCHAR(255), " +
            COL_WALLPAPER + " VARCHAR(255), " +
            COL_ISONLINE + " VARCHAR(255) " +
            ")";
    public static final String SELECT_STATEMENT = "SELECT " + COL_ID + " , "
            + COL_EMAIL + " , "
            + COL_WALLPAPER + " , "
            + COL_ISONLINE
            + " FROM " + TABLE_NAME;
    public static final String SELECT_STATEMENT_ID =
            "SELECT " + COL_ID + " , "
                    + COL_EMAIL + " , "
                    + COL_WALLPAPER + " , "
                    + COL_ISONLINE
                    + " FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ? ";

    public static final String SELECT_STATEMENT_EXCEPT_ID =
            "SELECT " + COL_ID + " , "
                    + COL_EMAIL + " , "
                    + COL_WALLPAPER + " , "
                    + COL_ISONLINE
                    + " FROM " + TABLE_NAME + " WHERE " + COL_ID + " != ? ";

    public String id = "";
    public String email = "";
    public String wallpaper = "";
    public String isOnline = "";

    public Device() {
        super();
    }

    public Device(ResultSet resultSet) {
        try {
            this.id = resultSet.getString(1);
            this.email = resultSet.getString(2);
            this.wallpaper = resultSet.getString(3);
            this.isOnline = resultSet.getString(4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getInsertStatement() {
        return "INSERT INTO " + TABLE_NAME + " VALUES ('" + this.id + "', '" + this.email + "', '"
                + this.wallpaper + "', '" + this.isOnline + "')";
    }

    public String getUpdateStatement() {
        return "UPDATE " + TABLE_NAME + " SET " + COL_ID + " = ? , "
                + COL_WALLPAPER + " = ? , "
                + COL_ISONLINE + " = ? "
                + " WHERE " + COL_EMAIL + " = ?";
    }

    public String getUpdateStatementForIsOnline() {
        return "UPDATE " + TABLE_NAME + " SET " + COL_ISONLINE + " = ? WHERE " + COL_EMAIL + " = ?";
    }

    public String getUpadteStatementForWallpaper() {
        return "UPDATE " + TABLE_NAME + " SET " + COL_WALLPAPER + " = ? WHERE " + COL_EMAIL + " = ?";
    }

    public String getDeleteStatement() {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_EMAIL + " = ? ";
    }

}
