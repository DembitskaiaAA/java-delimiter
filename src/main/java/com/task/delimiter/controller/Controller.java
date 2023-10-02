package com.task.delimiter.controller;

import com.task.delimiter.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class Controller {
    private final LineService lineService;

    public Controller(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping
    public Map<Character, Integer> checkCharacters(@RequestBody String line) {
        log.info("Getting line {}", line);
        return lineService.checkCharacters(line);
    }
}
