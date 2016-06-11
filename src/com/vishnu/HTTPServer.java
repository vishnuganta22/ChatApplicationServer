package com.vishnu;

import com.sun.net.httpserver.HttpServer;
import com.vishnu.databases.DatabaseHelper;
import com.vishnu.handlers.*;
import com.vishnu.utils.Util;

import java.net.InetSocketAddress;

public class HTTPServer {
    private static final String LOG_LABEL = "HTTPServer";

    public static void main(String[] args) {
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            databaseHelper.checkForDatabase();
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080),0);
            httpServer.createContext("/ping", new PingHandler());
            httpServer.createContext("/register",new RegisterHandler());
            httpServer.createContext("/email",new EmailHandler());
            httpServer.createContext("/device/contacts",new ContactsHandler());
            httpServer.createContext("/devices",new DevicesHandler());
            httpServer.setExecutor(null); // creates a default executor
            httpServer.start();
            System.out.print(Util.TAG + " " + LOG_LABEL + " :: Server is started ::");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
