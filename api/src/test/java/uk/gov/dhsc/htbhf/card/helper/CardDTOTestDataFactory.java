package uk.gov.dhsc.htbhf.card.helper;

import uk.gov.dhsc.htbhf.card.model.CardDTO;

import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.helper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.card.helper.TestConstants.HOMER_DOB;
import static uk.gov.dhsc.htbhf.card.helper.TestConstants.HOMER_EMAIL;
import static uk.gov.dhsc.htbhf.card.helper.TestConstants.HOMER_FIRST_NAME;
import static uk.gov.dhsc.htbhf.card.helper.TestConstants.HOMER_MOBILE;
import static uk.gov.dhsc.htbhf.card.helper.TestConstants.SIMPSON_LAST_NAME;

public class CardDTOTestDataFactory {

    public static CardDTO aValidCard() {
        return CardDTO.builder()
                .claimId(UUID.randomUUID().toString())
                .firstName(HOMER_FIRST_NAME)
                .lastName(SIMPSON_LAST_NAME)
                .dateOfBirth(HOMER_DOB)
                .email(HOMER_EMAIL)
                .mobile(HOMER_MOBILE)
                .address(aValidAddress())
                .build();
    }
}
