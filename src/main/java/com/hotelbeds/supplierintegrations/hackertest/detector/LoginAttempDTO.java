package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.enums.LoginAttempResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttempDTO {

  @NonNull
  private String ip;

  @NonNull
  private Long instant;

  @NonNull
  private LoginAttempResult loginAttempResult;

  @NonNull
  private String userName;

}
