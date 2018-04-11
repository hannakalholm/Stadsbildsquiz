package com.example.demo;

import com.example.demo.domain.City;
import com.example.demo.repository.DataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // endast denna rad i main
        SpringApplication.run(DemoApplication.class, args);

    }

}
