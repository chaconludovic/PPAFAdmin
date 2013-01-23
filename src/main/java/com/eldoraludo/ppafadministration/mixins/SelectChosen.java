package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

import java.util.Map;

@ImportJQueryUI({
        "jquery.ui.core"})
@Import(library = {"chosen.jquery.js", "selectChosen.js"},
        stylesheet = {"context:css/chosen.css"})
public class SelectChosen {


    @Parameter(required = false, defaultPrefix = BindingConstants.PROP)
    private Map<String, String> parameters;

    @Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
    private String message;

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private boolean allowDeselect = true;

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @Inject
    private Messages messages;


    @AfterRender
    void afterRender() {
        if (message == null) {
            message = messages.get("select.chosen.default");
        }

        JSONObject spec = new JSONObject()
                .put("elementId", element.getClientId())
                .put("placeholder", message)
                .put("deselect", allowDeselect);
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                spec.put(key, parameters.get(key));
            }
        }

        jsSupport.addInitializerCall("setupSelectChosen", spec);
    }

}