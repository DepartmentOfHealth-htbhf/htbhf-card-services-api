package uk.gov.dhsc.htbhf.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.card.model.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static uk.gov.dhsc.htbhf.card.testhelper.CardBalanceTestDataFactory.aValidCardBalance;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.aValidCardRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.CardResponseTestDataFactory.aValidCardResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsResponseTestDataFactory.aValidDepositFundsResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.CARD_ID;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.DEPOSIT_REFERENCE_ID;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    private static final String BASE_URI = "http://localhost:8120";
    private static final String CARDS_URI = BASE_URI + "/v1/cards";

    @Mock
    private RestTemplate restTemplate;

    private CardService cardService;

    @BeforeEach
    void init() {
        cardService = new CardService(BASE_URI, restTemplate);
    }

    @Test
    void shouldSuccessfullyDepositFunds() {
        //Given
        String cardId = "123456";
        String cardUrl = buildUrl(cardId, "/deposit");
        DepositFundsRequestDTO request = aValidDepositFundsRequest();
        given(restTemplate.postForEntity(cardUrl, request, DepositFundsResponse.class))
                .willReturn(new ResponseEntity<>(aValidDepositFundsResponse(), HttpStatus.OK));

        //When
        DepositFundsResponse depositFundsResponse = cardService.depositFunds(cardId, request);

        //Then
        assertThat(depositFundsResponse).isNotNull();
        assertThat(depositFundsResponse.getReferenceId()).isEqualTo(DEPOSIT_REFERENCE_ID);
        verify(restTemplate).postForEntity(cardUrl, request, DepositFundsResponse.class);
    }

    @Test
    void shouldSuccessfullyCreateCard() {
        //Given
        CardRequestDTO request = aValidCardRequest();
        ResponseEntity<CardResponse> responseEntity = new ResponseEntity<>(aValidCardResponse(), HttpStatus.OK);
        given(restTemplate.postForEntity(anyString(), any(), eq(CardResponse.class))).willReturn(responseEntity);

        //When
        CardResponse cardResponse = cardService.createCard(request);

        //Then
        assertThat(cardResponse).isNotNull();
        assertThat(cardResponse.getCardAccountId()).isEqualTo(CARD_ID);
        verify(restTemplate).postForEntity(CARDS_URI, request, CardResponse.class);
    }

    @Test
    void shouldSuccessfullyGetBalance() {
        //Given
        String cardId = "123465";
        String balanceUrl = buildUrl(cardId, "/balance");
        CardBalance cardBalance = aValidCardBalance();
        given(restTemplate.getForEntity(anyString(), any())).willReturn(new ResponseEntity<>(cardBalance, HttpStatus.OK));

        //When
        CardBalance result = cardService.getBalance(cardId);

        //Then
        assertThat(result).isEqualTo(cardBalance);
        verify(restTemplate).getForEntity(balanceUrl, CardBalance.class);
    }

    private String buildUrl(String cardId, String endpoint) {
        return CARDS_URI + "/" + cardId + endpoint;
    }
}
