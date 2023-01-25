package com.hotelbeds.supplierintegrations.hackertest.detector;

public interface LoginAttempNotifier {

    void notifyLoginAttempResult(String userName, String ip, boolean result);

}
