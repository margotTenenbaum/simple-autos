package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void testGetAllAutosByColor() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.findByColorContains(anyString())).thenReturn(Arrays.asList(testAuto));
        AutosList autosList = autoService.getAllAutosByColor("red");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
        assertEquals("red", autosList.getAutosList().get(0).getColor());
    }

    @Test
    void testGetAllAutosByMake() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.findByMakeContains(anyString())).thenReturn(Arrays.asList(testAuto));
        AutosList autosList = autoService.getAllAutosByMake("Honda");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
        assertEquals("Honda", autosList.getAutosList().get(0).getMake());
    }

    @Test
    void addAuto_valid_returnsAutoAdded() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.save(any(Auto.class))).thenReturn(testAuto);
        Auto auto = autoService.addAuto(testAuto);
        assertThat(auto.getMake()).isEqualTo("Honda");
    }

    @Test
    void getAutoByVin_withValidVin() {
        Auto testAuto = new Auto("red", "Honda", "Civic", 2000, "XX89DM");
        when(autosRepository.findByVin(anyString())).thenReturn(java.util.Optional.of(testAuto));
        Auto auto = autoService.getAuto("XX89DM");
        assertThat(auto).isNotNull();
        assertEquals(auto.getVin(), "XX89DM");
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}