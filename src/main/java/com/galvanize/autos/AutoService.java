package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    AutosRepository autosRepository;

    public AutoService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public AutosList getAllAutos() {
        return new AutosList(autosRepository.findAll());
    }

    public AutosList getAllAutos(String color, String make) {
        return null;
    }

    public AutosList getAllAutosByColor(String color) {
        return null;
    }

    public AutosList getAllAutosByMake(String make) {
        return null;
    }

    public Auto addAuto(Auto newAuto) {
        return null;
    }

    public Auto getAuto(String vin) {
        return null;
    }

    public Auto updateAuto(String vin, String color, String owner) {
        return null;
    }

    public void deleteAuto(String vin) throws AutoNotFoundException {
        return;
    }
}