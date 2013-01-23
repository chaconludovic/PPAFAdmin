package com.eldoraludo.ppafadministration.mixins;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.io.IOException;
import java.util.List;

@Import(library = "update-zone-client.js")
public class UpdateZoneOnClientEvent {

    @Parameter(defaultPrefix = BindingConstants.LITERAL, required=true)
    private List<String> clientEvents;

    @Inject
    private ComponentResources resources;

    @Environmental
    private JavaScriptSupport jsSupport;

    @Environmental
    private TrackableComponentEventCallback eventCallback;

    @Inject
    private Environment environment;

    @InjectContainer
    private ClientElement element;

    void afterRender() {
        String clientId = element.getClientId();

        JSONObject spec = new JSONObject();
        spec.put("element", clientId);
        JSONArray events = new JSONArray();
        for (String clientEvent : clientEvents) {
            events.put(new JSONObject("event", clientEvent,
                    "url", resources.createEventLink(EventConstants.ACTION, clientEvent).toAbsoluteURI()));
        }

        spec.put("events", events);

        jsSupport.addInitializerCall("updateZoneOnClientEvent", spec);
    }


    @OnEvent(EventConstants.ACTION)
    Object onAction(EventContext context) throws IOException {
        resources.triggerContextEvent(context.get(String.class, 0), context, eventCallback);
        if (eventCallback.isAborted()) {
            return null;
        }
        return getBody();
    }

     public Block getBody() {
        return resources.getBody();
    }

}