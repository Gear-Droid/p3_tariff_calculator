package ru.fastdelivery.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ComponentScan(basePackages = { "ru.fastdelivery" })
@EnableAutoConfiguration
@EnableCaching
public class Config {

    public static String getTestCurrencyCode() {
        return "RUB";
    }

    public static Currency getTestCurrency() {
        return new CurrencyFactory(code -> true)
                .create(getTestCurrencyCode());
    }

    @Bean
    public CurrencyFactory currencyFactory() {
        var currencyMock = mock(CurrencyFactory.class);
        when(currencyMock.create(getTestCurrencyCode()))
                .thenReturn(getTestCurrency());

        return currencyMock;
    }

    @Bean
    public Map<String, TariffCalculateUseCase> tariffCalculateUseCases() {
        return Map.of(
                "USD", mock(TariffCalculateUseCase.class),
                getTestCurrencyCode(), mock(TariffCalculateUseCase.class)
        );
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setHostName(AbstractSpringTest.REDIS_CONTAINER.getHost());
        configuration.setPort(AbstractSpringTest.REDIS_CONTAINER.getMappedPort(6379));

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    public CacheManager distancePriceCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        var cacheNames = List.of("priceByCoordinatesAndBasePrice");
        cacheNames.forEach(cacheName ->
                redisCacheConfigurationMap.put(
                        cacheName,
                        defaultConfig.entryTtl(Duration.ofMinutes(10))));

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }
}
