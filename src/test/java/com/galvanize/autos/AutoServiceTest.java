package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {
    private AutoService autoService;

    @Mock
    AutosRepository autosRepository;

    @BeforeEach
    void setup() {
        autoService = new AutoService(autosRepository);
    }

    @Test
    void getAllAutos() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.findAll()).thenReturn(Arrays.asList(testAuto));
        AutosList autosList = autoService.getAllAutos();
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
        assertEquals("red", autosList.getAutosList().get(0).getColor());
    }

    @Test
    void testGetAllAutosByColorAndMake() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.findByColorContainsAndMakeContains(anyString(), anyString())).thenReturn(Arrays.asList(testAuto));
        AutosList autosList = autoService.getAllAutos("red", "Honda");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
        assertEquals("red", autosList.getAutosList().get(0).getColor());
    }

    @Test
    void getAllAutosByColor() {
    }

    @Test
    void getAllAutosByMake() {
    }

    @Test
    void addAuto() {
    }

    @Test
    void getAuto() {
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}