package com.task.delimiter.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface LineService {
    Map<Character, Integer> checkCharacters(String line);

    String removeQuotationMarks(String line);
}
