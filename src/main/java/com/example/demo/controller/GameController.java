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
    List<City> answerOptions = new ArrayList<>();
    List<String> allCities;





    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/startview")
    public ModelAndView renderStartView() {
        allCities = dataRepository.getAllCities();
        System.out.println(allCities);
        return new ModelAndView("startview") // startview.html
                .addObject("startbutton", "Play game");
    }

    @GetMapping("/showview") // i länken som leder till sidan
    public ModelAndView renderGameView() {
        currentCity = City.randomCity(allCities);
        System.out.println(currentCity);
        // currentCity = demoapp.randomCity(allCities);
        // uppdatera cityList med currentCity och några felaktiga alternativ, ta bort gamla alternativ
        // currentLevel = 10;
        answerOptions.add(new City(1, "Stockholm"));
        answerOptions.add(new City(2, "Paris"));
        answerOptions.add(new City(3, "London"));
        return new ModelAndView("gameview") // gameview.html
                .addObject("pictureurl", dataRepository.getAllPictures())
                .addObject("cityList", answerOptions);
    }

    @GetMapping("/option/{id}")
    public ModelAndView clickListener(@PathVariable int id) {
        if (id == -1) { // jag vill svara på en lägre poängnivå
            // uppdatera currentLevel, men ej om currentLevel == 2
            return new ModelAndView("endofgame");
        }
        else if (id == 1) { // korrekt gissning
            // uppdatera playerPoints
            return new ModelAndView("correctanswer");
        }
        else { // felaktig gissning
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
