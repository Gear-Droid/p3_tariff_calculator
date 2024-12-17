package ru.fastdelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public abstract class ControllerTest extends AbstractSpringTest {

    @Autowired
    protected TestRestTemplate restTemplate;

}
