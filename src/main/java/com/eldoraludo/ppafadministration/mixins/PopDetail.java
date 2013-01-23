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
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library =
        {"jquery.qtip.js", "popdetail.js"})
public class PopDetail {

    @Inject
    private JavaScriptSupport jsSupport;

    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;

    @Inject
    private PageRenderLinkSource linkSource;

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageName;

    @Parameter(required = false, defaultPrefix = BindingConstants.PROP)
    private Object contextdetail;

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String titlePop;

    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String classesPop;

    @AfterRender
    void configurePopDetail() {
        String ajaxLink;
        if (contextdetail != null) {
            ajaxLink = linkSource.createPageRenderLinkWithContext(pageName, contextdetail).toURI();
        } else {
            ajaxLink = linkSource.createPageRenderLink(pageName).toURI();
        }

        JSONObject spec = new JSONObject()
                .put("clientId", element.getClientId())
                .put("text", "Chargement en cours")
                .put("title", titlePop)
                .put("classes", classesPop)
                .put("ajax", ajaxLink);

        jsSupport.addInitializerCall("configurePopDetail", spec);
    }
}