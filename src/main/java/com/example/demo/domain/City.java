package com.example.demo.domain;

import java.util.List;
import java.util.Random;

public class City {

    public int ID;
    public String name;

    public City(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public static String randomCity(List<String> cities) {
        Random rand = new Random();
        int x = rand.nextInt(cities.size());
        String result = cities.get(x);
        return result;
    }
}
