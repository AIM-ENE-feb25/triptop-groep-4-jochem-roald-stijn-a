package com.example.demo;

public class Person {
    private final SmartHomeSystem smartHomeSystem;

    public Person(SmartHomeSystem smartHomeSystem) {
        this.smartHomeSystem = smartHomeSystem;
    }

    public void returnFromWork() {
        System.out.println();
        System.out.println("--- Person: Back from work, go fix the house! ---");
        smartHomeSystem.returnFromWork();
    }

    public void goToBed() {
        System.out.println();
        System.out.println("--- Person: Going to bed. Time for some peace and quiet. ---");
        smartHomeSystem.goToBed();
    }
}
