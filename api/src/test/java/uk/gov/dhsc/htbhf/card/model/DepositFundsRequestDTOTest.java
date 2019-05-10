package uk.gov.dhsc.htbhf.card.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aDepositFundsRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aDepositFundsRequestWithReference;
import static uk.gov.dhsc.htbhf.card.testhelper.DepositFundsRequestDTOTestDataFactory.aValidDepositFundsRequest;

class DepositFundsRequestDTOTest extends AbstractValidationTest {

    @Test
    void shouldValidateTransferRequestSuccessfully() {
        DepositFundsRequestDTO depositFundsRequest = aValidDepositFundsRequest();

        Set<ConstraintViolation<DepositFundsRequestDTO>> violations = validator.validate(depositFundsRequest);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateWithNoAmount() {
        DepositFundsRequestDTO depositFundsRequest = aDepositFundsRequestWithAmount(null);

        Set<ConstraintViolation<DepositFundsRequestDTO>> violations = validator.validate(depositFundsRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "amountInPence");
    }

    @Test
    void shouldFailToValidateWithNoReference() {
        DepositFundsRequestDTO depositFundsRequest = aDepositFundsRequestWithReference(null);

        Set<ConstraintViolation<DepositFundsRequestDTO>> violations = validator.validate(depositFundsRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "reference");
    }
}
