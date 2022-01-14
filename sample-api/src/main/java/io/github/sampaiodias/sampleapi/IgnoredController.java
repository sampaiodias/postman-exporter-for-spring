package io.github.sampaiodias.sampleapi;

import io.github.sampaiodias.PostmanIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PostmanIgnore
@RestController
public class IgnoredController {

    @GetMapping("test")
    public String simpleGet() {
        return "Hello World";
    }
}
