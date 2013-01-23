package com.eldoraludo.ppafadministration.mixins;


import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"updateCalendarOnScroll.js"})
public class UpdateCalendarOnScroll {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject()
                .put("clientId", element.getClientId());
        jsSupport.addInitializerCall("updateCalendarOnScroll", spec);
    }


}