package com.sampaiodias.collectionmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostmanCollection {
    private CollectionInfo info;
    @JsonProperty(value = "item")
    private List<CollectionFolder> folders;

    public PostmanCollection(String collectionName) {
        this.info = new CollectionInfo(UUID.randomUUID().toString(), collectionName);
        folders = new ArrayList<>();
    }

    public List<CollectionFolder> getFolders() {
        return folders;
    }

    public CollectionFolder getOrCreateFolder(String name) {
        for (CollectionFolder folder : folders) {
            if (folder.getName().equals(name)) {
                return folder;
            }
        }
        CollectionFolder folder = new CollectionFolder(name);
        folders.add(folder);
        return folder;
    }

    public void sortData() {
        folders.sort(CollectionFolder::compareTo);
        for (CollectionFolder folder : folders) {
            folder.getRequests().sort(CollectionRequestItem::compareTo);
        }
    }
}
