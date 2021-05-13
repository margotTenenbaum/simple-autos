package com.galvanize.autos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

@Entity
@Table(name = "autos")
public class Auto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private String make;
    private String model;
    private String owner;
    private int year;
    @Column(unique = true)
    private String vin;

    public Auto() {

    }

    public Auto(String color, String make, String model, int year, String vin) {
        this.color = color;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}