package com.hotelbeds.supplierintegrations.hackertest.detector.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateUtilsTest {

  @Test
  public void when_given_two_timestamps_in_millis_then_return_the_correct_difference_between_them_in_minutes() {
    assertEquals(8, DateUtils.minutesDif(1674696749115L, 1674696258066L));
    assertEquals(3, DateUtils.minutesDif(1674698095204L, 1674697912143L));
    assertEquals(16, DateUtils.minutesDif(1674696676906L, 1674695692274L));
    assertEquals(63, DateUtils.minutesDif(1674698095204L, 1674694269193L));
    assertEquals(15, DateUtils.minutesDif(1674693777903L, 1674692871647L));
  }

}
