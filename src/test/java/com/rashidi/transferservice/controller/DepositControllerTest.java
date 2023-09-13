package com.rashidi.transferservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.rashidi.transferservice.controller.dto.ApiErrorResponse;
import com.rashidi.transferservice.controller.dto.DepositResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DepositControllerTest {

  static final String API_URL = "/api/deposits";
  private static final int CUSTOMER_NOT_FOUND_ERROR_CODE = 404;
  private static final int VALIDATION_ERROR_CODE = 400;

  @Autowired
  WebTestClient webTestClient;

  @Container
  @ServiceConnection
  static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:13");

  @Test
  public void shouldAddDepositSuccessfully() {
    String requestJson = """
        {
           "requestUid": "123",
           "customerId": 1,
           "fromAccountNumber": "account1",
           "toAccountNumber": "account2",
           "amount": 25
         }
         """;
    DepositResponseDto depositResponse = webTestClient
        .post().uri(API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestJson)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(DepositResponseDto.class)
        .returnResult()
        .getResponseBody();

    assertThat(depositResponse).isNotNull();
    assertThat(depositResponse.requestUid()).isEqualTo("123");
  }

  @Test
  public void shouldReturnBadRequest_WhenRequestIsNotValid() {
    String requestJson = """
        {
           "requestUid": "123",
           "customerId": 1,
           "fromAccountNumber": "",
           "toAccountNumber": "account2",
           "amount": -5
         }
         """;
    ApiErrorResponse errorResponse = webTestClient
        .post().uri(API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestJson)
        .exchange()
        .expectStatus()
        .is4xxClientError()
        .expectBody(ApiErrorResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.errorCode()).isEqualTo(VALIDATION_ERROR_CODE);
    assertThat(errorResponse.description()).contains("fromAccountNumber: must not be blank", "amount: must be greater than 0");
  }

  @Test
  public void shouldReturnNotFound_WhenCustomerNotFound_OnAddDepositRequest() {
    String requestJson = """
        {
           "requestUid": "123",
           "customerId": 2,
           "fromAccountNumber": "account1",
           "toAccountNumber": "account2",
           "amount": 25
         }
         """;
    ApiErrorResponse errorResponse = webTestClient
        .post().uri(API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestJson)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody(ApiErrorResponse.class)
        .returnResult()
        .getResponseBody();

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.requestUid()).isEqualTo("123");
    assertThat(errorResponse.errorCode()).isEqualTo(CUSTOMER_NOT_FOUND_ERROR_CODE);
    assertThat(errorResponse.description()).isEqualTo("Could not find customer, id: 2");
  }
}
