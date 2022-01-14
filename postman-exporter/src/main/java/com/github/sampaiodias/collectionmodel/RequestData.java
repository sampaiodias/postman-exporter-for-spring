package com.github.sampaiodias.collectionmodel;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class RequestData {
    private String method;
    private List<String> header;
    private RequestUrl url;
    private RequestBody body;

    public RequestData(String methodMapping, RequestUrl url, Method method) {
        this.method = methodMapping;
        this.url = url;

        String consumesString = getConsumesString(method);
        if (!Objects.equals(consumesString, "")) {
            this.body = new RequestBody(consumesString);
        }
    }

    private String getConsumesString(Method method) {
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null && getMapping.consumes().length > 0) {
            return getMapping.consumes()[0];
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null && postMapping.consumes().length > 0) {
            return postMapping.consumes()[0];
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null && deleteMapping.consumes().length > 0) {
            return deleteMapping.consumes()[0];
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null && putMapping.consumes().length > 0) {
            return putMapping.consumes()[0];
        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (patchMapping != null && patchMapping.consumes().length > 0) {
            return patchMapping.consumes()[0];
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.consumes().length > 0) {
            return requestMapping.consumes()[0];
        }
        return "";
    }
}
