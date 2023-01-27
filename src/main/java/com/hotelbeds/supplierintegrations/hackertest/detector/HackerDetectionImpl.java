package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.DateUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HackerDetectionImpl implements HackerDetection {

  private final String loginAttempsLogPath;

  public HackerDetectionImpl(@Value("${login-attemps-log.path}") String loginAttempsLogPath) {
    this.loginAttempsLogPath = loginAttempsLogPath;
  }

  // O(n) time complexity
  @Override
  public String parseLine(String line) {
    String ip = StringUtils.split(line, ",")[0];

    ArrayList<LoginAttempDTO> loginAttemps = new ArrayList<>();

    long minutes = 0L;
    int failedLoginAttemps = 0;

    try {
      BufferedReader br = new BufferedReader(new FileReader(loginAttempsLogPath));

      String loginAttempRecord = null;

      while ((loginAttempRecord = br.readLine()) != null) {
        String[] splitedloginAttempRecord = StringUtils.split(loginAttempRecord, ",");

        loginAttemps.add(new LoginAttempDTO(splitedloginAttempRecord[0],
            Long.parseLong(splitedloginAttempRecord[1]),
            LoginAttempResult.valueOf(splitedloginAttempRecord[2]),
            splitedloginAttempRecord[3]));
      }

      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Collections.reverse(loginAttemps);

    for (int i = 0; i < loginAttemps.size() - 1; i++) {
      LoginAttempDTO loginAttemp = loginAttemps.get(i);

      if (StringUtils.equals(ip, loginAttemp.getIp()) && StringUtils.equals(
          loginAttemp.getLoginAttempResult().toString(),
          LoginAttempResult.SIGNIN_FAILURE.name())) {
        failedLoginAttemps++;
      }

      minutes = minutes + (DateUtils.minutesDif(loginAttemps.get(i).getInstant(),
          loginAttemps.get(i + 1).getInstant()));
    }

    LoginAttempDTO lastLoginAttemp = loginAttemps.get(loginAttemps.size() - 1);

    if (StringUtils.equals(ip, lastLoginAttemp.getIp()) && StringUtils.equals(
        lastLoginAttemp.getLoginAttempResult().toString(),
        LoginAttempResult.SIGNIN_FAILURE.name())) {
      failedLoginAttemps++;
    }

    return (minutes == 5 && failedLoginAttemps >= 5) ? ip : null;
  }

}
