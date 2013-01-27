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
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.util.Filtres;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Import(stylesheet = "context:css/general.css")
public class GestionClient {

	@Inject
	private Session session;

	@Property
	private Client client;

	@Property
	private Piece clientPiece;

	@Persist
	@Property
	private String filtre;

	@Persist
	@Property
	private BeanModel<Piece> model;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;

	@SetupRender
	public void setUp() {
		model = beanModelSource.createDisplayModel(Piece.class, messages);
		model.add("client").label("client").sortable(true);
		model.include("client", "numeroPiece", "date");

	}

	public List<Client> getClients() {
		return session.createCriteria(Client.class).list();
	}

	public Collection<Piece> getClientPieces() {
		List<Piece> list = session.createCriteria(Piece.class).list();
		return StringUtils.isBlank(filtre) ? list : Collections2.filter(list,
				new Predicate<Piece>() {
					public boolean apply(Piece piece) {
						return Filtres.matchesAny(filtre, piece.getClient()
								.getNom(), piece.getDesignation());
					}
				});
	}
}
