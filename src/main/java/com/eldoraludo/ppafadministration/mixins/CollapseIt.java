package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.jquery.ImportJQueryUI;

@ImportJQueryUI({
        "jquery.ui.core"})
@Import(library = {"jquery.collapse.js","jquery.collapse_cookie_storage.js","jquery.collapse_storage.js", "collapseIt.js"})
public class CollapseIt {

    @Environmental
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;


    @AfterRender
    void afterRender() {
        JSONObject spec = new JSONObject()
                .put("elementId", element.getClientId());
        jsSupport.addInitializerCall("setupCollapseIt", spec);
    }

}