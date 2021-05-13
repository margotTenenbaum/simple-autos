package com.galvanize.autos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestPropertySource(locations= "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutosApplicationTests {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    AutosRepository autosRepository;

    Random r = new Random();
    List<Auto> testAutos;
    @BeforeEach
    void setup() {
        testAutos = new ArrayList<>();
        Auto auto;
        String[] colors = {"red", "yellow", "blue", "green", "orange", "black", "white", "silver"};
        String[] makes = {"Ford", "Tesla", "Honda", "Toyota", "Audi", "BMW", "Nissan", "Chevrolet"};
        String[] models = {"Explorer", "Y", "Civic", "Corolla", "A3", "M3", "Altima", "Camaro"};
        int[] years = {2001, 2003, 2012, 2014, 2015, 2019, 2020, 2021};

        for (int i = 0; i < 50; i++) {
            int random = r.nextInt(8);
            auto = new Auto(colors[r.nextInt(8)], makes[random], models[random], years[r.nextInt(8)], "AABBCC" + i);
            testAutos.add(auto);
        }
        autosRepository.saveAll(testAutos);
    }

    @AfterEach
    void tearDown() {
        autosRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getAutos_returnsListOfAutos_whenExists() {
        ResponseEntity<AutosList> response = testRestTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isEmpty()).isFalse();
    }

    @Test
    void getAutos_returnsEmptyListOfAutos_whenNoneExist() {
        autosRepository.deleteAll();
        ResponseEntity<AutosList> response = testRestTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getAutos_withSearch_returnsAutosList() {
        int random = r.nextInt(50);
        String color = testAutos.get(random).getColor();
        String make = testAutos.get(random).getMake();

        ResponseEntity<AutosList> response = testRestTemplate.getForEntity(String.format("/api/autos?color=%s&make=%s", color, make), AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEmpty()).isFalse();
        assertThat(response.getBody().getAutosList().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void getAutos_withSearch_returnsEmptyAutosListWhenNoneFound() {
        int random = r.nextInt(50);
        String color = "purple";
        String make = testAutos.get(random).getMake();

        ResponseEntity<AutosList> response = testRestTemplate.getForEntity(String.format("/api/autos?color=%s&make=%s", color, make), AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void addAutos_returnsNewAutoAdded() {
        Auto auto = new Auto("red", "Tesla", "X", 2018, "AABBCD");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Auto> request = new HttpEntity<>(auto, headers);

        ResponseEntity<Auto> response = testRestTemplate.postForEntity("/api/autos", request, Auto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getVin()).isEqualTo(auto.getVin());
    }

    @Test
    void getAutos_byVin_returnsAutoWhenValidVin() {
        ResponseEntity<Auto> response = testRestTemplate.getForEntity("/api/autos/AABBCC1", Auto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getVin()).isEqualTo("AABBCC1");
    }

    @Test
    void getAutos_byVin_returnsNoContentWhenInvalidVin() {
        ResponseEntity<Auto> response = testRestTemplate.getForEntity("/api/autos/AABBCC51", Auto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void updateAuto_returnsUpdatedAuto() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
        UpdateAuto updateAuto = new UpdateAuto("purple", "Elon");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<UpdateAuto> request = new HttpEntity<>(updateAuto, headers);

        ResponseEntity<Auto> response = testRestTemplate.exchange("/api/autos/AABBCC1", HttpMethod.PATCH, request, Auto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getOwner()).isEqualTo(updateAuto.getOwner());
    }

    @Test
    void updateAuto_returnsNoContentForInvalidVIN() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
        UpdateAuto updateAuto = new UpdateAuto("purple", "Elon");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<UpdateAuto> request = new HttpEntity<>(updateAuto, headers);

        ResponseEntity<Auto> response = testRestTemplate.exchange("/api/autos/AABBCC51", HttpMethod.PATCH, request, Auto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updateAuto_returnsBadRequestForInvalidBody() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> request = new HttpEntity<>("anything", headers);

        ResponseEntity<Auto> response = testRestTemplate.exchange("/api/autos/AABBCC1", HttpMethod.PATCH, request, Auto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteAuto_returnAcceptedIfDeleted() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
        ResponseEntity response = testRestTemplate.exchange("/api/autos/AABBCC1", HttpMethod.DELETE, new HttpEntity(""), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void deleteAuto_returnNoContentIfNotFound() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        testRestTemplate.getRestTemplate().setRequestFactory(requestFactory);
        ResponseEntity response = testRestTemplate.exchange("/api/autos/AABBCC51", HttpMethod.DELETE, new HttpEntity(""), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
