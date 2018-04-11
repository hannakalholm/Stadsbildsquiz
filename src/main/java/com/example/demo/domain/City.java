package com.example.demo.domain;

import com.example.demo.controller.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class City {

    public int ID;
    public String name;

    public City(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public static String generateRandomCity(List<String> cities) {
        Random rand = new Random();
        int x = rand.nextInt(cities.size());
        String result = cities.get(x);
        return result;
    }

    public static List<String> generateAnswerOptions(String city, List<String> cities) {
        List<String> answerOptions = new ArrayList<>();
        Random rand = new Random();
        int x = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            answerOptions.add("");
            if (i == x) {
                answerOptions.set(x, city);
            }
            else if (i != x) {
                String s = generateRandomCity(cities);
                if (!answerOptions.contains(s)) {
                    answerOptions.set(i, s);
                }
                else {
                    i--;
                }
            }
        }
        return answerOptions;
    }
}
