package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = { "form-validation-behaviour.js" })
public class FormValidationBehaviour {

    @InjectContainer
    private Form form;

    @Environmental
    private JavaScriptSupport jsSupport;

    @AfterRender
    void configureAutocomplete() {
        JSONObject spec = new JSONObject().put("formId", form.getClientId());
        jsSupport.addInitializerCall(InitializationPriority.EARLY, "configureValidation", spec);
    }
}