package uk.gov.dhsc.htbhf.card.model;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.assertions.AbstractValidationTest;

import java.util.Set;
import javax.validation.ConstraintViolation;

import static uk.gov.dhsc.htbhf.assertions.ConstraintViolationAssert.assertThat;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aTransferRequestWithAmount;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aTransferRequestWithReference;
import static uk.gov.dhsc.htbhf.card.testhelper.TransferRequestDTOTestDataFactory.aValidTransferRequest;

class TransferRequestDTOTest extends AbstractValidationTest {

    @Test
    void shouldValidateTransferRequestSuccessfully() {
        TransferRequestDTO transferRequest = aValidTransferRequest();

        Set<ConstraintViolation<TransferRequestDTO>> violations = validator.validate(transferRequest);

        assertThat(violations).hasNoViolations();
    }

    @Test
    void shouldFailToValidateWithNoAmount() {
        TransferRequestDTO transferRequest = aTransferRequestWithAmount(null);

        Set<ConstraintViolation<TransferRequestDTO>> violations = validator.validate(transferRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "amount");
    }

    @Test
    void shouldFailToValidateWithNoReference() {
        TransferRequestDTO transferRequest = aTransferRequestWithReference(null);

        Set<ConstraintViolation<TransferRequestDTO>> violations = validator.validate(transferRequest);

        assertThat(violations).hasSingleConstraintViolation("must not be null", "reference");
    }
}
