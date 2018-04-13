package com.example.demo.controller;

import com.example.demo.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameController {
    public String currentCity;
    public int currentPointLevel;
    public int playerPoints = 0;
    public int numberOfCitiesVisited = 0;
    public int totalCitiesToVisit = 10;
    public boolean playerClickedExitGame = false;

    List<String> allCities = new ArrayList<>();
    List<String> allImagesForCurrentCity;
    List<String> answerOptions;
    List<String> visitedCities = new ArrayList<>();


    @Autowired // gäller bara en rad och behöver alltid följas av en @Component
    private DataRepository dataRepository;


    @GetMapping("/startview") // URL som leder till sidan
    public ModelAndView renderStartView() {
        allCities = dataRepository.getAllCities();
        return new ModelAndView("startview") // Här refereras till startview.html
                .addObject("startbutton", "START");
    }

    @GetMapping("/showview")
    public ModelAndView renderGameViewNewCurrentCity() {
        if (playerClickedExitGame) {
            resetGameParameters();
        }
        if (numberOfCitiesVisited == totalCitiesToVisit) {
            String playerPointsText = "Ditt resultat är: " + playerPoints;
            resetGameParameters();
            return new ModelAndView("endofgame")
                    .addObject("playerPoints", playerPointsText);
        }
        updateGameParameters();
        createAndPrepareNewCurrentCity();

        String gameProgressText = "Du besöker stad " + numberOfCitiesVisited + " av " + totalCitiesToVisit;
        String playerPointsText = "Din aktuella poängställning är: " + playerPoints;
        String picturePointLevelText = "Den här bilden är värd " + currentPointLevel + " poäng";

        return new ModelAndView("gameview")
                .addObject("pictureurl", allImagesForCurrentCity.get(0))
                .addObject("cityList", answerOptions)
                .addObject("gameProgress", gameProgressText)
                .addObject("picturePointLevel", picturePointLevelText)
                .addObject("playerPoints", playerPointsText)
                .addObject("exitGame", "Hem");
    }


    @GetMapping("/option/{name}")
    public ModelAndView renderGameViewCurrentCityLowerPointLevel(@PathVariable String name) {
        if (name.equals("lowerpointlevel") && currentPointLevel != 2) { // Om användaren vill svara på en lägre poängnivå
            currentPointLevel -= 2;

            String gameProgressText = "Du besöker stad " + numberOfCitiesVisited + " av " + totalCitiesToVisit;
            String playerPointsText = "Din aktuella poängställning är: " + playerPoints;
            String picturePointLevelText = "Den här bilden är värd " + currentPointLevel + " poäng";

            return new ModelAndView("gameview")
                    .addObject("pictureurl", allImagesForCurrentCity.get(5 - (currentPointLevel / 2))) // För att få nästa url i listan från getAllPictures
                    .addObject("cityList", answerOptions)
                    .addObject("gameProgress", gameProgressText)
                    .addObject("picturePointLevel", picturePointLevelText)
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");

        } else if (name.equals(currentCity)) { // Om anvädaren ger korrekt gissning
            playerPoints = playerPoints + currentPointLevel;
            String playerPointsText = "Din aktuella poängställning är nu: " + playerPoints;
            return new ModelAndView("correctanswer")
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");

        } else { // Om användaren ger felaktig gissning
            String playerPointsText = "Din aktuella poängställning är nu: " + playerPoints;
            return new ModelAndView("wronganswer")
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");
        }
    }

    @GetMapping("/exitgame")
    public ModelAndView playerClickedExitGame() {
        playerClickedExitGame = true;
        return new ModelAndView("startview")
                .addObject("startbutton", "START");
    }

    //Nedan följer hjälpmetoder
    private void resetGameParameters() {
        playerPoints = 0;
        numberOfCitiesVisited = 0;
        visitedCities.clear();
        playerClickedExitGame = false;
    }

    private void updateGameParameters() {
        numberOfCitiesVisited++;
        currentPointLevel = 10;
    }

    private void createAndPrepareNewCurrentCity() {
        currentCity = generateRandomCurrentCity(allCities, visitedCities);
        allImagesForCurrentCity = dataRepository.getAllPictures(currentCity);
        answerOptions = generateRandomAnswerOptions(currentCity, allCities);
    }

    public static String generateRandomCurrentCity(List<String> cities, List<String> visited) {
        Random rand = new Random();
        String result;
        do {
            int x = rand.nextInt(cities.size());
            result = cities.get(x);
        } while (visited.contains(result));
        visited.add(result);
        return result;
    }

    public static List<String> generateRandomAnswerOptions(String city, List<String> cities) {
        List<String> answerOptions = new ArrayList<>();
        Random rand = new Random();
        int x = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (i == x) {
                answerOptions.add(x, city);
            }
            else if (i != x) {
                String s = generateRandomCityForAnswerOptions(cities);
                if (!answerOptions.contains(s) && !s.equals(city)) {
                    answerOptions.add(i, s);
                }
                else {
                    i--;
                }
            }
        }
        return answerOptions;
    }

    public static String generateRandomCityForAnswerOptions(List<String> cities) {
        Random rand = new Random();
        int x = rand.nextInt(cities.size());
        String result = cities.get(x);
        return result;
    }
}
