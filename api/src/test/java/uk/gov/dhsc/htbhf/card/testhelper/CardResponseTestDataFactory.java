package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.CardResponse;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.CARD_ID;

public class CardResponseTestDataFactory {

    public static CardResponse aValidCardResponse() {
        return CardResponse.builder().cardAccountId(CARD_ID).build();
    }
}
