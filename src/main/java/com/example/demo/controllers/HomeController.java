package com.example.demo.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
  

@Controller
public class HomeController {  
    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "<h1>Hello World</h1>";
    }

    @GetMapping("/about-us")
    public String test() {
        return "about-us";
    }

}