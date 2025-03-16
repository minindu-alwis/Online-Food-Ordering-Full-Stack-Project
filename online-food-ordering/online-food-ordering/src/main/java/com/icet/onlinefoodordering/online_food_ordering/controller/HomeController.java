package com.icet.onlinefoodordering.online_food_ordering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Add this annotation
@RequestMapping("/")  // Specify a path for the controller
public class HomeController {

    @GetMapping
    public ResponseEntity<String> homeController(){
        return new ResponseEntity<>("Welcome to minidu alwis", HttpStatus.OK);
    }
}
