package ru.stars.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
public class Star {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Name should not be empty")
    private String constellation;
    private int temperature;
    private double mass;
    private double radius;
    private double luminosity;

    public Star() {

    }

    public Star(int id, String name, String constellation, int temperature, double mass, double radius, double luminosity) {
        this.id = id;
        this.name = name;
        this.constellation = constellation;
        this.temperature = temperature;
        this.mass = mass;
        this.radius = radius;
        this.luminosity = luminosity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }
}
