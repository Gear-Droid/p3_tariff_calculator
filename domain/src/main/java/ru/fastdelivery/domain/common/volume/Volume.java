package ru.fastdelivery.domain.common.volume;

import ru.fastdelivery.domain.common.length.Length;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Объем
 *
 * @param mm3   значение объема в мм2
 */
public record Volume(BigInteger mm3) {

    public Volume {
        if (mm3 == null || isLessThanZero(mm3)) {
            throw new IllegalArgumentException("Volume mm3 cannot be null or below Zero!");
        }
    }

    private boolean isLessThanZero(BigInteger value) {
        return BigInteger.ZERO.compareTo(value) > 0;
    }

    public static Volume zero() {
        return new Volume(BigInteger.ZERO);
    }

    public BigDecimal convertToM3(int scale) {
        return new BigDecimal(mm3())
                .divide(new BigDecimal("1000000000"), scale, RoundingMode.HALF_UP);
    }

    public Volume add(Volume additionalVolume) {
        return new Volume(this.mm3.add(additionalVolume.mm3));
    }

    public static Volume calculate(Length l, Length w, Length h) {
        BigInteger mm3 = BigInteger.valueOf(l.mm())
                .multiply(BigInteger.valueOf(w.mm()))
                .multiply(BigInteger.valueOf(h.mm()));
        return new Volume(mm3);
    }

}
