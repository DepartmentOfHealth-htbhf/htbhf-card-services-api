package uk.gov.dhsc.htbhf.card.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithAddressLine1;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithClaimId;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithDateOfBirth;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithEmail;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithFirstName;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithLastName;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aCardWithMobile;
import static uk.gov.dhsc.htbhf.card.testhelper.CardDTOTestDataFactory.aValidCard;

class CardDTOTest extends AbstractValidationTest {

    @Test
    void shouldValidCardSuccessfully() {
        var card = aValidCard();

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateWithNoFirstName() {
        var card = aCardWithFirstName(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "firstName");
    }

    @Test
    void shouldFailToValidateWithNoLastName() {
        var card = aCardWithLastName(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "lastName");
    }

    @Test
    void shouldFailToValidateWithNoDateOfBirth() {
        var card = aCardWithDateOfBirth(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "dateOfBirth");
    }

    @Test
    void shouldFailToValidateWithDateOfBirthInTheFuture() {
        var card = aCardWithDateOfBirth(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must be a past date", "dateOfBirth");
    }

    @Test
    void shouldFailToValidateWithNoClaimId() {
        var card = aCardWithClaimId(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "claimId");
    }

    @Test
    void shouldFailToValidateWithNoAddress() {
        var card = aCardWithAddress(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "address");
    }

    @Test
    void shouldFailToValidateWithNoAddressLine1() {
        var card = aCardWithAddress(anAddressWithAddressLine1(null));

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "address.addressLine1");
    }

    @Test
    void shouldSuccessfullyValidateWithNoEmail() {
        var card = aCardWithEmail(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldSuccessfullyValidateWithNoMobile() {
        var card = aCardWithMobile(null);

        Set<ConstraintViolation<CardDTO>> violations = validator.validate(card);

        assertThat(violations).hasNoViolations();
    }
}
