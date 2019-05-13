package uk.gov.dhsc.htbhf.card.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.card.model.DepositFundsRequestDTO;
import uk.gov.dhsc.htbhf.card.model.DepositFundsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsResponseTestDataFactory.aValidDepositFundsResponse;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.DEPOSIT_REFERENCE_ID;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    private static final String BASE_URI = "http://localhost:8120";

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
        String cardUrl = buildCardUrl(cardId);
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

    private String buildCardUrl(String cardId) {
        return BASE_URI + "/v1/cards/" + cardId + "/deposit";
    }
}
