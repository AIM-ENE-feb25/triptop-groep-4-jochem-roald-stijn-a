package com.example.demo;

public class ClimateControl {
    private final Cooling cooling;
    private final Heating heating;
    private int temperature;

    public ClimateControl(Cooling cooling, Heating heating) {
        this.cooling = cooling;
        this.heating = heating;
    }

    public void setTemperature(int temperature) {
        if (temperature < this.temperature) {
            cooling.coolToTemperature(temperature);
        } else {
            heating.heatToTemperature(temperature);
        }
        this.temperature = temperature;
    }
}
