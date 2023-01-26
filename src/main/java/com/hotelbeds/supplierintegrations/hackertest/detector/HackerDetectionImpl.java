package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.DateUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class HackerDetectionImpl implements HackerDetection {

  // O(n) time complexity
  @Override
  public String parseLine(String line) {
    String ip = StringUtils.split(line, ",")[0];

    long minutes = 0L;
    int failedLoginAttemps = 0;

    try {
      BufferedReader br = new BufferedReader(new FileReader("logs/login_attemps.log"));

      long lastTimeStamp = 0L;

      String loginAttempRecord = null;

      while ((loginAttempRecord = br.readLine()) != null) {
        String[] splitedloginAttempRecord = StringUtils.split(loginAttempRecord, ",");

        if (StringUtils.equals(ip, splitedloginAttempRecord[0])) {
          lastTimeStamp = Long.parseLong(splitedloginAttempRecord[1]);
          if (StringUtils.equals(LoginAttempResult.SIGNIN_FAILURE.name(),
              splitedloginAttempRecord[2])) {
            ++failedLoginAttemps;
          }

          break;
        }
      }

      while ((loginAttempRecord = br.readLine()) != null && minutes <= 5) {
        String[] splitedRec = StringUtils.split(loginAttempRecord, ",");

        if (StringUtils.equals(ip, splitedRec[0]) && StringUtils.equals(
            LoginAttempResult.SIGNIN_FAILURE.name(), splitedRec[2])) {
          ++failedLoginAttemps;
        }

        long currentTimeStamp = Long.parseLong(splitedRec[1]);

        minutes = minutes + (DateUtils.minutesDif(currentTimeStamp, lastTimeStamp));
        lastTimeStamp = currentTimeStamp;
      }

      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return (minutes == 5 && failedLoginAttemps >= 5) ? ip : null;
  }

}
