package com.galvanize.autos;

public class UpdateAuto {
    private String color;
    private String owner;

    public UpdateAuto() {

    }

    public UpdateAuto(String color, String owner) {
        this.color = color;
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}