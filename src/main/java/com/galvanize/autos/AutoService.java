package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    public AutosList getAllAutos() {
        return null;
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