[![Maven Central](https://img.shields.io/badge/maven%20central-0.3.0-brightgreen)](https://search.maven.org/artifact/io.github.sampaiodias/postman-exporter)
[![MIT License](https://img.shields.io/github/license/sampaiodias/postman-exporter-for-spring)](https://github.com/sampaiodias/postman-exporter-for-spring/blob/main/LICENSE.md)

# Postman Exporter for Spring

This project is a simple Java library that can export all of your Spring endpoints into a Postman Collection json, which you can import on Postman. This library is fairly new, so feedback is greatly appreciated.

![example](images/readme-example.jpg)

Example:
```java
PostmanExporter exporter = new PostmanExporter();
String json = exporter.export(PostmanExportOptions.builder()
                    .collectionName("My Collection")
                    .packageFullName("com.example.package")
                    .baseUrl("{{url}}")
                    .variable("url", "localhost:8080")
                    .build());
```

To use this library on your project:
```xml
<dependency>
    <groupId>io.github.sampaiodias</groupId>
    <artifactId>postman-exporter</artifactId>
    <version>0.3.0</version>
</dependency>
```

## Features
- Supports all Mapping annotations: @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping.
- Generates Postman requests configured with request methods (GET, POST, etc.), Params and Body. 
- Groups requests into folders (one folder per class/Controller).
- Supports Postman variables (such as "{{variable-name}}").
- Ignores classes and methods annotated with @PostmanIgnore during exportation.
- Renames requests and folders easily with the @PostmanName annotation.
