package com.rashidi.transferservice.controller.fixture;

import com.rashidi.transferservice.domain.Deposit;
import java.math.BigDecimal;

public class DepositFixture {

  public static final String REQUEST_UID = "1111-3333";
  public static final long CUSTOMER_ID = 1;
  public static final String FROM_ACCOUNT_NUMBER = "22351111";
  public static final String TO_ACCOUNT_NUMBER = "55761161";
  public static final BigDecimal AMOUNT = new BigDecimal("150.00");

  public static Deposit createDeposit() {
    return Deposit.builder()
        .requestUid(REQUEST_UID)
        .customerId(CUSTOMER_ID)
        .fromAccountNumber(FROM_ACCOUNT_NUMBER)
        .toAccountNumber(TO_ACCOUNT_NUMBER)
        .amount(AMOUNT)
        .build();
  }
}
