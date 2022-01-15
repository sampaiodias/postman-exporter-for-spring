package io.github.sampaiodias;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sampaiodias.annotations.PostmanIgnore;
import io.github.sampaiodias.annotations.PostmanName;
import io.github.sampaiodias.collectionmodel.*;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * This is the class that can export your Spring endpoints into a Postman Collection.
 */
public class PostmanExporter {

    /**
     * Generates a json String based on all endpoints available on your project. You can you this String to import a new
     * collection on Postman.
     *
     * @param exportOptions Options used to change how the Collection will be exported.
     * @return The Postman Collection json string.
     * @throws JsonProcessingException If, for some reason, the collection fails to be serialized into a json.
     */
    public String export(PostmanExportOptions exportOptions) throws JsonProcessingException {
        return export(exportOptions.getCollectionName(), exportOptions.getBaseUrl(), exportOptions.getPackageFullName(),
                exportOptions.getStringVariables(), exportOptions.getPreRequestScriptData(), exportOptions.getTestScriptData());
    }

    /**
     * Generates a json String based on all endpoints available on your project. You can you this String to import a new
     * collection on Postman.
     *
     * @param collectionName The name that will be displayed on your Postman collection.
     * @param baseUrl The base URL of your application (for example: localhost:8080 or https://yourdomain.com).
     * @param packageFullName The complete package name of the files that will be inspected to find your endpoints.
     * @return The Postman Collection json string.
     * @throws JsonProcessingException If, for some reason, the collection fails to be serialized into a json.
     */
    public String export(String collectionName, String baseUrl, String packageFullName) throws JsonProcessingException {
        return export(collectionName, baseUrl, packageFullName, null, null, null);
    }

    private String export(String collectionName, String baseUrl, String packageFullName, Map<String, String> stringVariables,
                          List<RequestScriptData> preRequestScriptData, List<RequestScriptData> testScriptData) throws JsonProcessingException {
        Reflections reflections = new Reflections(packageFullName, new MethodAnnotationsScanner());
        Set<Method> methods = new HashSet<>();
        methods.addAll(reflections.getMethodsAnnotatedWith(GetMapping.class));
        methods.addAll(reflections.getMethodsAnnotatedWith(DeleteMapping.class));
        methods.addAll(reflections.getMethodsAnnotatedWith(PatchMapping.class));
        methods.addAll(reflections.getMethodsAnnotatedWith(PostMapping.class));
        methods.addAll(reflections.getMethodsAnnotatedWith(PutMapping.class));
        methods.addAll(reflections.getMethodsAnnotatedWith(RequestMapping.class));

        PostmanCollection collection = new PostmanCollection(collectionName, stringVariables);
        for (Method method : methods) {
            if (method.getAnnotation(PostmanIgnore.class) != null || method.getDeclaringClass().isAnnotationPresent(PostmanIgnore.class)) {
                continue;
            }

            PostmanName classPostmanName = method.getDeclaringClass().getAnnotation(PostmanName.class);
            String folderName = classPostmanName != null ? classPostmanName.value() : method.getDeclaringClass().getSimpleName();
            CollectionFolder folder = collection.getOrCreateFolder(folderName);

            PostmanName methodPostmanName = method.getAnnotation(PostmanName.class);
            String requestName = methodPostmanName != null ? methodPostmanName.value() : method.getName();

            RequestUrl url = new RequestUrl(getRawUrl(baseUrl, method), getQuery(method));
            List<RequestEvent> events = getRequestEvents(preRequestScriptData, testScriptData, folderName, requestName);

            folder.getRequests().add(new CollectionRequestItem(requestName,
                    new RequestData(getMappingString(method), url, method), events));
        }
        collection.sortData();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writeValueAsString(collection);
    }

    private List<RequestEvent> getRequestEvents(List<RequestScriptData> preRequestScriptData, List<RequestScriptData> testScriptData, String folderName, String requestName) {
        List<String> preRequestScripts = getScripts(preRequestScriptData, folderName, requestName);
        List<String> testScripts = getScripts(testScriptData, folderName, requestName);
        List<RequestEvent> events = null;
        if (preRequestScripts != null) {
            events = new ArrayList<>();
            events.add(new RequestEvent(RequestEvent.RequestScriptType.PRE_REQUEST, preRequestScripts));
        }
        if (testScripts != null) {
            if (events == null) {
                events = new ArrayList<>();
            }
            events.add(new RequestEvent(RequestEvent.RequestScriptType.TEST, testScripts));
        }
        return events;
    }

    private List<String> getScripts(List<RequestScriptData> dataList, String folderName, String requestName) {
        List<String> scripts = null;
        if (dataList != null && dataList.size() > 0) {
            for (RequestScriptData scriptData : dataList) {
                if (scriptData.getName() == null || Objects.equals(scriptData.getName(), "")
                        || scriptData.getName().equals(requestName) || scriptData.getName().equals(folderName)) {
                    if (scripts == null) {
                        scripts = new ArrayList<>();
                    }
                    scripts.addAll(scriptData.getScript());
                }
            }
        }
        return scripts;
    }

    private Map<String, String> getQuery(Method method) {
        Parameter[] parameters = method.getParameters();

        if (parameters.length == 0) {
            return null;
        }

        Map<String, String> query = new HashMap<>();
        for (Parameter parameter : parameters) {
            String parameterName = parameter.isNamePresent() ? parameter.getName() : "";
            RequestParam annotation = parameter.getAnnotation(RequestParam.class);
            if (annotation != null) {
                query.put(annotation.value().equals("") ? parameterName : annotation.value(), "");
            }
        }
        return query;
    }

    private String getRawUrl(String baseUrl, Method method) {
        String prefix = baseUrl + (baseUrl.endsWith("/") ? "" : "/");

        RequestMapping controllerMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        if (controllerMapping != null && controllerMapping.value().length > 0) {
            String path = normalizePathUrl(controllerMapping.value()[0]);
            prefix += path + (path.endsWith("/") ? "" : "/");
        }

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return prefix + (getMapping.value().length > 0 ? normalizePathUrl(getMapping.value()[0]) : "");
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return prefix + (postMapping.value().length > 0 ? normalizePathUrl(postMapping.value()[0]) : "");
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return prefix + (deleteMapping.value().length > 0 ? normalizePathUrl(deleteMapping.value()[0]) : "");
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return prefix + (putMapping.value().length > 0 ? normalizePathUrl(putMapping.value()[0]) : "");
        }
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (patchMapping != null) {
            return prefix + (patchMapping.value().length > 0 ? normalizePathUrl(patchMapping.value()[0]) : "");
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            return prefix + (requestMapping.value().length > 0 ? normalizePathUrl(requestMapping.value()[0]) : "");
        }

        return baseUrl;
    }

    private String normalizePathUrl(String path) {
        return !path.startsWith("/") ? path : path.substring(1);
    }

    private String getMappingString(Method method) {
        if (method.getAnnotation(GetMapping.class) != null) {
            return "GET";
        }
        if (method.getAnnotation(PostMapping.class) != null) {
            return "POST";
        }
        if (method.getAnnotation(DeleteMapping.class) != null) {
            return "DELETE";
        }
        if (method.getAnnotation(PutMapping.class) != null) {
            return "PUT";
        }
        if (method.getAnnotation(PatchMapping.class) != null) {
            return "PATCH";
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null && requestMapping.method().length > 0) {
            return requestMapping.method()[0].toString();
        }
        return "GET";
    }
}
