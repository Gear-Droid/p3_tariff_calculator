package ru.fastdelivery.domain.common.coordinates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class Coordinate<T extends Coordinate<T>> {

    protected final BigDecimal value;

    public Coordinate(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Coordinate can't be null!");
        }

        this.value = value.setScale(6, RoundingMode.HALF_UP);
    }

    public Double toDouble() {
        return value.doubleValue();
    }

    public int compareTo(T coordinate) {
        return value.compareTo(coordinate.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate<?> that = (Coordinate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
