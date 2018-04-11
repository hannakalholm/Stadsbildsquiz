package com.example.demo.controller;

import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/startview")
    public ModelAndView renderStartView() {
        return new ModelAndView("startview") // startview.html
                .addObject("startbutton", "Play game");
    }

    @GetMapping("/showview") // i länken som leder till sidan
    public ModelAndView renderGameView() {
        return new ModelAndView("gameview") // gameview.html
                .addObject("pictureurl", imageRepository.getAllPictures());

    }
}
