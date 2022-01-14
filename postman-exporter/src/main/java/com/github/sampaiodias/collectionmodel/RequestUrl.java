package com.github.sampaiodias.collectionmodel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestUrl {
    private String raw;
    private String protocol;
    private List<String> host;
    private String port;
    private List<String> path;
    private List<CollectionQuery> query;

    public RequestUrl(String raw, Map<String, String> query) {
        this.raw = raw.toLowerCase(Locale.ROOT);
        this.path = new ArrayList<>();
        this.query = query != null ? new ArrayList<>() : null;

        if (this.raw.startsWith("http")) {
            this.protocol = this.raw.substring(0, this.raw.lastIndexOf(":"));
            int slashIndex = this.raw.indexOf("/");
            String hostUrl = this.raw.substring(slashIndex + 2);
            hostUrl = hostUrl.substring(0, hostUrl.indexOf("/"));
            this.host = Arrays.asList(hostUrl.split("\\."));
            int secondSlashIndex = this.raw.indexOf("/", slashIndex + 1);
            this.path.addAll(Arrays.asList(this.raw.substring(secondSlashIndex + 1).split("/")));
            this.path.remove(0);
        } else if (this.raw.startsWith("localhost")) {
            this.host = Arrays.asList("localhost");
            this.port = this.raw.substring(this.raw.indexOf(":") + 1, this.raw.indexOf("/"));
            this.path.addAll(Arrays.asList(this.raw.substring(this.raw.indexOf("/") + 1).split("/")));
        }

        if (query != null) {
            StringBuilder urlStringBuilder = new StringBuilder();
            urlStringBuilder.append(raw.toLowerCase(Locale.ROOT));
            AtomicInteger i = new AtomicInteger();
            query.forEach((key, value) -> {
                urlStringBuilder.append((i.get() == 0 ? "?" : "&"));
                urlStringBuilder.append(key);
                urlStringBuilder.append("=");
                urlStringBuilder.append(value);

                this.query.add(new CollectionQuery(key, value));

                i.getAndIncrement();
            });
            this.raw = urlStringBuilder.toString();
        }
    }
}
