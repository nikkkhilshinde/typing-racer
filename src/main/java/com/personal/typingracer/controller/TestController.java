package com.personal.typingracer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nikhilshinde on 26/09/22
 */
@Slf4j
@RestController
public class TestController {

    @PostMapping("/test")
    public void testMethod(){
        log.info("Working");
    }
}
