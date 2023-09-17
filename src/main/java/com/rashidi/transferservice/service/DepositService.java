package com.rashidi.transferservice.service;

import com.rashidi.transferservice.domain.Deposit;
import com.rashidi.transferservice.metric.MetricsService;
import com.rashidi.transferservice.repository.DepositRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DepositService {

  private final DepositRepository depositRepository;
  private final CustomerService customerService;
  private MetricsService serviceMetrics;

  @Transactional
  public Deposit add(Deposit deposit) {
    var depositOptional = depositRepository.findByRequestUid(deposit.getRequestUid());
    return depositOptional.orElseGet(() -> {
      log.debug("New deposit request, requestUid: {}", deposit.getRequestUid());
      customerService.validateCustomer(deposit.getCustomerId());
      var savedDeposit = depositRepository.save(deposit);
      serviceMetrics.countCreatedDeposits(savedDeposit);
      return savedDeposit;
    });
  }
}
