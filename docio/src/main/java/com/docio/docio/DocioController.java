package com.docio.docio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocioController {

    @RequestMapping("/ping")
    public String pong(){
        return "pong";
    }

}
