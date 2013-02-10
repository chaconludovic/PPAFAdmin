package com.eldoraludo.ppafadministration.pages.item;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.pages.GestionItem;

public class CreeModifierItem {

	@Property
	private Item item;

	@Inject
	private Session session;

	@InjectPage
	private GestionItem gestionItem;

	Item onPassivate() {
		return item;
	}

	void onActivate(Item item) {
		this.item = item;
	}

	@CommitAfter
	Object onSuccess() {
		session.persist(item);

		return gestionItem;
	}

}
