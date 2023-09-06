package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * Class for checking current port.
 */
@RestController
public class InfoController {

    @Value("${server.port}")
    private Integer port;

    /**
     * The method for checking current port.
     */
    @GetMapping("/getPort")
    public String getPort() {
        return "Using port: " + port;
    }
}
