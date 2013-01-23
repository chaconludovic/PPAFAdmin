package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library =
        {"jquery.stickem.js", "stickem.js"})
public class Stickem {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject();

        spec.put("elementId", element.getClientId());

        jsSupport.addInitializerCall("stickemConfiguration", spec);
    }
}