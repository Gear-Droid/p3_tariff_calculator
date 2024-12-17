package ru.fastdelivery.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {

    private List<String> cacheNames = new ArrayList<>();
    private Map<String, CacheProperties> caches = new HashMap<>();

    @Data
    public static class CacheProperties {
        private Duration expiry = Duration.ZERO;
    }

    public interface CacheNames {
        String PRICE_BY_COORDINATES_AND_BASE_PRICE = "priceByCoordinatesAndBasePrice";
    }
}
