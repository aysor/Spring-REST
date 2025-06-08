package ru.netology.springrest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    private static final GenericContainer<?> myAppDev = new GenericContainer<>("devapp").withExposedPorts(8080);
    /*new GenericContainer<>(
            new ImageFromDockerfile().withDockerfile(Paths.get("./Dockerfile")))
            .withExposedPorts(8080);*/
    private static final GenericContainer<?> myProdApp = new GenericContainer<>("prodapp").withExposedPorts(8081);
            /*new GenericContainer<>(
                    new ImageFromDockerfile().withDockerfile(Paths.get("./Dockerfile")))
                    .withExposedPorts(8081);*/

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        myAppDev.start();
        myProdApp.start();
    }

    @Test
    @Order(1)
    void registerTest(){
        String url = "http://localhost:" + myProdApp.getMappedPort(8081) + "/register";
        User testUser = new User("Ivan", "qwrttyqwertty", List.of(Authorities.READ, Authorities.WRITE));
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url, testUser, String.class);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
    }

    @Test
    @Order(2)
    void contextLoads() {
        String host = "http://localhost:";
        Integer port = myProdApp.getMappedPort(8081);
        String request = "/authorize?user=Ivan&password=qwrttyqwertty";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(host + port + request, String.class);
        assertEquals("[\"READ\",\"WRITE\"]", forEntity.getBody());
    }

}