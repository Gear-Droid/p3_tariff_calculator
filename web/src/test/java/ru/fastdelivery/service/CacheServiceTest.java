package ru.fastdelivery.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import ru.fastdelivery.AbstractSpringTest;
import ru.fastdelivery.domain.common.coordinates.Coordinates;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CacheServiceTest extends AbstractSpringTest {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private CacheManager distancePriceCacheManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void before() {
        redisTemplate.getConnectionFactory()
                .getConnection()
                .commands()
                .flushAll();
    }

    @Test
    void givenRedisContainer_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertTrue(REDIS_CONTAINER.isRunning());
    }

    @Test
    public void whenCalculatePriceByDistance_thenCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        var mockedShipment = mock(Shipment.class);
        var mockedBasePrice = mock(Price.class);
        var mockedUseCase = mock(TariffCalculateUseCase.class);
        var mockedTotalPrice = mock(Price.class);
        var mockedCoordinates = mock(Coordinates.class);

        when(mockedUseCase.calculatePriceByDistance(mockedShipment, mockedBasePrice))
                .thenReturn(mockedTotalPrice);
        when(mockedShipment.destination()).thenReturn(mockedCoordinates);
        when(mockedShipment.departure()).thenReturn(mockedCoordinates);
        when(mockedCoordinates.toString()).thenReturn("[50.000111;60.000111]");
        when(mockedBasePrice.toString()).thenReturn("111(RUB)");

        var actual = cacheService.calculatePriceByDistance(mockedShipment, mockedBasePrice, mockedUseCase);

        assertEquals(mockedTotalPrice, actual);
        assertFalse(redisTemplate.keys("*").isEmpty());
    }
}
