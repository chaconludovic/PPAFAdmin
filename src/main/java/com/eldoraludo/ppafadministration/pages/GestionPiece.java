package com.eldoraludo.ppafadministration.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Piece;

public class GestionPiece {

	@Inject
	private Session session;

	@Property
	private Piece piece;

	public List<Piece> getPieces() {
		return session.createCriteria(Piece.class).list();
	}

}
