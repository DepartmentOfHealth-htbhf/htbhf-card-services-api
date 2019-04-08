package uk.gov.dhsc.htbhf.card.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.dhsc.htbhf.card.model.CardBalance;
import uk.gov.dhsc.htbhf.card.model.CardDTO;
import uk.gov.dhsc.htbhf.card.model.TransferRequestDTO;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aValidCard;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aValidTransferRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerTest {

    private static final URI ENDPOINT = URI.create("/v1/cards");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateCard() {
        CardDTO card = aValidCard();

        ResponseEntity<Void> response = restTemplate.postForEntity(ENDPOINT, card, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    void shouldTransferFundsToCard() {
        TransferRequestDTO transferRequest = aValidTransferRequest();

        ResponseEntity<Void> response = restTemplate.postForEntity(ENDPOINT + "/1/transfer", transferRequest, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldGetCardBalance() {
        ResponseEntity<CardBalance> response = restTemplate.getForEntity(ENDPOINT + "/1/balance", CardBalance.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }
}
