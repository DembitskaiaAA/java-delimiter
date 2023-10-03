package com.task.delimiter.aspect;

import com.task.delimiter.exception.ConditionException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LineServiceAspect {

    @Before("execution(* com.task.delimiter.service.LineServiceImp.checkCharacters(String)) && args(line)")
    public void beforeLineServiceCheckCharacters(String line) {
        if (line.charAt(0) == '"' && line.charAt(1) == '"' && line.length() == 2) {
            throw new ConditionException("The word must consist of more than just quotation marks");
        }

        if (!line.startsWith("\"") || !line.endsWith("\"")) {
            throw new ConditionException("For the application to work, enter words starting and ending with quotes");
        }

        for (Character ch : line.trim().substring(1, line.length() - 1).toCharArray()) {
            if (Character.isDigit(ch)) {
                throw new ConditionException("The request failed. Please use letters when asking");
            }
            if (ch == '"') {
                throw new ConditionException("The word must not contain quotation marks");
            }
            if (ch.equals(' ')) {
                throw new ConditionException("The request failed. The word can't contain blank spaces");
            }
        }
    }
}
