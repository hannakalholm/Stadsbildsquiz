package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

        List<String>cities = new ArrayList<>();
        cities.add("Stockholm");
        cities.add("Paris");
        cities.add("Rom");
        cities.add("London");
        cities.add("Lissabon");
        cities.add("Berlin");
        cities.add("Tallinn");
        cities.add("Barcelona");
        cities.add("Amsterdam");
        cities.add("Reykjavik");

    }
}
