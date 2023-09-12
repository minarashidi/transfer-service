package com.rashidi.transferservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rashidi.transferservice.domain.Deposit;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DepositResponseDto(
    @Schema(description = "requestUid")
    String requestUid,
    @Schema(description = "Created deposit id")
    long id) {

  public static DepositResponseDto fromDomain(Deposit deposit) {
    return DepositResponseDto.builder()
        .requestUid(deposit.getRequestUid())
        .id(deposit.getId())
        .build();
  }
}
