package com.github.sampaiodias.collectionmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CollectionInfo {

    @JsonProperty(value = "_postman_id")
    private String postmanId;
    private String name;
    private String schema;

    public CollectionInfo(String id, String name) {
        this.postmanId = id;
        this.name = name;
        this.schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";
    }
}
