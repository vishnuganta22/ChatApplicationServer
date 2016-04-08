package com.vishnu.databases.dao;


import com.vishnu.databases.DatabaseHelper;
import com.vishnu.databases.models.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishnu on 10/9/15.
 */
public class MessageDAO {
    private static final String LOG_LABEL = "database.dao.DeviceDAO";
    private static MessageDAO singleton;
    private final DatabaseHelper dbHelper;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static Connection connection = null;
    private Message message = new Message();

    private MessageDAO() {
        this.dbHelper = DatabaseHelper.getInstance();
    }

    public static MessageDAO getInstance() {
        if (singleton == null) {
            singleton = new MessageDAO();
        }
        return singleton;
    }

    public synchronized List<Message> getMessageResultSet() {
        ResultSet resultSet = null;
        List<Message> messageList = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Message.SELECT_STATEMENT);
            if (resultSet != null) {
                messageList = new ArrayList<>();
                while (resultSet.next()) {
                    Message message = new Message(resultSet);
                    messageList.add(message);
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
        return messageList;
    }

    public synchronized List<Message> getMessageByFrom(String from) {
        ResultSet resultSet = null;
        List<Message> messageList = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(Message.SELECT_STATEMENT_FROM);
            preparedStatement.setString(1, from);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messageList = new ArrayList<>();
                while (resultSet.next()) {
                    Message message = new Message(resultSet);
                    messageList.add(message);
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
        return messageList;
    }

    public synchronized List<Message> getMessageByTo(String to) {
        ResultSet resultSet = null;
        List<Message> messageList = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(Message.SELECT_STATEMENT_TO);
            preparedStatement.setString(1, to);
            preparedStatement.setString(2, "false");
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messageList = new ArrayList<>();
                while (resultSet.next()) {
                    Message message = new Message(resultSet);
                    messageList.add(message);
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
        return messageList;
    }

    public synchronized List<Message> getMessageById(String id) {
        ResultSet resultSet = null;
        List<Message> messageList = null;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(Message.SELECT_STATEMENT_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messageList = new ArrayList<>();
                while (resultSet.next()) {
                    Message message = new Message(resultSet);
                    messageList.add(message);
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
        return messageList;
    }

    public synchronized Boolean putMessage(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            statement = connection.createStatement();
            result = statement.executeUpdate(message.getInsertStatement());
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

    public synchronized Boolean updateMessage(String messageId, Message message) {
        Boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(message.getUpdateStatement());
            preparedStatement.setString(1, message.from);
            preparedStatement.setString(2, message.to);
            preparedStatement.setString(3, message.body);
            preparedStatement.setString(4, message.type);
            preparedStatement.setString(5, message.thumbnail);
            preparedStatement.setString(6, message.dateAndTime);
            preparedStatement.setString(7, String.valueOf(message.status));
            preparedStatement.setString(8, messageId);
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

    public synchronized Boolean deleteMessageById(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(message.getDeleteStatement(Message.COL_ID));
            preparedStatement.setString(1, message.id);
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

    public synchronized Boolean deleteMessageByFrom(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(message.getDeleteStatement(Message.COL_FROM));
            preparedStatement.setString(1, message.from);
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

    public synchronized Boolean deleteMessageByTo(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(message.getDeleteStatement(Message.COL_TO));
            preparedStatement.setString(1, message.to);
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

    public synchronized Boolean deleteMessageByType(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            connection = dbHelper.getConnectionWithDB();
            preparedStatement = connection.prepareStatement(message.getDeleteStatement(Message.COL_TYPE));
            preparedStatement.setString(1, message.type);
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
}
