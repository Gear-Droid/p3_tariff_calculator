package ru.fastdelivery;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.fastdelivery.config.Config;

@SpringBootTest(classes = Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractSpringTest {

    public static RedisContainer REDIS_CONTAINER;

    static {
        DockerImageName redis = DockerImageName.parse("redis:7.0.12");

        REDIS_CONTAINER = new RedisContainer(redis)
                .withExposedPorts(6379)
                .withReuse(true);

        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }
}
