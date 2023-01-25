package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.stereotype.Component;

@Component
public class HackerDetectionImpl implements HackerDetection {

  @Override
  public String parseLine(String line) {
    return "dadsass";
  }
}
