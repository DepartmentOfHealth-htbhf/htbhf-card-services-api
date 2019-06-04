package uk.gov.dhsc.htbhf.card.testhelper;

import uk.gov.dhsc.htbhf.card.model.CardBalance;

import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.AVAILABLE_BALANCE_IN_PENCE;
import static uk.gov.dhsc.htbhf.card.testhelper.TestConstants.LEDGER_BALANCE_IN_PENCE;

public class CardBalanceTestDataFactory {

    public static CardBalance aValidCardBalance() {
        return CardBalance.builder()
                .availableBalanceInPence(AVAILABLE_BALANCE_IN_PENCE)
                .ledgerBalanceInPence(LEDGER_BALANCE_IN_PENCE)
                .build();
    }
}
