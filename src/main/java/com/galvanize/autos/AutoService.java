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
        return new AutosList(autosRepository.findByColorContainsAndMakeContains(color, make));
    }

    public AutosList getAllAutosByColor(String color) {
        return new AutosList(autosRepository.findByColorContains(color));
    }

    public AutosList getAllAutosByMake(String make) {
        return new AutosList(autosRepository.findByMakeContains(make));
    }

    public Auto addAuto(Auto newAuto) {
        return autosRepository.save(newAuto);
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