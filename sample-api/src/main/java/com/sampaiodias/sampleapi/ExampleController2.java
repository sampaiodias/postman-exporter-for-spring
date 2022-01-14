package com.sampaiodias.sampleapi;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController2 {

    @GetMapping
    public String simpleGet() {
        return "Hello World";
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public String postWithBody(@RequestBody String body) {
        return "Hello World";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
    public String simplePostRequestMappingWithBody(@RequestBody String body) {
        return "Hello World";
    }
}
