package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.CardDTO;

import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_EMAIL;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_FIRST_NAME;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_MOBILE;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.SIMPSON_LAST_NAME;

public class CardDTOTestDataFactory {

    public static CardDTO aValidCard() {
        return CardDTO.builder()
                .claimId(UUID.randomUUID().toString())
                .firstName(HOMER_FIRST_NAME)
                .lastName(SIMPSON_LAST_NAME)
                .dateOfBirth(HOMER_DATE_OF_BIRTH)
                .email(HOMER_EMAIL)
                .mobile(HOMER_MOBILE)
                .address(aValidAddress())
                .build();
    }
}
