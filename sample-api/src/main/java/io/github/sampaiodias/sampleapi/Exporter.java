package io.github.sampaiodias.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.sampaiodias.PostmanExportOptions;
import io.github.sampaiodias.PostmanExporter;

import java.util.Arrays;

public class Exporter {

    public static void main(String[] args) {
        System.out.println(export());
    }

    public static String export() {
        PostmanExporter exporter = new PostmanExporter();
        try {
            return exporter.export(PostmanExportOptions.builder()
                    .collectionName("My Collection")
                    .packageFullName("io.github.sampaiodias.sampleapi")
                    .baseUrl("{{url}}")
                    .variable("url", "localhost:8080")
                    .preRequestScript(Arrays.asList("pm.test(\"Status code is 200\", function () {\n" +
                                    "    pm.response.to.have.status(200);\n" +
                                    "});"))
                    .testScript("Custom Named Folder", Arrays.asList("pm.test(\"Status code is 200\", function () {\n" +
                            "    pm.response.to.have.status(200);\n" +
                            "});"))
                    .testScript("simpleGet", Arrays.asList("pm.test(\"Status code is 200\", function () {\n" +
                            "    pm.response.to.have.status(200);\n" +
                            "});"))
                    .build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
