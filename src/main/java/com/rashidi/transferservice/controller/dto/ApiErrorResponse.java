package com.rashidi.transferservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiErrorResponse(
    @Schema(description = "requestUid")
    String requestUid,
    @Schema(description = "Error code")
    int errorCode,
    @Schema(description = "Error description")
    String description) {

}
