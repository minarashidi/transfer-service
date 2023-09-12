package com.rashidi.transferservice.service;

import com.rashidi.transferservice.domain.Deposit;
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

  @Transactional
  public Deposit add(Deposit deposit) {
    customerService.validateCustomer(deposit.getCustomerId());
    return depositRepository.save(deposit);
  }
}
