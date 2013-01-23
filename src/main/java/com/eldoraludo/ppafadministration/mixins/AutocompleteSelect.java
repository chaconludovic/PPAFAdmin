package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI({"jquery.ui.core", "jquery.ui.widget", "jquery.ui.button", "jquery.ui.autocomplete"})
@Import(library = {"jquery.automplete.select.js", "autocompleteSelect.js", "jquery.watermark.js", "watermark.js"},
        stylesheet = {"context:css/devise-auto.css"})
public class AutocompleteSelect {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String placeholder;

    @Parameter(required = false,value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean focus;

    @Parameter(required = false,value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean newItem;

    @Parameter(required = false,value = "true", defaultPrefix = BindingConstants.LITERAL)
    private boolean deleteChoice;

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String styleCss;

    @Parameter(required = false,value = "false", defaultPrefix = BindingConstants.LITERAL)
    private boolean useCssValue;


    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject()
                .put("elementId", element.getClientId())
                .put("styleCss", styleCss)
                .put("focused", focus)
                .put("newItem", newItem)
                .put("useCssValue", useCssValue)
                .put("deletechoice", deleteChoice)
                .put("placeholder", placeholder);
        if (useCssValue) {
            jsSupport.addInitializerCall("autocompleteSelectWithCss", spec);
        } else {
            jsSupport.addInitializerCall("autocompleteSelect", spec);
        }

    }


}