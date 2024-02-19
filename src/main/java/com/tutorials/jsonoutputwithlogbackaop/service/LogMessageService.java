package com.tutorials.jsonoutputwithlogbackaop.service;

import com.tutorials.jsonoutputwithlogbackaop.exception.CustomException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogMessageService {
    public Map<String, String> success(Map<String, String> body) {
        return Map.of("status", "SUCCESS");
    }

    public Map<String, String> error(Map<String, String> body) {
        throw new CustomException("This is an exception message");
    }
}
