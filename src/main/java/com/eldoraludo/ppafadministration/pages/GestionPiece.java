package com.eldoraludo.ppafadministration.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Piece;

@Import(stylesheet = "context:general.css")
public class GestionPiece {

	@Inject
	private Session session;

	@Property
	private Piece piece;

	public List<Piece> getPieces() {
		return session.createCriteria(Piece.class).list();
	}

	public Double getPrixRemise() {
		if (piece.getRemise() != null) {
			return (piece.getPrixUnitaire() - piece.getPrixUnitaire()
					* piece.getRemise() / 100);
		} else {
			return null;
		}
	}

	public Double getTotal() {
		if (piece.getRemise() != null) {
			return (piece.getPrixUnitaire() - piece.getPrixUnitaire()
					* piece.getRemise() / 100)
					* piece.getQuantite();
		} else {
			return piece.getPrixUnitaire() * piece.getQuantite();
		}
	}
}
