package com.task.delimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {
    LineService lineService = new LineService();
    @GetMapping
    public Map<String, Integer> checkСharacters(String line) {
        return line.

    }
}
