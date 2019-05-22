package uk.gov.dhsc.htbhf.card.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import uk.gov.dhsc.htbhf.card.model.*;
import uk.gov.dhsc.htbhf.card.service.CardService;
import uk.gov.dhsc.htbhf.errorhandler.ErrorResponse;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.assertions.IntegrationTestAssertions.assertValidationErrorInResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.CardBalanceTestDataFactory.aValidCardBalance;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aCardRequestWithAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aValidCardRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.CardResponseTestDataFactory.aValidCardResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aDepositFundsRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsResponseTestDataFactory.aValidDepositFundsResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerTest {

    private static final URI ENDPOINT = URI.create("/v1/cards");
    private static final String BALANCE_URL = ENDPOINT + "/1/balance";
    private static final String DEPOSIT_URL = ENDPOINT + "/1/deposit";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CardService cardService;

    @Test
    void shouldCreateCard() {
        CardRequestDTO cardRequest = aValidCardRequest();
        given(cardService.createCard(any())).willReturn(aValidCardResponse());

        ResponseEntity<CardResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, CardResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCardAccountId()).isNotNull();
        verify(cardService).createCard(cardRequest);
    }

    @Test
    void shouldReturnBadRequestWhenCreatingCardWithAddressMissing() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(ENDPOINT, cardRequest, ErrorResponse.class);

        assertValidationErrorInResponse(response, "address", "must not be null");
        verifyZeroInteractions(cardService);
    }

    @Test
    void shouldDepositFundsToCard() {
        DepositFundsRequestDTO depositFundsRequestDTO = aValidDepositFundsRequest();
        given(cardService.depositFunds(any(), any())).willReturn(aValidDepositFundsResponse());

        ResponseEntity<Void> response = restTemplate.postForEntity(DEPOSIT_URL, depositFundsRequestDTO, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        verify(cardService).depositFunds("1", depositFundsRequestDTO);
    }

    @Test
    void shouldReturnBadRequestWhenDepositFundsWithNoAmount() {
        DepositFundsRequestDTO depositFundsRequestDTO = aDepositFundsRequestWithAmount(null);

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(DEPOSIT_URL, depositFundsRequestDTO, ErrorResponse.class);

        assertValidationErrorInResponse(response, "amountInPence", "must not be null");
        verifyZeroInteractions(cardService);
    }

    @Test
    void shouldGetCardBalance() {
        CardBalance cardBalance = aValidCardBalance();
        given(cardService.getBalance(any())).willReturn(cardBalance);

        ResponseEntity<CardBalance> response = restTemplate.getForEntity(BALANCE_URL, CardBalance.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        CardBalance balance = response.getBody();
        assertThat(balance).isEqualTo(cardBalance);
        verify(cardService).getBalance("1");
    }
}
