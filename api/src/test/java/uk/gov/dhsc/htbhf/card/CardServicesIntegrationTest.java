package uk.gov.dhsc.htbhf.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import uk.gov.dhsc.htbhf.card.model.CardBalance;
import uk.gov.dhsc.htbhf.card.model.CardRequestDTO;
import uk.gov.dhsc.htbhf.card.model.CardResponse;
import uk.gov.dhsc.htbhf.card.model.DepositFundsRequestDTO;
import uk.gov.dhsc.htbhf.card.model.DepositFundsResponse;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aCardRequestWithAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aValidCardRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.CardResponseTestDataFactory.aValidCardResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aDepositFundsRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsResponseTestDataFactory.aValidDepositFundsResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.CARD_ID;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.DEPOSIT_REFERENCE_ID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8120)
class CardServicesIntegrationTest {

    private static final URI ENDPOINT = URI.create("/v1/cards");
    private static final String BALANCE_URL = ENDPOINT + "/1/balance";
    private static final String DEPOSIT_URL = ENDPOINT + "/1/deposit";
    private static final String CARD_SERVICES_URL = "/v1/cards";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    @SuppressWarnings("PMD.UnnecessaryFullyQualifiedName")
    void tearDown() {
        WireMock.reset();
    }

    @Test
    void shouldSuccessfullyCreateCard() throws JsonProcessingException {
        CardRequestDTO cardRequest = aValidCardRequest();
        CardResponse cardResponseResponse = aValidCardResponse();
        stubNewCardEndpointWithSuccessfulResponse(cardResponseResponse);

        ResponseEntity<CardResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, CardResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        CardResponse cardResponse = response.getBody();
        assertThat(cardResponse).isNotNull();
        assertThat(cardResponse.getCardAccountId()).isEqualTo(CARD_ID);
        verifyPostToNewCardEndpoint();
    }

    @Test
    void shouldReturnBadRequestWithInvalidCardRequest() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, ErrorResponse.class);

        assertValidationErrorInResponse(response, "address", "must not be null");
    }

    @Test
    void shouldSuccessfullyDepositFundsToCard() throws JsonProcessingException {
        DepositFundsRequestDTO depositFundsRequestDTO = aValidDepositFundsRequest();
        DepositFundsResponse depositFundsResponse = aValidDepositFundsResponse();
        stubDepositFundsEndpointWithSuccessfulResponse(depositFundsResponse);

        ResponseEntity<DepositFundsResponse> response = restTemplate.postForEntity(DEPOSIT_URL, depositFundsRequestDTO, DepositFundsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        DepositFundsResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getReferenceId()).isEqualTo(DEPOSIT_REFERENCE_ID);
        verifyPostToDepositFundsEndpoint();
    }

    @Test
    void shouldReturnBadRequestWithInvalidDepositFundsRequest() {
        DepositFundsRequestDTO depositFundsRequestDTO = aDepositFundsRequestWithAmount(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(DEPOSIT_URL, depositFundsRequestDTO, ErrorResponse.class);

        assertValidationErrorInResponse(response, "amountInPence", "must not be null");
    }

    @Test
    void shouldSuccessfullyGetCardBalance() {
        ResponseEntity<CardBalance> response = restTemplate.getForEntity(BALANCE_URL, CardBalance.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        CardBalance balance = response.getBody();
        assertThat(balance).isNotNull();
        assertThat(balance.getAvailableBalanceInPence()).isEqualTo(10);
        assertThat(balance.getLedgerBalanceInPence()).isEqualTo(10);
    }

    private void stubNewCardEndpointWithSuccessfulResponse(CardResponse cardResponse) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(cardResponse);
        stubFor(post(urlEqualTo(CARD_SERVICES_URL)).willReturn(okJson(json)));
    }

    private void stubDepositFundsEndpointWithSuccessfulResponse(DepositFundsResponse depositFundsResponse) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(depositFundsResponse);
        stubFor(post(urlEqualTo(DEPOSIT_URL)).willReturn(okJson(json)));
    }

    private void verifyPostToNewCardEndpoint() {
        verify(exactly(1), postRequestedFor(urlEqualTo(CARD_SERVICES_URL)));
    }

    private void verifyPostToDepositFundsEndpoint() {
        verify(exactly(1), postRequestedFor(urlEqualTo(DEPOSIT_URL)));
    }
}
