package io.github.sampaiodias.collectionmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestEvent {
    private String listen;
    private RequestEventScript script;

    public RequestEvent(RequestScriptType scriptType, List<String> scripts) {
        this.listen = scriptType.text;
        this.script = new RequestEventScript(scripts);
    }

    public class RequestEventScript {
        private String type = "text/javascript";
        @JsonProperty("exec")
        private List<String> scripts;

        public RequestEventScript(List<String> scripts) {
            this.scripts = scripts;
        }
    }

    public enum RequestScriptType {
        PRE_REQUEST("prerequest"),
        TEST("test");

        private String text;

        RequestScriptType(String text) {
            this.text = text;
        }
    }
}
