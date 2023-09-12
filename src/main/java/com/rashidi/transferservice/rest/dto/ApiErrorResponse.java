package com.rashidi.transferservice.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ApiErrorResponse {

  @Schema(description = "requestUid")
  String requestUid;

  @Schema(description = "Error code")
  int errorCode;

  @Schema(description = "Error description")
  String description;

}
