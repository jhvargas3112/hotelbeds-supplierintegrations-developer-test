package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

    long time = 0L;
    int failedLoginAttemps = 0;

    try {
      BufferedReader br = new BufferedReader(new FileReader("logs/login_attemps.log"));

      long lastTimeRec = 0L;

      String rec = null;

      while ((rec = br.readLine()) != null) {
        String[] splitedRec = StringUtils.split(rec, ",");
        if (StringUtils.equals(ip, splitedRec[0])) {
          lastTimeRec = Long.parseLong(splitedRec[1]);
          if (StringUtils.equals(LoginAttempResult.SIGNIN_FAILURE.name(), splitedRec[2])) {
            ++failedLoginAttemps;
          }
          break;
        }
      }

      while ((rec = br.readLine()) != null && time <= 300000) {
        String[] splitedRec = StringUtils.split(rec, ",");
        if (StringUtils.equals(ip, splitedRec[0]) && StringUtils.equals(LoginAttempResult.SIGNIN_FAILURE.name(), splitedRec[2])) {
          ++failedLoginAttemps;

          long currentTimeRec = Long.parseLong(splitedRec[1]);
          time = time + (currentTimeRec - lastTimeRec);
          lastTimeRec = currentTimeRec;
        }
      }

      br.close();


    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {

    }

    return (time == 300000 && failedLoginAttemps >= 5) ? ip : null;
  }

}
