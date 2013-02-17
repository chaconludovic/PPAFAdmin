package com.eldoraludo.ppafadministration.pages.piece;

import org.apache.tapestry5.annotations.Property;

import com.eldoraludo.ppafadministration.entities.Piece;

public class CreerModifierPiece {

	@Property
	private Long id;

	public void onActivate(long id) {
		this.id = id;
	}
}
