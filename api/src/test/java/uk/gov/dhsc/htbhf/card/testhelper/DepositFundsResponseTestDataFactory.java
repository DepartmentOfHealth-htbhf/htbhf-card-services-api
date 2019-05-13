package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.DepositFundsResponse;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.DEPOSIT_REFERENCE_ID;

public class DepositFundsResponseTestDataFactory {

    public static DepositFundsResponse aValidDepositFundsResponse() {
        return DepositFundsResponse.builder().referenceId(DEPOSIT_REFERENCE_ID).build();
    }
}
