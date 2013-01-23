package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library =
        {"stateful-ui.js"})
public class StatefulUI {

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private Form form;

    @Parameter(name = "unstate-zone",
            required = false,
            defaultPrefix = BindingConstants.COMPONENT)
    private Zone zone;

    @Parameter(name = "before-validation",required = false)
    private Boolean beforeValidation = false;

    @Parameter(value = "dlgId", defaultPrefix = BindingConstants.LITERAL)
    private String dlgId;

    @Inject
    private ComponentResources resources;

    @AfterRender
    void configureAutocomplete() {
        JSONObject spec = new JSONObject()
                .put("formId", form.getClientId());
        if (message != null) {
            spec.put("message", message);
        }
        if (zone != null){
            spec.put("zoneId", zone.getClientId());
        }
        spec.put("beforeValidation", beforeValidation);
        spec.put("dlgId", dlgId);

        jsSupport.addInitializerCall("statefulUI", spec);
    }
}