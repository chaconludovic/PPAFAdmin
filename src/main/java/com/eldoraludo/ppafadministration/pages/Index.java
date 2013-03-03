package com.eldoraludo.ppafadministration.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Piece;

/**
 * Start page of application ppafadministration.
 */
@Import(library={"context:js/demo.js"})
public class Index {
	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	public Date getCurrentTime() {
		return new Date();
	}
    @Inject
    private JavaScriptSupport javascript;

    public JSONObject getOptions(){
        JSONObject opt = new JSONObject();
        opt.put("text", "Source: WorldClimate.com");
        opt.put("x", -20);

        JSONObject high = new JSONObject();
        high.put("subtitle", opt);

        return high;
    }

    @AfterRender
    public void afterRender(){
        javascript.addInitializerCall(InitializationPriority.EARLY, "index", new JSONObject());
    }
}
