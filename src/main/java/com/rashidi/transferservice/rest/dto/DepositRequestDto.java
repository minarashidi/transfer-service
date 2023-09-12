package com.rashidi.transferservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rashidi.transferservice.domain.Deposit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DepositRequestDto(
    @Schema(description = "requestUid", example = "6881763f-d955-40e6-b040-26907c9c6377")
    @NotBlank
    String requestUid,
    @Schema(description = "The customer id who does deposit")
    long customerId,
    @Schema(description = "The account number from which money will be withdrawn")
    @NotBlank
    String fromAccountNumber,
    @Schema(description = "The account number to which money will be deposited")
    @NotBlank
    String toAccountNumber,
    @Schema(description = "The transfer amount")
    @NotNull @Positive
    BigDecimal amount) {

  public Deposit toDomain() {
    return Deposit.builder()
        .requestUid(requestUid)
        .customerId(customerId)
        .fromAccountNumber(fromAccountNumber)
        .toAccountNumber(toAccountNumber)
        .amount(amount)
        .build();
  }

}
