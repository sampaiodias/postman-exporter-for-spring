package io.github.sampaiodias.collectionmodel;

import java.util.ArrayList;
import java.util.List;

public class CollectionRequestItem implements Comparable<CollectionRequestItem> {
    private String name;
    private RequestData request;
    private List<RequestEvent> event;
    private List<Object> response;

    public CollectionRequestItem(String name, RequestData data, List<RequestEvent> event) {
        this.name = name;
        this.request = data;
        this.event = event;
        this.response = new ArrayList<>();
    }

    @Override
    public int compareTo(CollectionRequestItem otherItem) {
        return name.compareTo(otherItem.name);
    }
}
