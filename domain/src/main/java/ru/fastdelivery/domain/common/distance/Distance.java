package ru.fastdelivery.domain.common.distance;

/**
 * Дистанция
 *
 * @param meter значение дистанции в метрах
 */
public record Distance(Integer meter) {
    public Distance {
        if (meter == null || meter < 0) {
            throw new IllegalArgumentException("Distance can't be Null or lower than Zero!");
        }
    }

    public Double toKm() {
        return ((double) meter) / 1000.;
    }
}
