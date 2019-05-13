package uk.gov.dhsc.htbhf.card.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.dhsc.htbhf.card.model.CardRequestDTO;
import uk.gov.dhsc.htbhf.card.model.CardResponse;
import uk.gov.dhsc.htbhf.card.model.DepositFundsRequestDTO;
import uk.gov.dhsc.htbhf.card.model.DepositFundsResponse;

@Service
@Slf4j
public class CardService {

    private static final String ENDPOINT = "/v1/cards";
    private final String uri;
    private final RestTemplate restTemplate;

    public CardService(@Value("${card-service.base-uri}") String baseUri,
                       RestTemplate restTemplate) {
        this.uri = baseUri + ENDPOINT;
        this.restTemplate = restTemplate;
    }

    /**
     * Creates a card for the given claimant detailed in the request object.
     *
     * @param cardRequest The request
     * @return The card response
     */
    public CardResponse createCard(CardRequestDTO cardRequest) {
        ResponseEntity<CardResponse> response = restTemplate.postForEntity(uri, cardRequest, CardResponse.class);
        return response.getBody();
    }

    /**
     * Call through to the card service provider to deposit funds to the card.
     *
     * @param cardId              The card id
     * @param depositFundsRequest The deposit funds request
     * @return The response
     */
    public DepositFundsResponse depositFunds(String cardId, DepositFundsRequestDTO depositFundsRequest) {
        String cardUrl = buildCardUrl(cardId);
        ResponseEntity<DepositFundsResponse> response = restTemplate.postForEntity(cardUrl, depositFundsRequest, DepositFundsResponse.class);
        return response.getBody();
    }

    private String buildCardUrl(String cardId) {
        return uri + "/" + cardId + "/deposit";
    }
}
