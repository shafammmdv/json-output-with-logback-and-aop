package com.tutorials.jsonoutputwithlogbackaop.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogbackConfiguration {

    public LogbackConfiguration() {
        configureLogging();
    }

    private void configureLogging() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);
        loggerContext.reset();

        // Configure a ConsoleAppender
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);

        // Customize log format using a custom encoder
        LayoutWrappingEncoder<ILoggingEvent> encoder = new CustomLogEncoder();
        encoder.setContext(loggerContext);
        encoder.start();
        consoleAppender.setEncoder(encoder);

        // Add the appender to the root logger
        consoleAppender.start();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(consoleAppender);

        // Set log level (e.g., INFO, DEBUG, WARN)
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.DEBUG);
    }
}
