package com.eldoraludo.ppafadministration.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Piece {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	public Long id;

	@Validate("required")
	@ManyToOne
	public Client client;

	@Validate("required")
	public Date date;

	@Validate("required")
	public String numeroPiece;

	@Validate("required")
	public TypePiece type;

	public Double remise;

	public Double fraisPort;

	@Validate("required")
	public Double montant;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}