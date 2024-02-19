package com.tutorials.jsonoutputwithlogbackaop.controller;

import com.tutorials.jsonoutputwithlogbackaop.service.LogMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogMessageController {
    LogMessageService service;

    @GetMapping("/success")
    ResponseEntity<Map> success(@RequestHeader(name = "THREAD_ID", required = false) UUID threadId) {
        return ResponseEntity.ok(
                service.success(Map.of(
                        "name", "Emin",
                        "surname", "Israfilzade")));
    }

    @GetMapping("/error")
    ResponseEntity<Map> error(@RequestHeader(name = "THREAD_ID", required = false) UUID threadId) {
        return ResponseEntity.ok(
                service.error(Map.of(
                        "name", "Emin",
                        "surname", "Israfilzade")));
    }
}
