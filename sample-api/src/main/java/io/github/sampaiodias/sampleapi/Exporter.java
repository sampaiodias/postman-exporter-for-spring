package io.github.sampaiodias.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.sampaiodias.PostmanExportOptions;
import io.github.sampaiodias.PostmanExporter;

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
                    .build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
