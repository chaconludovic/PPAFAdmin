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
        {"jquery.blockUI.js", "block-ui.js"})
public class BlockUI {

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String message;

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private Form form;

    @Parameter(name = "unblock-zone",
            required = false,
            defaultPrefix = BindingConstants.COMPONENT)
    private Zone zone;

    @Parameter(name = "divToBlock",
            required = false,
            defaultPrefix = BindingConstants.LITERAL)
    private String divToBlock;

    @Parameter(name = "growlTitle",
            required = false,
            defaultPrefix = BindingConstants.LITERAL)
    private String growlTitle;

    @Parameter(name = "growlMessage",
            required = false,
            defaultPrefix = BindingConstants.LITERAL)
    private String growlMessage;


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
        if (divToBlock != null){
            spec.put("divblocked", divToBlock);
        }
        if (growlTitle != null){
            spec.put("growlTitle", growlTitle);
        }
        if (growlMessage != null){
            spec.put("growlMessage", growlMessage);
        }
        jsSupport.addInitializerCall("blockUI", spec);
    }

}
