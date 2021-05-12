package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return autosRepository.findByVin(vin).orElse(null);
    }

    public Auto updateAuto(String vin, String color, String owner) {
        Optional<Auto> auto = autosRepository.findByVin(vin);
        if(auto.isEmpty()) {
            return null;
        } else {
            auto.get().setColor(color);
            auto.get().setOwner(owner);
            return autosRepository.save(auto.get());
        }
    }

    public void deleteAuto(String vin) {
        Optional<Auto> auto = autosRepository.findByVin(vin);
        if (auto.isPresent()) {
            autosRepository.delete(auto.get());
        } else {
            throw new AutoNotFoundException();
        }
    }
}