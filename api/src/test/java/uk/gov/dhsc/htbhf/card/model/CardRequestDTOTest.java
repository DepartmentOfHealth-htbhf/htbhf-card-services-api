package uk.gov.dhsc.htbhf.card.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithAddressLine1;
import static uk.gov.dhsc.htbhf.card.testhelper.CardRequestDTOTestDataFactory.*;

class CardRequestDTOTest extends AbstractValidationTest {

    @Test
    void shouldValidCardSuccessfully() {
        CardRequestDTO cardRequest = aValidCardRequest();

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateWithNoFirstName() {
        CardRequestDTO cardRequest = aCardRequestWithFirstName(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "firstName");
    }

    @Test
    void shouldFailToValidateWithNoLastName() {
        CardRequestDTO cardRequest = aCardRequestWithLastName(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "lastName");
    }

    @Test
    void shouldFailToValidateWithNoDateOfBirth() {
        CardRequestDTO cardRequest = aCardRequestWithDateOfBirth(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "dateOfBirth");
    }

    @Test
    void shouldFailToValidateWithDateOfBirthInTheFuture() {
        CardRequestDTO cardRequest = aCardRequestWithDateOfBirth(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must be a past date", "dateOfBirth");
    }

    @Test
    void shouldFailToValidateWithNoClaimId() {
        CardRequestDTO cardRequest = aCardRequestWithClaimId(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "claimId");
    }

    @Test
    void shouldFailToValidateWithNoAddress() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "address");
    }

    @Test
    void shouldFailToValidateWithNoAddressLine1() {
        CardRequestDTO cardRequest = aCardRequestWithAddress(anAddressWithAddressLine1(null));

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "address.addressLine1");
    }

    @Test
    void shouldSuccessfullyValidateWithNoEmail() {
        CardRequestDTO cardRequest = aCardRequestWithEmail(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldSuccessfullyValidateWithNoMobile() {
        CardRequestDTO cardRequest = aCardRequestWithMobile(null);

        Set<ConstraintViolation<CardRequestDTO>> violations = validator.validate(cardRequest);

        assertThat(violations).hasNoViolations();
    }
}
