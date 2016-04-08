package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.utils.Util;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vishnu on 2/4/16.
 */
public class RegisterHandler extends Thread implements HttpHandler {
    private static final String LOG_LABEL = "RegisterHandler";
    private HttpExchange httpExchange;
    private GenerateOTP generateOTP;

    @Override
    public void run() {
        super.run();
        try {
            String response;
            int responseCode;
            System.out.println(Util.TAG + " " + LOG_LABEL + " Remote Address " + httpExchange.getRemoteAddress());
            if (httpExchange.getRequestMethod().equals("POST")) {
                InputStream inputStream = httpExchange.getRequestBody();
                String requestBody = new String(IOUtils.toByteArray(inputStream));
                System.out.println(Util.TAG + " " + LOG_LABEL + " Request Body " + requestBody);
                JSONObject OTPJSON = new JSONObject(requestBody);
                String emailAddress = String.valueOf(OTPJSON.get("emailAddress"));
                generateOTP = EmailHandler.generateOTPMap.get(emailAddress);
                if(generateOTP != null){
                    int otp_validation = generateOTP.validateOTP(requestBody);
                    if (otp_validation == GenerateOTP.VALID_OTP) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", generateOTP.id);
                        jsonObject.put("emailAddress", emailAddress);
                        response = jsonObject.toString();
                        responseCode = Util.SUCCESS_RESPONSE_CODE;
                        EmailHandler.generateOTPMap.remove(emailAddress);
                    } else if (otp_validation == GenerateOTP.OTP_EXPIRED) {
                        EmailHandler.generateOTPMap.remove(emailAddress);
                        response = "OTP IS EXPIRED";
                        responseCode = Util.INCORRECT_DATA_RESPONSE_CODE;
                    } else {
                        response = "Invalid OTP";
                        responseCode = Util.INCORRECT_DATA_RESPONSE_CODE;
                    }
                }else{
                    response = "OTP IS EXPIRED";
                    responseCode = Util.INCORRECT_DATA_RESPONSE_CODE;
                }
            } else {
                response = "Invalid request";
                responseCode = Util.INVALID_RESPONSE_CODE;
            }
            httpExchange.sendResponseHeaders(responseCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        this.run();
    }
}
