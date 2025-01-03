package ru.fastdelivery.domain.common.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * Валюта для стоимости
 */
@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Currency implements Serializable {
    String code;

    @Override
    public String toString() {
        return code;
    }
}
