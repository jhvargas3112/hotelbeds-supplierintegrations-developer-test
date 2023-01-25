package com.hotelbeds.supplierintegrations.hackertest.detector;

public interface LoginAttempNotifier {

    String notifyLoginAttempResult(String userName, String ip, boolean result);

}
