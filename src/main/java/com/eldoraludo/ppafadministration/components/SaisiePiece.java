package com.eldoraludo.ppafadministration.components;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;
import org.hibernate.Session;

import com.eldoraludo.ppafadministration.entities.Article;
import com.eldoraludo.ppafadministration.entities.Client;
import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.entities.TypePiece;
import com.eldoraludo.ppafadministration.pages.GestionPiece;
import com.eldoraludo.ppafadministration.support.AjaxLoopHolder;

//http://tapestry5-jquery.com/core/docsajaxformloop
//http://tawus.wordpress.com/2011/07/26/tapestry-ajaxformloop/
//http://jumpstart.doublenegative.com.au/jumpstart/examples/ajax/formloop1
//http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/corelib/components/AjaxFormLoop.html

public class SaisiePiece {

	@Parameter(required = false)
	private Long id;

	@Property
	@Persist
	// @Parameter(required = false)
	private Piece piece; // TODO voir pourqyoi on porurait pas utiliser
							// @Parameter pour la modification

	@Property
	private FieldValue fieldValue;

	@Inject
	private Session session;

	@InjectPage
	private GestionPiece gestionPiece;

	@Property
	@Persist
	@Validate("required")
	private Date date;

	@Property
	@Persist
	@Validate("required")
	private String numeroPiece;

	@Property
	@Persist
	@Validate("required")
	private TypePiece type;

	@Property
	@Persist
	@Validate("required")
	private Client client;

	@Inject
	private SelectModelFactory selectModelFactory;

	@Property
	@Persist
	private AjaxLoopHolder<FieldValue> holder;

	// public void onActivate(long id) {
	//
	// }

	@SetupRender
	public void setupRender() {
		holder = new AjaxLoopHolder<FieldValue>();
		if (this.id != null) {
			this.piece = (Piece) session.load(Piece.class, id);
			this.client = this.piece.getClient();
			this.date = this.piece.getDate();
			this.numeroPiece = this.piece.getNumeroPiece();
			this.type = this.piece.getType();
			if (this.piece != null) {
				int i = 0;
				for (Article article_ : this.piece.getArticles()) {
					FieldValue value = new FieldValue();
					value.article = article_;
					value.order = i++;
					holder.add(value);
				}
			}
		} else {
			this.piece = new Piece();
			this.client = null;
			this.date = null;
			this.numeroPiece = null;
			this.type = null;
		}
	}

	// public void onActivate(Piece piece) {
	//
	// }

	//
	// @SetupRender
	// void setupRender() {
	// if (this.piece != null) {
	// this.client = this.piece.getClient();
	// this.date = this.piece.getDate();
	// this.numeroPiece = this.piece.getNumeroPiece();
	// this.type = this.piece.getType();
	// }
	// if (holder == null) {
	// holder = new AjaxLoopHolder<FieldValue>();
	// if (this.piece != null) {
	// int i = 0;
	// for (Article article_ : this.piece.getArticles()) {
	// FieldValue value = new FieldValue();
	// value.article = article_;
	// value.order = i++;
	// holder.add(value);
	// }
	// }
	// }
	// }

	@OnEvent(value = EventConstants.SUCCESS, component = "pieceForm")
	@CommitAfter
	Object onSuccess() {
		// if (this.piece == null) {
		// this.piece = new Piece();
		// } else {
		// // this.piece = (Piece) session.merge(this.piece);
		// }
		this.piece.setClient(client);
		this.piece.setDate(date);
		this.piece.setType(type);
		this.piece.setNumeroPiece(numeroPiece);
		// for (FieldValue value : holder.getValues()) {
		// Article article = value.article;
		// article.setPiece(this.piece);
		// // article = (Article) session.merge(article);
		// this.piece.getArticles().add(article);
		// }
		// session.update(piece);
		session.update(this.piece);
		//session.persist(this.piece);
		//this.piece = (Piece) session.load(Piece.class, 1l);
		return gestionPiece;
	}

	@OnEvent(value = EventConstants.ADD_ROW, component = "articlesLoop")
	Object onAddRowFromArticlesLoop() {
		FieldValue value = new FieldValue();
		value.order = getMaxIndexInHolder() + 1;
		value.article = new Article();
		value.article.setPiece(this.piece);
		holder.add(value);
		this.piece.getArticles().add(value.article);
		return value;
	}

	public Integer getMaxIndexInHolder() {
		Integer maxIndex = 0;
		if (holder != null) {
			for (FieldValue value : holder.getValues()) {
				if (value.order >= maxIndex) {
					maxIndex = value.order;
				}
			}
		}
		return maxIndex;
	}

	@OnEvent(value = EventConstants.REMOVE_ROW, component = "articlesLoop")
	@CommitAfter
	void onRemoveRowFromArticlesLoop(FieldValue value) {
		Article articleToDelete = value.article;
		this.piece.getArticles().remove(articleToDelete);
		holder.remove(value);
		session.delete(articleToDelete);
	}

	// @OnEvent(component = "prixUnitaire", value = UpdateZone.DEFAULT_EVENT)
	// public void onChangeFromPrixUnitaire(
	// @RequestParameter(value = "article", allowBlank = true) Article article,
	// @RequestParameter(value = "prixUnitaire", allowBlank = true) Double
	// prixUnitaire) {
	// if (article != null) {
	// article.setPrixUnitaire(prixUnitaire);
	// }
	// ajaxResponseRenderer.addRender(totalZone);
	// }
	//
	// @OnEvent(component = "remise", value = UpdateZone.DEFAULT_EVENT)
	// public void onChangeFromRemise(
	// @RequestParameter(value = "article", allowBlank = true) Article article,
	// @RequestParameter(value = "remise", allowBlank = true) Double remise) {
	// if (article != null) {
	// article.setRemise(remise);
	// }
	// ajaxResponseRenderer.addRender(totalZone);
	// }

	public SelectModel getListeItem() {
		List<Item> items = session.createCriteria(Item.class).list();
		return selectModelFactory.create(items, "designation");
	}

	public SelectModel getListeClient() {
		List<Client> clients = session.createCriteria(Client.class).list();
		return selectModelFactory.create(clients, "nom");
	}

	public static class FieldValue implements Comparable<FieldValue> {

		@Validate("required")
		public Article article;

		public Integer order;

		public int compareTo(FieldValue o) {
			if (this.order == null)
				return 1;
			if (o.order == null)
				return -1;
			return this.order.compareTo(o.order);
		}

		@Override
		public boolean equals(Object o) {
			return reflectionEquals(this, o);
		}

		@Override
		public int hashCode() {
			return reflectionHashCode(this);
		}

		@Override
		public String toString() {
			return reflectionToString(this);
		}
	}

}