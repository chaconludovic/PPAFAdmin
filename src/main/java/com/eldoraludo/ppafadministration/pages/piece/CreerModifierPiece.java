package com.eldoraludo.ppafadministration.pages.piece;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.SelectModelFactory;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Client;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.pages.GestionPiece;
import com.eldoraludo.ppafadministration.pages.Index;

public class CreerModifierPiece {

	@Property
	private Piece piece;

	@Inject
	private Session session;

	@InjectPage
	private GestionPiece gestionPiece;

	@Property
	private SelectModel clientSelectModel;

	@Inject
	private SelectModelFactory selectModelFactory;

	void setupRender() {
		List<Client> clients = session.createCriteria(Client.class).list();
		clientSelectModel = selectModelFactory.create(clients, "nom");
	}

	Piece onPassivate() {
		return piece;
	}

	void onActivate(Piece piece) {
		this.piece = piece;
	}

	@CommitAfter
	Object onSuccess() {
		session.persist(piece);

		return gestionPiece;
	}

}
