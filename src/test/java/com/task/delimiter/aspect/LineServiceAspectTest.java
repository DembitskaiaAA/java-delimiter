package com.task.delimiter.aspect;

import com.task.delimiter.exception.ConditionException;
import com.task.delimiter.service.LineService;
import com.task.delimiter.service.LineServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LineServiceAspectTest {

    private LineService lineService;

    @BeforeEach
    public void createLineService(@Autowired LineServiceImp lineServiceImp) {
        this.lineService = lineServiceImp;
    }

    @Test
    void beforeLineServiceCheckCharactersForMarksOnly() {
        String word = "\"\"";
        try {
            lineService.checkCharacters(word);
        } catch (ConditionException e) {
            assertEquals("The word must consist of more than just quotation marks", e.getMessage());
        }
    }

    @Test
    void beforeLineServiceCheckCharactersForStartAndEndWithMarks() {
        String word = "\"word";
        try {
            lineService.checkCharacters(word);
        } catch (ConditionException e) {
            assertEquals("For the application to work, enter words starting and ending with quotes", e.getMessage());
        }
    }

    @Test
    void beforeLineServiceCheckCharactersForNumbers() {
        String word = "\"word2\"";
        try {
            lineService.checkCharacters(word);
        } catch (ConditionException e) {
            assertEquals("The request failed. Please use letters when asking", e.getMessage());
        }
    }

    @Test
    void beforeLineServiceCheckCharactersForContainMarks() {
        String word = "\"wor\"d\"";
        try {
            lineService.checkCharacters(word);
        } catch (ConditionException e) {
            assertEquals("The word must not contain quotation marks", e.getMessage());
        }
    }

    @Test
    void beforeLineServiceCheckCharactersForContainWhiteSpaces() {
        String word = "\"wor d\"";
        try {
            lineService.checkCharacters(word);
        } catch (ConditionException e) {
            assertEquals("The request failed. The word can't contain blank spaces", e.getMessage());
        }
    }
}