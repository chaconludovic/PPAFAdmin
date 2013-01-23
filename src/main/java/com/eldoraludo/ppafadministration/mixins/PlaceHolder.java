package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"jquery.watermark.js", "watermark.js" })
public class PlaceHolder {

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String placeholder;

    @Inject
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @AfterRender
    void configurePlaceholder() {
        JSONObject spec = new JSONObject().put("clientId", element.getClientId()).put("placeholder", placeholder);
        jsSupport.addInitializerCall("configureWatermark", spec);
    }

}