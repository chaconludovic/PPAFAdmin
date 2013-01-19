package com.eldoraludo.ppafadministration.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Client;

public class GestionClient {

	@Inject
	private Session session;

	@Property
	private Client client;

	public List<Client> getClients() {
		return session.createCriteria(Client.class).list();
	}

}
