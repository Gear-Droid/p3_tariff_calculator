package ru.fastdelivery.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import ru.fastdelivery.properties.AppCacheProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration {

    private void clearCacheOnStart(LettuceConnectionFactory lettuceConnectionFactory) {
        lettuceConnectionFactory.getConnection()
                .commands()
                .flushAll();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.redis", name = "enable", havingValue = "true")
    public CacheManager distancePriceCacheManager(
            AppCacheProperties appCacheProperties,
            LettuceConnectionFactory lettuceConnectionFactory) {
        clearCacheOnStart(lettuceConnectionFactory);

        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        appCacheProperties.getCacheNames()
                .forEach(cacheName ->
                        redisCacheConfigurationMap.put(
                                cacheName,
                                defaultConfig.entryTtl(appCacheProperties.getCaches().get(cacheName)
                                                .getExpiry())));

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }
}
