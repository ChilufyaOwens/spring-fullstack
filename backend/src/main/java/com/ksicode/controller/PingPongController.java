package com.ksicode.controller;

import com.ksicode.dto.Ping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/pings")
public class PingPongController {

    /**
     * This api is used for testing the application
     * @return response api
     */
    @GetMapping
    public ResponseEntity<Ping> getPing(){
        Ping ping = new Ping(
                "Pong"
        );
        return new ResponseEntity<>(ping, HttpStatus.OK);
    }
}
