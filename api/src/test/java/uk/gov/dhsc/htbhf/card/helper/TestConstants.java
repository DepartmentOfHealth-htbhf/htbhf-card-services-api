package uk.gov.dhsc.htbhf.card.helper;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TestConstants {

    public static final String HOMER_FIRST_NAME = "Homer";
    public static final String SIMPSON_LAST_NAME = "Simpson";
    public static final LocalDate HOMER_DOB = LocalDate.parse("1985-12-31");
    public static final String HOMER_EMAIL = "homer@simpson.com";
    public static final String HOMER_MOBILE = "07700900000";

    public static final String SIMPSONS_ADDRESS_LINE_1 = "742 Evergreen Terrace";
    public static final String SIMPSONS_ADDRESS_LINE_2 = "Mystery Spot";
    public static final String SIMPSONS_TOWN = "Springfield";
    public static final String SIMPSONS_POSTCODE = "AA1 1AA";

    public static final BigDecimal TRANSFER_AMOUNT = BigDecimal.valueOf(12.40);
}
