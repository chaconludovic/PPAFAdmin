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
import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.util.Filtres;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Import(stylesheet = { "context:css/general.css",
		"context:css/bst_framework.css" })
public class GestionItem {

	@Inject
	private Session session;

	@Property
	private Item item;

	@Persist
	@Property
	private String filtre;

	@Persist
	@Property
	private BeanModel<Item> model;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;

	@SetupRender
	public void setUp() {
		model = beanModelSource.createDisplayModel(Item.class, messages);
		model.include("designation", "ean13", "prixUnitaire");

	}

	public Collection<Item> getItems() {
		List<Item> list = session.createCriteria(Item.class).list();
		return StringUtils.isBlank(filtre) ? list : Collections2.filter(list,
				new Predicate<Item>() {
					public boolean apply(Item item) {
						return Filtres.matchesAny(filtre, item.getDesignation());
					}
				});
	}

}
