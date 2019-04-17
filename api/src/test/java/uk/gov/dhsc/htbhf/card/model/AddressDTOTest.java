package uk.gov.dhsc.htbhf.card.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.aValidAddress;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithAddressLine1;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithAddressLine2;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithPostcode;
import static uk.gov.dhsc.htbhf.card.testhelper.AddressDTOTestDataFactory.anAddressWithTownOrCity;

class AddressDTOTest extends AbstractValidationTest {

    @Test
    void shouldValidateAddressSuccessfully() {
        var address = aValidAddress();

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(address);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateAddressWithNoAddressLine1() {
        var address = anAddressWithAddressLine1(null);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(address);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "addressLine1");
    }

    @Test
    void shouldValidateAddressSuccessfullyWithNoAddressLine2() {
        var address = anAddressWithAddressLine2(null);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(address);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateAddressWithNoTownOrCity() {
        var address = anAddressWithTownOrCity(null);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(address);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "townOrCity");
    }

    @Test
    void shouldFailToValidateAddressWithNoPostcode() {
        var address = anAddressWithPostcode(null);

        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(address);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "postcode");
    }
}