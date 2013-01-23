package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = {"submit-on-enter.js" })
public class SubmitOnEnter {




    @Parameter(required = false)
    private ClientElement elementCible;

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zoneCible;

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String eventCible;

    @Inject
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @Inject
	private ComponentResources resources;

    @AfterRender
    void configureSubmitOnEnter() {
        JSONObject spec = new JSONObject()
                .put("clientId", element.getClientId())
                .put("zoneCible", zoneCible)
                .put("url", resources.createEventLink(eventCible).toAbsoluteURI());
        jsSupport.addInitializerCall("configureSubmitOnEnter", spec);
    }

}