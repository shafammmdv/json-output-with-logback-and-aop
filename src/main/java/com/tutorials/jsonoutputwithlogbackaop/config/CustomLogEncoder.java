package com.tutorials.jsonoutputwithlogbackaop.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tutorials.jsonoutputwithlogbackaop.model.LogOutput;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

public class CustomLogEncoder extends LayoutWrappingEncoder<ILoggingEvent> {
    ObjectMapper om = new ObjectMapper();

    @SneakyThrows
    @Override
    public byte[] encode(ILoggingEvent event) {
        om.registerModule(new JavaTimeModule());
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

        LogOutput logOutput = new LogOutput(
                event.getInstant(), event.getLoggerName(), event.getThreadName(),
                event.getLevel(), event.getMessage(), event.getArgumentArray());

        return "%s%s".formatted(om.writeValueAsString(logOutput), "\n").getBytes();
    }


}
