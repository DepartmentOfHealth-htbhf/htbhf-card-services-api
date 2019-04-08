package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.AddressDTO;
import uk.gov.dhsc.htbhf.card.model.CardDTO;

import java.time.LocalDate;
import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_DATE_OF_BIRTH;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_EMAIL;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_FIRST_NAME;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.HOMER_MOBILE;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.SIMPSON_LAST_NAME;

public class CardDTOTestDataFactory {

    public static CardDTO aValidCard() {
        return defaultCardBuilder().build();
    }

    public static CardDTO aCardWithAddress(AddressDTO address) {
        return defaultCardBuilder()
                .address(address)
                .build();
    }

    public static CardDTO aCardWithFirstName(String firstName) {
        return defaultCardBuilder()
                .firstName(firstName)
                .build();
    }

    public static CardDTO aCardWithLastName(String lastName) {
        return defaultCardBuilder()
                .lastName(lastName)
                .build();
    }

    public static CardDTO aCardWithDateOfBirth(LocalDate dateOfBirth) {
        return defaultCardBuilder()
                .dateOfBirth(dateOfBirth)
                .build();
    }

    public static CardDTO aCardWithClaimId(String claimId) {
        return defaultCardBuilder()
                .claimId(claimId)
                .build();
    }

    public static CardDTO aCardWithEmail(String email) {
        return defaultCardBuilder()
                .email(email)
                .build();
    }

    public static CardDTO aCardWithMobile(String mobile) {
        return defaultCardBuilder()
                .mobile(mobile)
                .build();
    }

    private static CardDTO.CardDTOBuilder defaultCardBuilder() {
        return CardDTO.builder()
                .claimId(UUID.randomUUID().toString())
                .firstName(HOMER_FIRST_NAME)
                .lastName(SIMPSON_LAST_NAME)
                .dateOfBirth(HOMER_DATE_OF_BIRTH)
                .email(HOMER_EMAIL)
                .mobile(HOMER_MOBILE)
                .address(aValidAddress());
    }
}
