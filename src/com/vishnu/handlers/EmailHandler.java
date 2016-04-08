package com.vishnu.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vishnu.utils.Util;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by vishnu on 2/4/16.
 */
public class EmailHandler extends Thread implements HttpHandler {
    private static final String LOG_LABEL = "PingHandler";
    private HttpExchange httpExchange;
    private GenerateOTP generateOTP;
    public static HashMap<String, GenerateOTP> generateOTPMap = new HashMap<>();

    @Override
    public void run() {
        super.run();
        String response;
        int responseCode;
        System.out.println(Util.TAG + " " + LOG_LABEL + " Remote Address " + httpExchange.getRemoteAddress());
        try {
            if (httpExchange.getRequestMethod().equals(Util.HTTP_METHOD_POST)) {
                InputStream inputStream = httpExchange.getRequestBody();
                String requestBody = new String(IOUtils.toByteArray(inputStream));
                System.out.println(Util.TAG + " " + LOG_LABEL + " Request Body " + requestBody);
                if (sendMail(requestBody)) {
                    response = "OTP is sent your mail";
                    responseCode = Util.SUCCESS_RESPONSE_CODE;
                } else {
                    response = "invalid mail id";
                    responseCode = Util.INCORRECT_DATA_RESPONSE_CODE;
                }
            } else {
                responseCode = Util.INVALID_RESPONSE_CODE;
                response = "Invalid Request";
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
        this.run();
    }

    private Boolean sendMail(String emailAddressJSON) {
        String fromAddress = "vishnu.company22@gmail.com";
        String password = "mayee22vish";
        try {
            JSONObject jsonObject = new JSONObject(emailAddressJSON);
            String emailAddress = String.valueOf(jsonObject.get("emailAddress"));
            System.out.println(emailAddress);
            String emailBody = "Hi " + emailAddress + ",\n"
                    + "OTP to Register your mail for ChatApplication is :",
                    emailBody2 = "\nThank you for choosing our application \n\n\nRegards \nvishnu@company";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromAddress, password);
                }
            });
            String id = UUID.randomUUID().toString();
            generateOTP = new GenerateOTP(id);
            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(fromAddress));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            mail.setSubject("OTP To Register For ChatApplication");
            mail.setText(emailBody + generateOTP.init() + emailBody2);
            Transport.send(mail);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    generateOTPMap.remove(emailAddress);
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 300000);
            System.out.println("successfully send the mail to::" + emailAddress);
            generateOTPMap.put(emailAddress, generateOTP);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
