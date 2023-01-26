package com.hotelbeds.supplierintegrations.hackertest.detector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HackerDetectionImplTest {

  private HackerDetectionImpl hackerDetection;

  @Test
  public void when_given_a_file_log_with_five_records_then_its_detect_a_malicious_ip() {
    hackerDetection = new HackerDetectionImpl("src/test/resources/login_attemps_tests1.log");
    String ip = hackerDetection.parseLine("80.238.9.155,1674673376786,SIGNIN_FAILED,jhonny");
    assertEquals("80.238.9.155", ip);
  }

  @Test
  public void when_given_a_file_log_with_five_records_then_its_not_detect_a_malicious_ip() {
    hackerDetection = new HackerDetectionImpl("src/test/resources/login_attemps_tests2.log");
    String ip = hackerDetection.parseLine("80.238.9.155,1674673376786,SIGNIN_FAILED,jhonny");
    assertNull(ip);
  }

  @Test
  public void when_given_a_file_log_with_records_of_different_customers_then_its_detect_a_malicious_ip() {
    hackerDetection = new HackerDetectionImpl("src/test/resources/login_attemps_tests3.log");
    String ip = hackerDetection.parseLine("80.238.9.175,1674673491660,SIGNIN_FAILURE,ana");
    assertEquals("80.238.9.175", ip);
  }

}
