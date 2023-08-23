package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
public class InfoController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/getPort")
    public String getPort() {
        return "Используемый порт: " + port;
    }
}
