package com.github.sampaiodias.sampleapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.sampaiodias.PostmanExporter;

public class Exporter {

    public static void main(String[] args) {
        System.out.println(export());
    }

    public static String export() {
        PostmanExporter exporter = new PostmanExporter();
        try {
            return exporter.export("My Collection", "localhost:8080", "com.github.sampaiodias.sampleapi");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
