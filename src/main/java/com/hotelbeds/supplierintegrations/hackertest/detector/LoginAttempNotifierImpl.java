package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Component;

@Component
public class LoginAttempNotifierImpl implements LoginAttempNotifier {
    @Override
    public void notifyLoginAttempResult(String userName, String ip, boolean result) {

        if (result) {
            notifySuccessLoginAttempResult(userName, ip);
        } else {
            notifyFailedLoginAttempResult(userName, ip);
        }

    }

    private void notifySuccessLoginAttempResult(String userName, String ip) {

    }

    private void notifyFailedLoginAttempResult(String userName, String ip) {

    }
}
