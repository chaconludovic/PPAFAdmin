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

@Import(library = {"jquery.tablescroll.js", "tablescroll.js" })
public class TableScroll {

    @Inject
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @Parameter(required = true, defaultPrefix = BindingConstants.MESSAGE)
    private String tableId;

    @AfterRender
    void configureTableScroll() {
        JSONObject spec = new JSONObject().put("clientId", element.getClientId()).put("tableId", tableId);
        jsSupport.addInitializerCall("configureTableScroll", spec);
    }

}