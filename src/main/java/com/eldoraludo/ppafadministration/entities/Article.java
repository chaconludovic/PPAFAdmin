package com.eldoraludo.ppafadministration.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Article implements Comparable<Article> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	private Long id;

	@Validate("required")
	@ManyToOne(optional = false)
	private Item item;

	private String info;

	private Double quantite;

	@Validate("required")
	private Double prixUnitaire;

	private Double remise;

	@Validate("required")
	@ManyToOne(optional = false)
	private Piece piece;

	public Double getPrixUnitaireRemiser() {
		if (remise != null) {
			return (prixUnitaire - prixUnitaire * remise / 100);
		} else {
			return prixUnitaire;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public Double getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Double getTotal() {
		return getPrixUnitaireRemiser() * quantite;
	}

	public int compareTo(Article o) {
		// TODO Auto-generated method stub
		return this.info.compareTo(o.info);
	}

}