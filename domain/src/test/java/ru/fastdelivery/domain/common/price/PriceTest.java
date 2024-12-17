package ru.fastdelivery.domain.common.price;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.fastdelivery.domain.Config;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    private final Currency currency = Config.getTestCurrency();

    @Test
    @DisplayName("Значение null -> исключение")
    void whenAmountIsNull_thenException() {
        assertThatThrownBy(() -> new Price(null, currency))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Валюта null -> исключение")
    void whenCurrencyIsNull_thenException() {
        var amount = BigDecimal.valueOf(1);
        assertThatThrownBy(() -> new Price(amount, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Значение меньше нуля -> исключение")
    void whenAmountBelowZero_thenException() {
        var amount = BigDecimal.valueOf(-1);
        assertThatThrownBy(() -> new Price(amount, currency))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"0.000, 0.00",
            "0.001, 0.00",
            "0.005, 0.01",
            "1.999, 2.00"})
    void testAmountScale(BigDecimal amount, BigDecimal expected) {
        var actual = new Price(amount, currency).amount();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Умножение -> корректный результат")
    void multiply() {
        var price = new Price(BigDecimal.valueOf(10), currency);
        var pieces = new BigDecimal("2.54");
        var expected = new Price(BigDecimal.valueOf(25.4), currency);

        var actualPrice = price.multiply(pieces);

        assertThat(actualPrice.amount()).isEqualByComparingTo(expected.amount());
        assertThat(actualPrice.currency()).isEqualTo(expected.currency());
    }

    @Test
    @DisplayName("Умножение с методом округления -> корректный результат")
    void whenMultiplyWithRoundingMode_thenResult() {
        var price = new Price(BigDecimal.valueOf(1), currency);
        var pieces = new BigDecimal("2.541");
        var expected = new Price(BigDecimal.valueOf(2.55), currency);

        var actualPrice = price.multiply(pieces, RoundingMode.UP);

        assertThat(actualPrice.amount()).isEqualByComparingTo(expected.amount());
        assertThat(actualPrice.currency()).isEqualTo(expected.currency());
    }

    @Test
    @DisplayName("Максимум -> корректный результат")
    void max() {
        var price = new Price(BigDecimal.valueOf(10), currency);
        var moreThanPrice = new Price(BigDecimal.valueOf(100), currency);

        var actualMax = price.max(moreThanPrice);

        assertThat(actualMax).isEqualTo(moreThanPrice);
    }

    @Test
    @DisplayName("Максимум с разными валютами -> исключение")
    void maxWithDifferentCurrency_thenException() {
        var price = new Price(
                BigDecimal.valueOf(10),
                new CurrencyFactory(code -> true).create("RUB")
        );
        var moreThanPrice = new Price(
                BigDecimal.valueOf(100),
                new CurrencyFactory(code -> true).create("USD")
        );

        assertThatThrownBy(() -> price.max(moreThanPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }
}