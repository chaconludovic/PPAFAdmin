package com.eldoraludo.ppafadministration.pages;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Client;
import com.eldoraludo.ppafadministration.entities.Fournisseur;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.util.Filtres;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Import(stylesheet = { "context:css/general.css",
		"context:css/bst_framework.css" })
public class GestionFournisseur {

	@Inject
	private Session session;

	@Property
	private Fournisseur fournisseur;

	@Persist
	@Property
	private String filtre;

	@Persist
	@Property
	private BeanModel<Fournisseur> model;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;

	@SetupRender
	public void setUp() {
		model = beanModelSource.createDisplayModel(Fournisseur.class, messages);
		// model.add("client").label("client").sortable(true);
		model.include("nom", "rue", "ville", "codePostal");

	}

	public Collection<Fournisseur> getFournisseurs() {
		List<Fournisseur> list = session.createCriteria(Fournisseur.class).list();
		return StringUtils.isBlank(filtre) ? list : Collections2.filter(list,
				new Predicate<Fournisseur>() {
					public boolean apply(Fournisseur fournisseur) {
						return Filtres.matchesAny(filtre, fournisseur.getNom());
					}
				});
	}

}
