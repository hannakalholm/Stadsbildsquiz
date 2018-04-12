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
    public int currentLevel;
    public int playerPoints;



    @Autowired
    private DataRepository dataRepository;
    List<String> answerOptions;
    List<String> allCities;
    List<String> allImages;

    @GetMapping("/startview")
    public ModelAndView renderStartView() {
        allCities = dataRepository.getAllCities();
        System.out.println(allCities);
        return new ModelAndView("startview") // startview.html
                .addObject("startbutton", "Play game");
    }

    @GetMapping("/showview") // i länken som leder till sidan
    public ModelAndView renderGameView() {
        currentLevel = 10;
        currentCity = City.generateRandomCity(allCities);
        System.out.println(currentCity);
        allImages = dataRepository.getAllPictures(currentCity);
        answerOptions = City.generateAnswerOptions(currentCity, allCities);

        return new ModelAndView("gameview") // gameview.html
                .addObject("pictureurl", allImages.get(0))
                .addObject("cityList", answerOptions);
    }

    @GetMapping("/option/{name}")
    public ModelAndView clickListener(@PathVariable String name) {
        if (name.equals("vetej") && currentLevel != 2) { // jag vill svara på en lägre poängnivå
            currentLevel -= 2;
            return new ModelAndView("gameview")
                    .addObject("pictureurl", allImages.get(5 - (currentLevel / 2))) // nästa url i listan från getAllPictures
                    .addObject("cityList", answerOptions);

        } else if (name.equals(currentCity)) { // korrekt gissning
            // TODO: uppdatera playerPoints
            return new ModelAndView("correctanswer");

        } else { // felaktig gissning
            return new ModelAndView("wronganswer");
        }

    }

    @GetMapping("/correctanswer") // i länken som leder till sidan
    public ModelAndView correctAnswer() {
        return new ModelAndView("correctanswer"); // correctanswer.html
    }

    @GetMapping("/wronganswer") // i länken som leder till sidan
    public ModelAndView wrongAnswer() {
        return new ModelAndView("wronganswer"); // wronganswer.html
    }

    @GetMapping("/endofgame") // i länken som leder till sidan
    public ModelAndView endOfGame() {
        return new ModelAndView("endofgame"); // endofgame.html
    }
}
