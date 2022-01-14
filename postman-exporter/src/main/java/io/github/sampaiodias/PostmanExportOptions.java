package io.github.sampaiodias;

import java.util.HashMap;
import java.util.Map;

public class PostmanExportOptions {
    private String collectionName;
    private String baseUrl;
    private String packageFullName;
    private Map<String, String> stringVariables;

    PostmanExportOptions(String collectionName, String baseUrl, String packageFullName, Map<String, String> stringVariables) {
        this.collectionName = collectionName;
        this.baseUrl = baseUrl;
        this.packageFullName = packageFullName;
        this.stringVariables = stringVariables;
    }

    /**
     * The name that will be displayed on your Postman collection.
     * @return
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * The base URL of your application (for example: localhost:8080 or https://yourdomain.com).
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * The complete package name of the files that will be inspected to find your endpoints.
     * @return
     */
    public String getPackageFullName() {
        return packageFullName;
    }

    /**
     * A map of all string variables to be added to the list of variables of the Collection.
     * @return
     */
    public Map<String, String> getStringVariables() {
        return stringVariables;
    }

    public static PostmanExportOptionsBuilder builder() {
        return new PostmanExportOptionsBuilder();
    }

    public static class PostmanExportOptionsBuilder {
        private String collectionName = "My Collection";
        private String baseUrl = "{{url}}";
        private String packageFullName;
        private Map<String, String> stringVariables;

        PostmanExportOptionsBuilder() {

        }

        /**
         * The name that will be displayed on your Postman collection.
         * @param collectionName
         * @return
         */
        public PostmanExportOptionsBuilder collectionName(String collectionName) {
            this.collectionName = collectionName;
            return this;
        }

        /**
         * The base URL of your application (for example: localhost:8080, or https://yourdomain.com, or {{url}}).
         * @param baseUrl
         * @return
         */
        public PostmanExportOptionsBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * The complete package name of the files that will be inspected to find your endpoints.
         * @param packageFullName
         * @return
         */
        public PostmanExportOptionsBuilder packageFullName(String packageFullName) {
            this.packageFullName = packageFullName;
            return this;
        }

        /**
         * Add a string variable to be added to the list of variables of the Collection.
         * @param key
         * @param value
         * @return
         */
        public PostmanExportOptionsBuilder variable(String key, String value) {
            if (this.stringVariables == null) {
                this.stringVariables = new HashMap<>();
            }
            this.stringVariables.put(key, value);
            return this;
        }

        public PostmanExportOptions build() {
            return new PostmanExportOptions(collectionName, baseUrl, packageFullName, stringVariables);
        }
    }
}
