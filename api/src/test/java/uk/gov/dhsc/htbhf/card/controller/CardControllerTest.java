package uk.gov.dhsc.htbhf.card.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.dhsc.htbhf.card.model.CardBalance;
import uk.gov.dhsc.htbhf.card.model.CardRequestDTO;
import uk.gov.dhsc.htbhf.card.model.CardResponse;
import uk.gov.dhsc.htbhf.card.model.TransferRequestDTO;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aCardRequestWithAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aValidCardRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aTransferRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aValidTransferRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerTest {

    private static final URI ENDPOINT = URI.create("/v1/cards");
    private static final String BALANCE_URL = ENDPOINT + "/1/balance";
    private static final String TRANSFER_URL = ENDPOINT + "/1/transfer";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateCard() {
        CardRequestDTO cardRequest = aValidCardRequest();

        ResponseEntity<CardResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, CardResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCardAccountId()).isNotNull();
    }

    @Test
    void shouldReturnBadRequestWhenCreatingCardWithAddressMissing() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, ErrorResponse.class);

        assertValidationErrorInResponse(response, "address", "must not be null");
    }

    @Test
    void shouldTransferFundsToCard() {
        TransferRequestDTO transferRequest = aValidTransferRequest();

        ResponseEntity<Void> response = restTemplate.postForEntity(TRANSFER_URL, transferRequest, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldReturnBadRequestWhenTransferringFundsWithNoAmount() {
        TransferRequestDTO card = aTransferRequestWithAmount(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(TRANSFER_URL, card, ErrorResponse.class);

        assertValidationErrorInResponse(response, "amount", "must not be null");
    }

    @Test
    void shouldGetCardBalance() {
        ResponseEntity<CardBalance> response = restTemplate.getForEntity(BALANCE_URL, CardBalance.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }
}
