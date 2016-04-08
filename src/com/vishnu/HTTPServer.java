package com.vishnu;

import com.sun.net.httpserver.HttpServer;
import com.vishnu.databases.DatabaseHelper;
import com.vishnu.handlers.ContactsHandler;
import com.vishnu.handlers.EmailHandler;
import com.vishnu.handlers.PingHandler;
import com.vishnu.handlers.RegisterHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {
    private static final String LOG_LABEL = "HTTPServer";

    public static void main(String[] args) {
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            databaseHelper.checkForDatabase();
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(1555), 0);
            httpServer.createContext("/ping", new PingHandler());
            httpServer.createContext("/register",new RegisterHandler());
            httpServer.createContext("/email",new EmailHandler());
            httpServer.createContext("device//contacts",new ContactsHandler());
            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
