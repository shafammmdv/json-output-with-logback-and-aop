package com.tutorials.jsonoutputwithlogbackaop.model;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record LogOutput(
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("loggerName") String loggerName,
        @JsonProperty("threadName") String threadName,
        @JsonProperty("level") Level level,
        @JsonProperty("message") String message,
        @JsonProperty("logDetails") Object logDetails) {

}
