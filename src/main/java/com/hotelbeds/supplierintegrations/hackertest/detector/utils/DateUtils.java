package com.hotelbeds.supplierintegrations.hackertest.detector.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  private static final String RFC_2822_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
  private static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  public static long minutesDif(long time1, long time2) {
    String localDateTime1;
    String localDateTime2;

    try {
      localDateTime1 = new SimpleDateFormat(RFC_2822_FORMAT).format(
          new SimpleDateFormat(ISO_FORMAT).parse(
              LocalDateTime.ofInstant(Instant.ofEpochMilli(time1), ZoneId.systemDefault())
                  .toString()));

      localDateTime2 = new SimpleDateFormat(RFC_2822_FORMAT).format(
          new SimpleDateFormat(ISO_FORMAT).parse(
              LocalDateTime.ofInstant(Instant.ofEpochMilli(time2), ZoneId.systemDefault())
                  .toString()));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    return Duration.between(
            LocalDateTime.parse(localDateTime2, DateTimeFormatter.ofPattern(RFC_2822_FORMAT)),
            LocalDateTime.parse(localDateTime1, DateTimeFormatter.ofPattern(RFC_2822_FORMAT)))
        .toMinutes();

  }

}
