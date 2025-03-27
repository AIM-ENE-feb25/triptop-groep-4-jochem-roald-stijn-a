package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Heating heating = new Heating();
		Cooling cooling = new Cooling();

		ClimateControl climateControl = new ClimateControl(cooling, heating);

		Lightning lightning = new Lightning();
		MusicSystem musicSystem = new MusicSystem();

		SmartHomeSystem smartHomeSystem = new SmartHomeSystem(climateControl, musicSystem, lightning);
		Person person = new Person(smartHomeSystem);

		person.returnFromWork();
		person.goToBed();
	}

}
