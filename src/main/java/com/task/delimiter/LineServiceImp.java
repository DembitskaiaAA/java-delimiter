package com.task.delimiter;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LineServiceImp implements LineService {

    Map<String, Map<Character, Integer>> words = new HashMap<>();
    Map<Character, Integer> characters;

    @Override
    public Map<Character, Integer> checkCharacters(String line) {
        String savedLine = removeQuotationMarks(line);
        if (words.containsKey(savedLine)) {
            return words.get(savedLine).entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        } else {
            characters = new HashMap<>();
            for (Character ch : savedLine.toCharArray()) {
                if (characters.containsKey(ch)) {
                    characters.put(ch, characters.get(ch) + 1);
                } else {
                    characters.put(ch, 1);
                }
            }
            words.put(savedLine, characters);
            return characters.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        }
    }

    @Override
    public String removeQuotationMarks(String line) {
        return line.substring(1, line.length() - 1);
    }
}
