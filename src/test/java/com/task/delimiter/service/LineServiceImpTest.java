package com.task.delimiter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LineServiceImpTest {

    private LineService lineService;

    @BeforeEach
    void createMaps(@Autowired LineServiceImp lineServiceImp) {
        this.lineService = lineServiceImp;
    }

    @Test
    void checkCharactersCorrect() {
        String word = "\"book\"";
        Map<Character, Integer> correctResult = new LinkedHashMap<>();
        correctResult.put('o', 2);
        correctResult.put('b', 1);
        correctResult.put('k', 1);
        Map<Character, Integer> result = lineService.checkCharacters(word);
        assertEquals(correctResult, result);
    }
}