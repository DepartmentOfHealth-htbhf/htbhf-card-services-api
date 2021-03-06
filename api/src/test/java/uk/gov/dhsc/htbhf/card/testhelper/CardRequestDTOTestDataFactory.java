package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.AddressDTO;
import uk.gov.dhsc.htbhf.card.model.CardRequestDTO;

import java.time.LocalDate;
import java.util.UUID;

import static uk.gov.dhsc.htbhf.TestConstants.*;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.aValidAddress;

public class CardRequestDTOTestDataFactory {

    public static CardRequestDTO aValidCardRequest() {
        return defaultCardRequestBuilder().build();
    }

    public static CardRequestDTO aCardRequestWithAddress(AddressDTO address) {
        return defaultCardRequestBuilder()
                .address(address)
                .build();
    }

    public static CardRequestDTO aCardRequestWithFirstName(String firstName) {
        return defaultCardRequestBuilder()
                .firstName(firstName)
                .build();
    }

    public static CardRequestDTO aCardRequestWithLastName(String lastName) {
        return defaultCardRequestBuilder()
                .lastName(lastName)
                .build();
    }

    public static CardRequestDTO aCardRequestWithDateOfBirth(LocalDate dateOfBirth) {
        return defaultCardRequestBuilder()
                .dateOfBirth(dateOfBirth)
                .build();
    }

    public static CardRequestDTO aCardRequestWithClaimId(String claimId) {
        return defaultCardRequestBuilder()
                .claimId(claimId)
                .build();
    }

    public static CardRequestDTO aCardRequestWithEmail(String email) {
        return defaultCardRequestBuilder()
                .email(email)
                .build();
    }

    public static CardRequestDTO aCardRequestWithMobile(String mobile) {
        return defaultCardRequestBuilder()
                .mobile(mobile)
                .build();
    }

    private static CardRequestDTO.CardRequestDTOBuilder defaultCardRequestBuilder() {
        return CardRequestDTO.builder()
                .claimId(UUID.randomUUID().toString())
                .firstName(HOMER_FORENAME)
                .lastName(SIMPSON_SURNAME)
                .dateOfBirth(HOMER_DATE_OF_BIRTH)
                .email(HOMER_EMAIL)
                .mobile(HOMER_MOBILE)
                .address(aValidAddress());
    }
}
