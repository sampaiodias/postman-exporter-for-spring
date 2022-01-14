package com.sampaiodias.collectionmodel;

import org.springframework.http.MediaType;

public class RequestBody {
    private String mode;
    private String raw;
    private RequestBodyOptions options;

    public RequestBody(String consumesString) {
        String language = getLanguage(consumesString);

        this.mode = language.equals("binary") ? "binary" : "raw";
        this.raw = "";
        this.options = new RequestBodyOptions(language);
    }

    private String getLanguage(String consumesString) {
        if (consumesString.contains("json")) {
            return "json";
        }
        if (consumesString.contains("xml")) {
            return "xml";
        }

        switch (consumesString) {
            case MediaType.TEXT_HTML_VALUE:
                return "html";
            case MediaType.APPLICATION_XML_VALUE:
            case MediaType.TEXT_XML_VALUE:
                return "xml";
            case MediaType.APPLICATION_JSON_VALUE:
                return "json";
            case MediaType.IMAGE_GIF_VALUE:
            case MediaType.IMAGE_JPEG_VALUE:
            case MediaType.IMAGE_PNG_VALUE:
            case MediaType.APPLICATION_PDF_VALUE:
                return "binary";
            default:
                return "text";
        }
    }

    public class RequestBodyOptions {
        private RequestBodyOptionsRaw raw;

        public RequestBodyOptions(String language) {
            this.raw = new RequestBodyOptionsRaw(language);
        }

        public class RequestBodyOptionsRaw {
            private String language;

            public RequestBodyOptionsRaw(String language) {
                this.language = language;
            }
        }
    }
}
