package com.example.serviceconsumer.controller;

import com.example.serviceconsumer.service.ServiceHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    @Autowired
    ServiceHello serviceHello;

    @GetMapping("/{name}")
    public String hi(@PathVariable String name){
        return serviceHello.hello(name);
    }
}
