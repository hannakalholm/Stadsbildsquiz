package com.example.demo.controller;

import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {

    private ImageRepository imageRepository = new ImageRepository();

    @GetMapping("/view/")
    public ModelAndView view() {
        return new ModelAndView("view")
                .addObject("views", imageRepository.getAllPictures());

    }
}
