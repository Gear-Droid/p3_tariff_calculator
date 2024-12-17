package ru.fastdelivery.domain.common.length;

import lombok.NonNull;

/**
 * Длина
 *
 * @param mm    значение длины в мм
 */
public record Length(Integer mm) {

    public Length {
        if (mm == null || isLessThanZero(mm)) {
            throw new IllegalArgumentException("Length mm cannot be null or below Zero!");
        }
    }

    private static boolean isLessThanZero(Integer value) {
        return value < 0;
    }

    public static Length fromMillimeter(Integer mm) {
        return new Length(mm);
    }

    public boolean isLongerThan(Length l) {
        return mm().compareTo(l.mm()) > 0;
    }

    public Length normalizedTo(int norm) {
        int whole = mm / norm;
        int remainder = mm % norm != 0 ? 1 : 0;
        return new Length((whole + remainder) * norm);
    }

}
