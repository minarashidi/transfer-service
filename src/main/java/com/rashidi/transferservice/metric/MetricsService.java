package com.rashidi.transferservice.metric;

import com.rashidi.transferservice.domain.Deposit;

public interface MetricsService {

  void countCreatedDeposits(Deposit deposit);

  void incrementExceptionCounter(String tageName, String tagValue);
}
