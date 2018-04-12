package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.domain.City;
import com.example.demo.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    public String currentCity;
    public int currentPointLevel;
    public int playerPoints = 0;
    public int numberOfCitiesVisited = 0;
    public int totalCitiesToVisit = 3;
    public boolean playerClickedExitGame = false;

    List<String> answerOptions;
    List<String> allCities = new ArrayList<>();
    List<String> allImages;
    List<String> visitedCities = new ArrayList<>();


    @Autowired // gäller bara en rad
    private DataRepository dataRepository;


    @GetMapping("/startview")
    public ModelAndView renderStartView() {
        allCities = dataRepository.getAllCities();
        System.out.println(allCities);
        return new ModelAndView("startview") // startview.html
                .addObject("startbutton", "Play game");
    }

    // nollställ poäng, numberOfCitiesVisited om användaren har klickat avbryt
    @GetMapping("/showview") // i länken som leder till sidan
    public ModelAndView renderGameView() {
        if (playerClickedExitGame) {
            numberOfCitiesVisited = 0;
            playerPoints = 0;
            visitedCities.clear();
            playerClickedExitGame = false;
        }
        if (numberOfCitiesVisited == totalCitiesToVisit) {
            String playerPointsText = "Ditt resultat är: " + playerPoints;
            numberOfCitiesVisited = 0;
            playerPoints = 0;
            visitedCities.clear();
            return new ModelAndView("endofgame")
                    .addObject("playerPoints", playerPointsText);
        }
        numberOfCitiesVisited++;
        currentPointLevel = 10;
        currentCity = City.generateRandomCity(allCities, visitedCities);
        System.out.println(currentCity);
        allImages = dataRepository.getAllPictures(currentCity);
        answerOptions = City.generateAnswerOptions(currentCity, allCities);
        String gameProgressText = "Du besöker stad " + numberOfCitiesVisited + " av " + totalCitiesToVisit;
        String picturePointLevelText = "Den här bilden är värd " + currentPointLevel + " poäng";
        String playerPointsText = "Din aktuella poängställning är: " + playerPoints;

        return new ModelAndView("gameview") // gameview.html
                .addObject("pictureurl", allImages.get(0))
                .addObject("cityList", answerOptions)
                .addObject("gameProgress", gameProgressText)
                .addObject("picturePointLevel", picturePointLevelText)
                .addObject("playerPoints", playerPointsText)
                .addObject("exitGame", "Hem");
    }

    @GetMapping("/option/{name}")
    public ModelAndView clickListener(@PathVariable String name) {
        if (name.equals("vetej") && currentPointLevel != 2) { // jag vill svara på en lägre poängnivå
            currentPointLevel -= 2;
            String gameProgressText = "Du besöker stad " + numberOfCitiesVisited + " av " + totalCitiesToVisit;
            String picturePointLevelText = "Den här bilden är värd " + currentPointLevel + " poäng";
            String playerPointsText = "Din aktuella poängställning är: " + playerPoints;
            return new ModelAndView("gameview")
                    .addObject("pictureurl", allImages.get(5 - (currentPointLevel / 2))) // nästa url i listan från getAllPictures
                    .addObject("cityList", answerOptions)
                    .addObject("gameProgress", gameProgressText)
                    .addObject("picturePointLevel", picturePointLevelText)
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");

        } else if (name.equals(currentCity)) { // korrekt gissning
            playerPoints = playerPoints + currentPointLevel;
            String playerPointsText = "Din aktuella poängställning är nu: " + playerPoints;
            return new ModelAndView("correctanswer")
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");

        } else { // felaktig gissning
            String playerPointsText = "Din aktuella poängställning är nu: " + playerPoints;
            return new ModelAndView("wronganswer")
                    .addObject("playerPoints", playerPointsText)
                    .addObject("exitGame", "Hem");
        }

    }

    @GetMapping("/exitgame")
    public ModelAndView playerClickedExitGame() {
        playerClickedExitGame = true;
        return new ModelAndView("startview") // startview.html
                .addObject("startbutton", "Play game");
    }
}
