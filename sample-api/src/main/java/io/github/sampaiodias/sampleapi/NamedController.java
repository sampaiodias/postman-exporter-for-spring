package io.github.sampaiodias.sampleapi;

import io.github.sampaiodias.annotations.PostmanName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PostmanName("Custom Named Folder")
@RestController
public class NamedController {

    @PostmanName("My Favorite GET Request")
    @GetMapping("test")
    public String simpleGet() {
        return "Hello World";
    }
}
