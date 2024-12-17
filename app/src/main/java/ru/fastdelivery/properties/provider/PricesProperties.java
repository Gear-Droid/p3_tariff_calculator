package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Настройки базовых цен стоимости перевозки для различных валют
 */
@Getter
@Setter
@ConfigurationProperties
public class PricesProperties {
    @NotNull
    @Size(min = 1)
    private Map<String, PriceProperties> costs;
}
