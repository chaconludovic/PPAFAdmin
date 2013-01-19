package com.eldoraludo.ppafadministration.pages.client;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Client;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.pages.GestionClient;

public class CreeModifierClient {

	@Property
	private Client client;

	@Inject
	private Session session;

	@InjectPage
	private GestionClient gestionClient;

	Client onPassivate() {
		return client;
	}

	void onActivate(Client client) {
		this.client = client;
	}

	@CommitAfter
	Object onSuccess() {
		session.persist(client);

		return gestionClient;
	}

}
