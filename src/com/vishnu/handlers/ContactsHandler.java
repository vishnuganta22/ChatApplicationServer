package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.utils.Util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vishnu on 2/4/16.
 */
public class ContactsHandler extends Thread implements HttpHandler {
    private static final String LOG_LABEL = "ContactsHandler";
    private HttpExchange httpExchange;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        this.run();
    }

    @Override
    public void run() {
        super.run();
        String response;
        int responseCode;
        System.out.println(Util.TAG + " " + LOG_LABEL + " Remote Address " + httpExchange.getRemoteAddress());
        try {
            if (httpExchange.getRequestMethod().equals(Util.HTTP_METHOD_GET)) {
                response = "";
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
}
