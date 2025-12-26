package com.example.backend.producer_consumer_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController {

    @GetMapping("/snapShot")
    public Dto[] sendSnapShots(){
        return null;
    }
}
