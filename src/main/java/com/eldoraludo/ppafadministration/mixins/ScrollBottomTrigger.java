package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@Import(library = {"scrollBottomTrigger.js"})
public class ScrollBottomTrigger {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject()
                .put("elementId", element.getClientId());
        jsSupport.addInitializerCall("scrollBottomTrigger", spec);
    }


}