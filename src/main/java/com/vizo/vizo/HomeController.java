package com.vizo.vizo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public String homeControllerHandler(){
        return "this is home controller by Janhvi Koske";
    }

    @GetMapping("/home")
    public String homeControllerHandler2(){
        return "Janhvi Koske";
    }

}
