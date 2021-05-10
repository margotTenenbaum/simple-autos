package com.galvanize.autos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AutosController.class)
class AutosControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService autoService;


    @Test
    public void getAutosReturnsListOfAutos() throws Exception {
        List<Auto> testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList.add(new Auto("red", "Honda", "Civic", 2000 + i, "XX89DM"));
        }
        when(autoService.getAllAutos()).thenReturn(new AutosList(testList));
        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autosList", hasSize(5)));
    }

    @Test
    public void getAutosReturnsNoContent() throws Exception {
        when(autoService.getAllAutos()).thenReturn(new AutosList());

        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAutosWithParamsReturnListOfAutos() throws Exception {
        List<Auto> testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList.add(new Auto("red", "Honda", "Civic", 2000 + i, "XX89DM"));
        }
        when(autoService.getAllAutos(anyString(),anyString())).thenReturn(new AutosList(testList));
        mockMvc.perform(get("/api/autos?color=red&make=Honda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autosList", hasSize(5)));
    }

    @Test
    public void getAutosWithColorParamReturnsListOfAutos() throws Exception {
        List<Auto> testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList.add(new Auto("red", "Honda", "Civic", 2000 + i, "XX89DM"));
        }
        when(autoService.getAllAutosByColor(anyString())).thenReturn(new AutosList(testList));
        mockMvc.perform(get("/api/autos?color=red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autosList", hasSize(5)));
    }

    @Test
    public void getAutosWithMakeParamReturnsListOfAutos() throws Exception {
        List<Auto> testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList.add(new Auto("red", "Honda", "Civic", 2000 + i, "XX89DM"));
        }
        when(autoService.getAllAutosByMake(anyString())).thenReturn(new AutosList(testList));
        mockMvc.perform(get("/api/autos?make=Honda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autosList", hasSize(5)));
    }

}