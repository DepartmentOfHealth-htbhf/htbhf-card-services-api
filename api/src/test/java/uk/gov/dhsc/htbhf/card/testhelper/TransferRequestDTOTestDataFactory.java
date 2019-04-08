package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.TransferRequestDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.TRANSFER_AMOUNT;

public class TransferRequestDTOTestDataFactory {

    public static TransferRequestDTO aValidTransferRequest() {
        return buildDefaultRequest().build();
    }

    public static TransferRequestDTO aTransferRequestWithAmount(BigDecimal amount) {
        return buildDefaultRequest()
                .amount(amount)
                .build();
    }

    public static TransferRequestDTO aTransferRequestWithReference(String reference) {
        return buildDefaultRequest()
                .reference(reference)
                .build();
    }

    private static TransferRequestDTO.TransferRequestDTOBuilder buildDefaultRequest() {
        return TransferRequestDTO.builder()
                .amount(TRANSFER_AMOUNT)
                .reference(UUID.randomUUID().toString());
    }
}
