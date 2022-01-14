package com.sampaiodias.sampleapi;

import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleController {

    @GetMapping("test")
    public String simpleGet() {
        return "Hello World";
    }

    @PostMapping("test")
    public String simplePost(@RequestParam String someParam) {
        return someParam;
    }

    @PutMapping
    public String simplePut() {
        return "Put";
    }

    @PatchMapping
    public String simplePatch() {
        return "Patch";
    }

    @DeleteMapping("/test")
    public String simpleDelete() {
        return "Delete";
    }
}
