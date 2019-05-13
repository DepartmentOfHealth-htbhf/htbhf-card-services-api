package uk.gov.dhsc.htbhf.card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.card.model.*;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aCardRequestWithAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aValidCardRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aDepositFundsRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsResponseTestDataFactory.aValidDepositFundsResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.DEPOSIT_REFERENCE_ID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CardServicesIntegrationTest {

    private static final URI ENDPOINT = URI.create("/v1/cards");
    private static final String BALANCE_URL = ENDPOINT + "/1/balance";
    private static final String DEPOSIT_URL = ENDPOINT + "/1/deposit";

    private static final String CARD_SERVICES_DEPOSIT_URL = "http://localhost:8120/v1/cards/1/deposit";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RestTemplate restTemplateWithIdHeaders;

    @Test
    void shouldSuccessfullyCreateCard() {
        CardRequestDTO cardRequest = aValidCardRequest();

        ResponseEntity<CardResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, CardResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCardAccountId()).isNotNull();
    }

    @Test
    void shouldReturnBadRequestWithInvalidCardRequest() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, ErrorResponse.class);

        assertValidationErrorInResponse(response, "address", "must not be null");
    }

    @Test
    void shouldSuccessfullyDepositFundsToCard() {
        DepositFundsRequestDTO depositFundsRequestDTO = aValidDepositFundsRequest();
        DepositFundsResponse depositFundsResponse = aValidDepositFundsResponse();
        ResponseEntity<DepositFundsResponse> depositFundsResponseEntity = new ResponseEntity<>(depositFundsResponse, OK);
        given(restTemplateWithIdHeaders.postForEntity(anyString(), any(), eq(DepositFundsResponse.class))).willReturn(depositFundsResponseEntity);

        ResponseEntity<DepositFundsResponse> response = restTemplate.postForEntity(DEPOSIT_URL, depositFundsRequestDTO, DepositFundsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        DepositFundsResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getReferenceId()).isEqualTo(DEPOSIT_REFERENCE_ID);
        verify(restTemplateWithIdHeaders).postForEntity(CARD_SERVICES_DEPOSIT_URL, depositFundsRequestDTO, DepositFundsResponse.class);
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

}
