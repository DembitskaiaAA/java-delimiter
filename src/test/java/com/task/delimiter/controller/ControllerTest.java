package com.task.delimiter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.delimiter.exception.ConditionException;
import com.task.delimiter.exception.ErrorResponse;
import com.task.delimiter.service.LineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(Controller.class)
class ControllerTest {
    @Value("${HOME.URL}")
    String url;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LineService lineService;

    @Test
    void checkCharactersCorrect() throws Exception {
        String word = "book";
        Map<Character, Integer> line = new LinkedHashMap<>();
        line.put('o', 2);
        line.put('b', 1);
        line.put('k', 1);

        given(lineService.checkCharacters(anyString())).willReturn(line);
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.content().json(line.toString()));
    }

    @Test
    void checkCharactersLineForMarksOnly() throws Exception {
        String word = "\"\"";
        ErrorResponse errorResponse = new ErrorResponse("The word must consist of more than just quotation marks");
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        when(lineService.checkCharacters(word)).thenThrow(new ConditionException("The word must consist of more than just quotation marks"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorResponseJson));
    }

    @Test
    void checkCharactersLineForStartsAndEndsWithMarks() throws Exception {
        String word = "\"book";
        ErrorResponse errorResponse = new ErrorResponse("For the application to work, enter words starting and ending with quotes");
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        when(lineService.checkCharacters(word))
                .thenThrow(new ConditionException("For the application to work, enter words starting and ending with quotes"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorResponseJson));
    }

    @Test
    void checkCharactersLineWithNumber() throws Exception {
        String word = "book2";
        ErrorResponse errorResponse = new ErrorResponse("The request failed. Please use letters when asking");
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        when(lineService.checkCharacters(word)).thenThrow(new ConditionException("The request failed. Please use letters when asking"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorResponseJson));
    }


    @Test
    void checkCharactersForContainMarks() throws Exception {
        String word = "w\"ord";
        ErrorResponse errorResponse = new ErrorResponse("The word must not contain quotation marks");
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        when(lineService.checkCharacters(word))
                .thenThrow(new ConditionException("The word must not contain quotation marks"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorResponseJson));
    }

    @Test
    void checkCharactersLineForWhiteSpace() throws Exception {
        String word = " ";
        ErrorResponse errorResponse = new ErrorResponse("The request failed. The word can't contain blank spaces");
        String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

        when(lineService.checkCharacters(word))
                .thenThrow(new ConditionException("The request failed. The word can't contain blank spaces"));
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(errorResponseJson));
    }

    @Test
    void checkCharactersEmptyLine() throws Exception {
        String word = "";

        mockMvc.perform(MockMvcRequestBuilders.post(url).content(word))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Request body is missing"));
    }
}