package com.task.delimiter.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LineServiceImp implements LineService {

    private Map<String, Map<Character, Integer>> words = new HashMap<>();
    private Map<Character, Integer> characters;

    @Override
    public Map<Character, Integer> checkCharacters(String line) {
        String savedLine = removeQuotationMarks(line);
        words.computeIfAbsent(savedLine, value -> {
            characters = new HashMap<>();
            for (Character ch : savedLine.toCharArray()) {
                characters.computeIfPresent(ch, (key, repeatable) -> (repeatable += 1));
                characters.computeIfAbsent(ch, (repeatable) -> (1));
            }
            return characters;
        });
        return words.get(savedLine).entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    @Override
    public String removeQuotationMarks(String line) {
        return line.substring(1, line.length() - 1);
    }
}
