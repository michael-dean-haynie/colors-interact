package com.codetudes.colorsinteract.endpoints;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/development")
public class DevelopmentController {

    @GetMapping("/hello")
    HelloWorldDTO helloworld() {
        log.info("helloworld endpoint hit");
        HelloWorldDTO dto = new HelloWorldDTO();
        dto.setHello("world");
        return dto;
    }

    @Data
    class HelloWorldDTO {
        private String hello;
    }
}
