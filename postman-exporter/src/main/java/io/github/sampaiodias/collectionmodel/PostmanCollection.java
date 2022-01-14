package io.github.sampaiodias.collectionmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PostmanCollection {
    private CollectionInfo info;
    private List<CollectionVariable> variables;
    @JsonProperty(value = "item")
    private List<CollectionFolder> folders;

    public PostmanCollection(String collectionName, Map<String, String> stringVariables) {
        this.info = new CollectionInfo(UUID.randomUUID().toString(), collectionName);
        folders = new ArrayList<>();

        if (stringVariables != null && stringVariables.size() > 0) {
            if (this.variables == null) {
                this.variables = new ArrayList<>();
            }
            stringVariables.forEach((key, value) -> {
                this.variables.add(new CollectionVariable(key, value, "string"));
            });
        }
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
