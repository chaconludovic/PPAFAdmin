package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "update-zone.js")
public class UpdateZone {

    public static final String DEFAULT_EVENT = "change";

    @Inject
    private ComponentResources resources;

    @Environmental
    private JavaScriptSupport jsSupport;

    /**
     * The event to listen for on the client. If not specified, zone update can only be triggered manually through
     * calling updateZone on the JS object.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
    private String clientEvent = DEFAULT_EVENT;

    /**
     * The event to listen for in your component class Defaults to clientEvent
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
    private String event;

    /**
     * The name of the request paramter sent to the event listener
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = false)
    private String paramName;

    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "default")
    private String prefix;

    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "0")
    private long delay;

    /**
     * The element we attach ourselves to
     */
    @InjectContainer
    private ClientElement element;

    @Parameter
    private Object[] context;

    /**
     * The zone to be updated by us.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
    private String zone;

    void afterRender() {
        if (zone != null) {
            Link link = resources.createEventLink(event == null ? clientEvent : event, context);
            String elementId = element.getClientId();
            JSONObject spec = new JSONObject();
            spec.put("url", link.toURI());
            spec.put("elementId", elementId);
            spec.put("clientEvent", clientEvent);
            spec.put("zoneId", zone);
            spec.put("delay", delay);
            spec.put("paramName", paramName == null ? resources.getContainerResources().getId() : paramName);

            jsSupport.addInitializerCall("linkElementToZone", spec);
        }
    }
}
