package io.github.sampaiodias.collectionmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CollectionFolder implements Comparable<CollectionFolder> {
    private String name;
    @JsonProperty("item")
    private List<CollectionRequestItem> requests;

    public CollectionFolder(String name) {
        this.name = name;
        this.requests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<CollectionRequestItem> getRequests() {
        return requests;
    }

    @Override
    public int compareTo(CollectionFolder otherFolder) {
        return name.compareTo(otherFolder.getName());
    }
}
