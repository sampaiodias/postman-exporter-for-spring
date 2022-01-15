package io.github.sampaiodias.collectionmodel;

import java.util.Collection;
import java.util.List;

public class RequestScriptData {
    private String name;
    private List<String> script;

    public RequestScriptData(String name, List<String> script) {
        this.name = name;
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public List<String> getScript() {
        return script;
    }
}
