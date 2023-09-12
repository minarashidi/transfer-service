package com.rashidi.transferservice.service;

import com.rashidi.transferservice.exceptions.NotFoundException;
import com.rashidi.transferservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

  private final CustomerRepository customerRepository;

  public void validateCustomer(long id) {
    var customerOptional = customerRepository.findById(id);
    customerOptional.orElseThrow(() -> {
      log.debug("Could not find customer, id: {}", id);
      return new NotFoundException(String.format("Could not find customer, id: %s", id));
    });
  }
}
