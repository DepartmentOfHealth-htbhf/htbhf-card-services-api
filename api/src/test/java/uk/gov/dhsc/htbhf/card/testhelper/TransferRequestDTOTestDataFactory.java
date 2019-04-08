package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.TransferRequestDTO;

import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.TRANSFER_AMOUNT;

public class TransferRequestDTOTestDataFactory {

    public static TransferRequestDTO aValidTransferRequest() {
        return TransferRequestDTO.builder()
                .amount(TRANSFER_AMOUNT)
                .reference(UUID.randomUUID().toString())
                .build();
    }
}
