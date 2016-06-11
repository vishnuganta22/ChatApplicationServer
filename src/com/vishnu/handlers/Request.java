package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.exceptions.InvalidURL;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by vishn on 6/11/2016.
 */
abstract class Request extends Thread implements HttpHandler {
    private static final String LOG_LABEL = "handlers.Request";

    public abstract void validate(URI uri) throws InvalidURL;

    void sendResponseToClient(HttpExchange httpExchange, int responseCode, String response) {
        try {
            httpExchange.sendResponseHeaders(responseCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
