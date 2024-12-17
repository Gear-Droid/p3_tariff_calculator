package ru.fastdelivery.domain;

import ru.fastdelivery.domain.common.currency.Currency;

public class Config {

    public static String getTestCurrencyCode() {
        return "RUB";
    }

    public static Currency getTestCurrency() {
        return TestUtils.createTestCurrency(getTestCurrencyCode(), code -> true);
    }
}
