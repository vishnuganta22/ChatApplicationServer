package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.exceptions.InvalidURL;
import com.vishnu.utils.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by vishnu on 2/4/16.
 */
public class PingHandler extends Request {
    private static final String LOG_LABEL = "PingHandler";
    private HttpExchange httpExchange;

    @Override
    public void run() {
        super.run();
        String response;
        int responseCode;
        System.out.println(Util.TAG + " " + LOG_LABEL + " Remote Address " + httpExchange.getRemoteAddress());
        if (httpExchange.getRequestMethod().equals(Util.HTTP_METHOD_GET)) {
            response = "Ping is successful";
            responseCode = Util.SUCCESS_RESPONSE_CODE;
        } else {
            response = "Invalid Request";
            responseCode = Util.INVALID_RESPONSE_CODE;
        }
        sendResponseToClient(httpExchange, responseCode, response);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        try {
            validate(httpExchange.getRequestURI());
        } catch (InvalidURL invalidURL) {
            System.out.println(Util.TAG + " " + LOG_LABEL + invalidURL.getMessage());
            sendResponseToClient(httpExchange,Util.INVALID_REQUEST_CODE,invalidURL.getMessage());
        }
    }

    @Override
    public void validate(URI uri) throws InvalidURL {
        String[] paths = uri.getPath().split("/");
        String REQUEST = "ping";
        if (paths.length == 2 && paths[1].equalsIgnoreCase(REQUEST)) {
            run();
        } else {
            throw new InvalidURL("Invalid Path - No such path exists");
        }
    }
}
