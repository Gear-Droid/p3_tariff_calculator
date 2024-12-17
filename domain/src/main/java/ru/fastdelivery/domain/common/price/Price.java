package ru.fastdelivery.domain.common.price;

import ru.fastdelivery.domain.common.currency.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Цена
 *
 * @param amount   значение цены
 * @param currency валюта цены
 */
public record Price(
        BigDecimal amount,
        Currency currency
) implements Serializable {
    public Price {
        if (amount == null || isLessThanZero(amount)) {
            throw new IllegalArgumentException("Price Amount cannot be null or below Zero!");
        }

        if (currency == null) {
            throw new IllegalArgumentException("Price Currency cannot be null!");
        }

        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    private static boolean isLessThanZero(BigDecimal price) {
        return BigDecimal.ZERO.compareTo(price) > 0;
    }

    public Price multiply(BigDecimal amount) {
        return multiply(amount, RoundingMode.HALF_UP);
    }

    public Price multiply(BigDecimal amount, RoundingMode rm) {
        return new Price(
                this.amount
                        .multiply(amount)
                        .setScale(2, rm),
                this.currency
        );
    }

    public Price max(Price price) {
        if (!currency.equals(price.currency)) {
            throw new IllegalArgumentException("Cannot compare Prices in difference Currency!");
        }

        return new Price(
                this.amount
                        .max(price.amount),
                this.currency
        );
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(amount, currency);
    }
}
