package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.utils.Util;

import java.io.IOException;

/**
 * Created by vishnu on 2/4/16.
 */
public class ContactsHandler extends Thread implements HttpHandler{
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
        if(httpExchange.getRequestMethod().equals(Util.HTTP_METHOD_GET)){

        }else{

        }
    }
}
