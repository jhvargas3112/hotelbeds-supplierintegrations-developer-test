package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LoginAttempNotifierImpl implements LoginAttempNotifier {

    private static final Logger log = LoggerFactory.getLogger(LoginAttempNotifierImpl.class);

    @Override
    public String notifyLoginAttempResult(String userName, String ip, boolean result) {

        if (result) {
            notifySuccessLoginAttempResult(userName, ip);
        } else {
            notifyFailedLoginAttempResult(userName, ip);
        }

        return new StringBuilder().append(ip)
                .appendCodePoint(44)
                .append(Instant.now().toEpochMilli())
                .appendCodePoint(44)
                .append(result ? LoginAttempResult.SIGNIN_SUCCESS : LoginAttempResult.SIGNIN_FAILURE)
                .appendCodePoint(44)
                .append(userName)
                .toString();
    }

    private void notifySuccessLoginAttempResult(String userName, String ip) {
        log.info(ip + ",{}" + ",{}" + ",{}", Instant.now().toEpochMilli(), LoginAttempResult.SIGNIN_SUCCESS, userName);
    }

    private void notifyFailedLoginAttempResult(String userName, String ip) {
        log.info(ip + ",{}" + ",{}" + ",{}", Instant.now().toEpochMilli(), LoginAttempResult.SIGNIN_FAILURE, userName);
    }

}
