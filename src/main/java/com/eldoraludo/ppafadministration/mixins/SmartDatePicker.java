package com.eldoraludo.ppafadministration.mixins;

import com.eldoraludo.ppafadministration.util.DateFormat;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.Date;
import java.util.Map;

@Import(library = { "smart-datepicker.js" })
public class SmartDatePicker {

	@Parameter(required = false, defaultPrefix = BindingConstants.PROP)
	private Map<String, String> parameters;

	@Environmental
	private JavaScriptSupport jsSupport;

	@InjectContainer
	private ClientElement element;

	@Inject
	private Messages messages;

	@AfterRender
	void afterRender() {
		DateFormat df = DateFormat.FR_SLASH;

		JSONObject spec = new JSONObject();
		spec.put("elementId", element.getClientId());
		spec.put("dateAnalyseTime", new Date().getTime());
		spec.put("dateFormat", df.getJqueryFormat());
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				spec.put(key, parameters.get(key));
			}
		}

		jsSupport.addInitializerCall("setupSmartDatepicker", spec);
	}

}
