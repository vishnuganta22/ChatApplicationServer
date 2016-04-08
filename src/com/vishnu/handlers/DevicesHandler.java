package com.vishnu.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.databases.dao.DeviceDAO;
import com.vishnu.databases.models.Device;
import com.vishnu.utils.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by vishnu on 9/4/16.
 */
public class DevicesHandler extends Thread implements HttpHandler{
    private static final String LOG_LABEL = "DevicesHandler";
    private HttpExchange httpExchange;
    private DeviceDAO deviceDAO;

    @Override
    public void run() {
        super.run();
        String response;
        int responseCode;
        System.out.println(Util.TAG + " " + LOG_LABEL + " Remote Address " + httpExchange.getRemoteAddress());
        try {
            if (httpExchange.getRequestMethod().equals(Util.HTTP_METHOD_GET)) {
                List<Device> deviceList = deviceDAO.getDeviceResultSet();
                JsonArray jsonArray = new JsonArray();
                for (Device device : deviceList){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("ID",device.id);
                    jsonObject.addProperty("EMAIL",device.email);
                    jsonObject.addProperty("WALLPAPER",device.wallpaper);
                    jsonObject.addProperty("IS_ONLINE",device.isOnline);
                    jsonArray.add(jsonObject);
                }
                response = jsonArray.toString();
                responseCode = 200;
            } else {
                response = "Invalid Request";
                responseCode = Util.INVALID_RESPONSE_CODE;
            }
            httpExchange.sendResponseHeaders(responseCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        this.deviceDAO = DeviceDAO.getInstance();
        this.run();
    }
}
