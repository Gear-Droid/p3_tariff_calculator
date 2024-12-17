package ru.fastdelivery.properties.provider;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;

import java.util.List;

/**
 * Настройки валют из конфига
 */
@Validated
@Configuration
@ConfigurationProperties("currency")
@Getter
@Setter
public class CurrencyProperties implements CurrencyPropertiesProvider {
    @NotNull
    @Size(min = 1)
    private List<String> available;

    @Override
    public boolean isAvailable(String code) {
        return available.contains(code);
    }
}
