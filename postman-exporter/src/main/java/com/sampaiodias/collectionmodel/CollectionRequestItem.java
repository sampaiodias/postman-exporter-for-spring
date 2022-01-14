package com.sampaiodias.collectionmodel;

import java.util.ArrayList;
import java.util.List;

public class CollectionRequestItem implements Comparable<CollectionRequestItem> {
    private String name;
    private RequestData request;
    private List<Object> response;

    public CollectionRequestItem(String name, RequestData data) {
        this.name = name;
        this.request = data;
        this.response = new ArrayList<>();
    }

    @Override
    public int compareTo(CollectionRequestItem otherItem) {
        return name.compareTo(otherItem.name);
    }
}
