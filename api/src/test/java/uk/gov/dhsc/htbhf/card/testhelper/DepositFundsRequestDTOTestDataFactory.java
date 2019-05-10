package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.DepositFundsRequestDTO;

import java.util.UUID;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.TRANSFER_AMOUNT;

public class DepositFundsRequestDTOTestDataFactory {

    public static DepositFundsRequestDTO aValidDepositFundsRequest() {
        return buildDefaultRequest().build();
    }

    public static DepositFundsRequestDTO aDepositFundsRequestWithAmount(Integer amount) {
        return buildDefaultRequest()
                .amountInPence(amount)
                .build();
    }

    public static DepositFundsRequestDTO aDepositFundsRequestWithReference(String reference) {
        return buildDefaultRequest()
                .reference(reference)
                .build();
    }

    private static DepositFundsRequestDTO.DepositFundsRequestDTOBuilder buildDefaultRequest() {
        return DepositFundsRequestDTO.builder()
                .amountInPence(TRANSFER_AMOUNT)
                .reference(UUID.randomUUID().toString());
    }
}
