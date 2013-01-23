package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"confirm.js"})
public class Confirm {
    @Parameter(value = "Etes-vous sur de vouloir effectuer cette action ?", defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject();

        spec.put("elementId", element.getClientId());
        spec.put("message", message);

        jsSupport.addInitializerCall("setupConfirm", spec);
    }
}