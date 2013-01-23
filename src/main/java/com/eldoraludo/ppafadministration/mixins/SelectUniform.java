package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

import java.util.Map;

@ImportJQueryUI({
        "jquery.ui.core"})
@Import(library = {"jquery.uniform.min.js", "selectUniform.js"})
public class SelectUniform {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;


    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject()
                .put("elementId", element.getClientId());
        jsSupport.addInitializerCall("setupSelectUniform", spec);
    }

}