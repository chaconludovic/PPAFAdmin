package com.eldoraludo.ppafadministration.pages.fournisseur;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Fournisseur;
import com.eldoraludo.ppafadministration.pages.GestionFournisseur;

public class CreeModifierFournisseur {

	@Property
	private Fournisseur fournisseur;

	@Inject
	private Session session;

	@InjectPage
	private GestionFournisseur gestionFournisseur;

	Fournisseur onPassivate() {
		return fournisseur;
	}

	void onActivate(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	@CommitAfter
	Object onSuccess() {
		session.persist(fournisseur);

		return gestionFournisseur;
	}

}
