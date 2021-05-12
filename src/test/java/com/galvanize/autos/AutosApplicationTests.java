package com.galvanize.autos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutosApplicationTests {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    AutosRepository autosRepository;

    List<Auto> testAutos;
    @BeforeEach
    void setup() {
        testAutos = new ArrayList<>();
        Auto auto;
        String[] colors = {"red", "yellow", "blue", "green", "orange", "black", "white", "silver"};
        String[] makes = {"Ford", "Tesla", "Honda", "Toyota", "Audi", "BMW", "Nissan", "Chevrolet"};
        String[] models = {"Explorer", "Y", "Civic", "Corolla", "A3", "M3", "Altima", "Camaro"};
        int[] years = {2001, 2003, 2012, 2014, 2015, 2019, 2020, 2021};
        Random r = new Random();
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


}
