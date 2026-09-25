package com.example.logging_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logging")
public class LoggingDemoController {
    private static final Logger log = LoggerFactory.getLogger(LoggingDemoController.class);

    @GetMapping("/demo")
    public String demo() {
        log.trace("TRACE message from /logging/demo");
        log.debug("DEBUG message from /logging/demo");
        log.info("INFO message from /logging/demo");
        log.warn("WARN message from /logging/demo");
        log.error("ERROR message from /logging/demo");

        return "Logging demo executed. Check console output.";
    }

    @GetMapping("/order")
    public String order() {
        log.info("Order created successfully");
        return "Order created. Check JSON log in console.";
    }
}

