package com.vishnu.databases.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by vishnu on 10/9/15.
 */
public class Message {
    private static final String LOG_LABEL = "database.model.Message";

    public static final String TABLE_NAME = "Message";

    public static final String COL_ID = "ID";
    public static final String COL_FROM = "FROMADDRESS";
    public static final String COL_TO = "TOADDRESS";
    public static final String COL_BODY = "BODY";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_THUMBNAIL = "THUMBNAIL";
    public static final String COL_DATE_AND_TIME = "DATE_AND_TIME";
    public static final String COL_STATUS = "STATUS";

    public static final String[] FIELDS = {COL_ID, COL_FROM, COL_TO, COL_BODY, COL_TYPE, COL_THUMBNAIL, COL_DATE_AND_TIME, COL_STATUS};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " ("
            + COL_ID + " VARCHAR(255), " +
            COL_FROM + " VARCHAR(255), " +
            COL_TO + " VARCHAR(255), " +
            COL_BODY + " VARCHAR(10000), " +
            COL_TYPE + " VARCHAR(255), " +
            COL_THUMBNAIL + " VARCHAR(255), " +
            COL_DATE_AND_TIME + " VARCHAR(255), " +
            COL_STATUS + " VARCHAR(255))";
    public static final String SELECT_STATEMENT = "SELECT "
            + COL_ID + " , "
            + COL_FROM + " , "
            + COL_TO + " , "
            + COL_BODY + " , "
            + COL_TYPE + " , "
            + COL_THUMBNAIL + " , "
            + COL_DATE_AND_TIME + " , "
            + COL_STATUS
            + " FROM " + TABLE_NAME;
    public static final String SELECT_STATEMENT_ID =
            "SELECT " + COL_ID + " , "
                    + COL_FROM + " , "
                    + COL_TO + " , "
                    + COL_BODY + " , "
                    + COL_TYPE + " , "
                    + COL_THUMBNAIL + " , "
                    + COL_DATE_AND_TIME + " , "
                    + COL_STATUS
                    + " FROM " + TABLE_NAME + " WHERE " + Message.COL_ID + " = ? ";

    public static final String SELECT_STATEMENT_FROM =
            "SELECT " + COL_ID + " , "
                    + COL_FROM + " , "
                    + COL_TO + " , "
                    + COL_BODY + " , "
                    + COL_TYPE + " , "
                    + COL_THUMBNAIL + " , "
                    + COL_DATE_AND_TIME + " , "
                    + COL_STATUS
                    + " FROM " + TABLE_NAME + " WHERE " + Message.COL_FROM + " = ? ";

    public static final String SELECT_STATEMENT_TO =
            "SELECT " + COL_ID + " , "
                    + COL_FROM + " , "
                    + COL_TO + " , "
                    + COL_BODY + " , "
                    + COL_TYPE + " , "
                    + COL_THUMBNAIL + " , "
                    + COL_DATE_AND_TIME + " , "
                    + COL_STATUS
                    + " FROM " + TABLE_NAME + " WHERE " + Message.COL_TO + " = ? AND " + Message.COL_STATUS + " = ? ";

    public String id = String.valueOf(UUID.randomUUID());
    public String from = "";
    public String to = "";
    public String body = "";
    public String type = "";
    public String thumbnail = "";
    public String dateAndTime = "";
    public boolean status = false;

    public Message() {
        super();
    }

    public Message(ResultSet resultSet) {
        try {
            this.id = resultSet.getString(1);
            this.from = resultSet.getString(2);
            this.to = resultSet.getString(3);
            this.body = resultSet.getString(4);
            this.type = resultSet.getString(5);
            this.thumbnail = resultSet.getString(6);
            this.dateAndTime = resultSet.getString(7);
            this.status = resultSet.getBoolean(8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getInsertStatement() {
        return "INSERT INTO " + TABLE_NAME + " VALUES ('" + this.id + "', '"
                + this.from + "', '"
                + this.to + "', '"
                + this.body + "', '"
                + this.type + "', '"
                + this.thumbnail + "', '"
                + this.dateAndTime + "', '"
                + this.status +
                "')";
    }

    public String getUpdateStatement() {
        return "UPDATE " + TABLE_NAME + " SET " + COL_FROM + " = ? , "
                + COL_TO + " = ? , "
                + COL_BODY + " = ? , "
                + COL_TYPE + " = ? , "
                + COL_THUMBNAIL + " = ? , "
                + COL_DATE_AND_TIME + " = ? ,"
                + COL_STATUS + " = ? "
                + " WHERE " + COL_ID + " = ?";
    }

    public String getDeleteStatement(String colName) {
        return "DELETE FROM " + TABLE_NAME + " WHERE " + colName + " = ? ";
    }
}
