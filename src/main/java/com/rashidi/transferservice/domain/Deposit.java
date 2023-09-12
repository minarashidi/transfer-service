package com.rashidi.transferservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table(schema = "transfer_service", name = "deposit")
public class Deposit {

  @Id
  long id;
  String requestUid;
  long customerId;
  String fromAccountNumber;
  String toAccountNumber;
  BigDecimal amount;
  LocalDateTime createdAt;
}
