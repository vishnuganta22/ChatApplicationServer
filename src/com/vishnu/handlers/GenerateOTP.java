package com.vishnu.handlers;

import com.vishnu.databases.dao.DeviceDAO;
import com.vishnu.databases.models.Device;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by vishnu on 2/4/16.
 */
class GenerateOTP {
    private static final String LOG_LABEL = "GenerateOTP";
    static final int VALID_OTP = 1;
    private static final int INVALID_OTP = 2;
    static final int OTP_EXPIRED = 3;
    static final int OTP_IS_NULL = 4;

    String id;
    private Long eTimeStamp;
    private String otp;

    GenerateOTP(String id) {
        this.id = id;
    }

    String init() {
        otp = id.replace("-", "");
        otp = otp.substring(9, 17);
        Date date = new Date();
        Long timeStamp = date.getTime();
        this.eTimeStamp = timeStamp + 300000;
        return otp;
    }

    int validateOTP(String JSON) {
        String clientOTP = null;
        String emailAddress = null;
        try {
            JSONObject otpJSON = new JSONObject(JSON);
            emailAddress = String.valueOf(otpJSON.get("emailAddress"));
            clientOTP = String.valueOf(otpJSON.get("otp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        if (clientOTP != null) {
            if (date.getTime() <= eTimeStamp && clientOTP.equals(otp)) {
                Device device = new Device();
                device.id = id;
                device.email = emailAddress;
                device.wallpaper = "";
                device.isOnline = "true";
                DeviceDAO deviceDAO = DeviceDAO.getInstance();
                deviceDAO.putDevice(device);
                return VALID_OTP;
            } else {
                if (date.getTime() > eTimeStamp) {
                    return OTP_EXPIRED;
                }
                return INVALID_OTP;
            }
        }
        return OTP_IS_NULL;
    }
}
