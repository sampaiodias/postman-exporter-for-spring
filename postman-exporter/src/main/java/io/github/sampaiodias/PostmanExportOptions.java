package io.github.sampaiodias;

import io.github.sampaiodias.collectionmodel.RequestScriptData;

import java.util.*;

public class PostmanExportOptions {
    private String collectionName;
    private String baseUrl;
    private String packageFullName;
    private Map<String, String> stringVariables;
    private List<RequestScriptData> preRequestScriptData;
    private List<RequestScriptData> testScriptData;

    PostmanExportOptions(String collectionName, String baseUrl, String packageFullName,
                         Map<String, String> stringVariables, List<RequestScriptData> preRequestScriptData,
                         List<RequestScriptData> testScriptData) {
        this.collectionName = collectionName;
        this.baseUrl = baseUrl;
        this.packageFullName = packageFullName;
        this.stringVariables = stringVariables;
        this.preRequestScriptData = preRequestScriptData;
        this.testScriptData = testScriptData;
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

    /**
     * The data that defines Pre-request scripts to be added to requests.
     * @return
     */
    public List<RequestScriptData> getPreRequestScriptData() {
        return preRequestScriptData;
    }

    /**
     * The data that defines Test scripts to be added to requests.
     * @return
     */
    public List<RequestScriptData> getTestScriptData() {
        return testScriptData;
    }

    public static PostmanExportOptionsBuilder builder() {
        return new PostmanExportOptionsBuilder();
    }

    public static class PostmanExportOptionsBuilder {
        private String collectionName = "My Collection";
        private String baseUrl = "{{url}}";
        private String packageFullName;
        private Map<String, String> stringVariables;
        private List<RequestScriptData> preRequestScriptData;
        private List<RequestScriptData> testScriptData;

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
         * The base URL of your application. For example: {{url}}, or localhost:8080, or https://yourdomain.com). Using
         * a Postman variable (like {{url}}) is the recommended way.
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

        /**
         * Add a Pre-request script that will be added to ALL Postman requests exported.
         * @param scriptLines Scripts that will be executed by the request. You can add one script line per list element.
         * @return
         */
        public PostmanExportOptionsBuilder preRequestScript(List<String> scriptLines) {
            return preRequestScript(null, scriptLines);
        }

        /**
         * Add a Test script that will be added to ALL Postman requests exported.
         * @param scriptLines Scripts that will be executed by the request. You can add one script line per list element.
         * @return
         */
        public PostmanExportOptionsBuilder testScript(List<String> scriptLines) {
            return testScript(null, scriptLines);
        }

        /**
         * Add a Pre-request script that will be added to Postman requests exported that have the name specified, or
         * requests inside a folder with the specified name.
         * @param name The name of the request or folder
         * @param scriptLines Scripts that will be executed by the request. You can add one script line per list element.
         * @return
         */
        public PostmanExportOptionsBuilder preRequestScript(String name, List<String> scriptLines) {
            if (this.preRequestScriptData == null) {
                this.preRequestScriptData = new ArrayList<>();
            }
            this.preRequestScriptData.add(new RequestScriptData(name, scriptLines));
            return this;
        }

        /**
         * Add a Test script that will be added to Postman requests exported that have the name specified, or
         * requests inside a folder with the specified name.
         * @param name The name of the request or folder.
         * @param scriptLines Scripts that will be executed by the request. You can add one script line per list element.
         * @return
         */
        public PostmanExportOptionsBuilder testScript(String name, List<String> scriptLines) {
            if (this.testScriptData == null) {
                this.testScriptData = new ArrayList<>();
            }
            this.testScriptData.add(new RequestScriptData(name, scriptLines));
            return this;
        }

        public PostmanExportOptions build() {
            return new PostmanExportOptions(collectionName, baseUrl, packageFullName, stringVariables, preRequestScriptData, testScriptData);
        }
    }
}
