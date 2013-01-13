package com.eldoraludo.ppafadministration.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Piece;

/**
 * Start page of application ppafadministration.
 */
public class Index {
	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@InjectComponent
	private Zone zone;

	@Persist
	@Property
	private int clickCount;

	@Inject
	private AlertManager alertManager;

	public Date getCurrentTime() {
		return new Date();
	}

	void onActionFromIncrement() {
		alertManager.info("Increment clicked");

		clickCount++;
	}

	Object onActionFromIncrementAjax() {
		clickCount++;

		alertManager.info("Increment (via Ajax) clicked");

		return zone;
	}
}
