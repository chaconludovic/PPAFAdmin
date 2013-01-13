package com.eldoraludo.ppafadministration.pages.piece;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.pages.GestionPiece;
import com.eldoraludo.ppafadministration.pages.Index;

public class CreePiece {

	@Property
	private Piece piece;

	@Inject
	private Session session;

	@InjectPage
	private GestionPiece gestionPiece;

	@CommitAfter
	Object onSuccess() {
		session.persist(piece);

		return gestionPiece;
	}

}
