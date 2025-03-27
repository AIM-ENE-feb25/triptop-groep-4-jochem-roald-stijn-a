package com.example.demo;

public class SmartHomeSystem {
    private final ClimateControl climateControl;
    private final MusicSystem musicSystem;
    private final Lightning lightning;

    public SmartHomeSystem(ClimateControl climateControl, MusicSystem musicSystem, Lightning lightning) {
        this.climateControl = climateControl;
        this.musicSystem = musicSystem;
        this.lightning = lightning;
    }

    public void returnFromWork() {
        System.out.println("SmartHome: fixing house...");
        climateControl.setTemperature(19);
        musicSystem.on();
        musicSystem.playMusic();
        lightning.on();
        lightning.dim();
    }

    public void goToBed() {
        System.out.println("SmartHome: turning off systems...");
        climateControl.setTemperature(17);
        musicSystem.off();
        lightning.off();
    }
}
