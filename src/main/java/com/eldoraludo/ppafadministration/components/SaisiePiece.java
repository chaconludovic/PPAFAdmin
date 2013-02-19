package com.eldoraludo.ppafadministration.components;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

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
import com.eldoraludo.ppafadministration.pages.GestionPiece;
import com.eldoraludo.ppafadministration.support.AjaxLoopHolder;

//http://tapestry5-jquery.com/core/docsajaxformloop
//http://tawus.wordpress.com/2011/07/26/tapestry-ajaxformloop/
//http://jumpstart.doublenegative.com.au/jumpstart/examples/ajax/formloop1
//http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/corelib/components/AjaxFormLoop.html
// TODO voir pourquoi on doit ajouter une ligne dans le holder et dans l'objet
public class SaisiePiece {

	@Parameter(required = false)
	private Long id;

	@Property
	@Persist
	private Piece piece;

	@Property
	private FieldValue fieldValue;

	@Inject
	private Session session;

	@InjectPage
	private GestionPiece gestionPiece;

	@Inject
	private SelectModelFactory selectModelFactory;

	@Property
	@Persist
	private AjaxLoopHolder<FieldValue> holder;

	@SetupRender
	public void setupRender() {
		holder = new AjaxLoopHolder<FieldValue>();
		if (this.id != null) {
			this.piece = (Piece) session.load(Piece.class, id);
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
		}
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "pieceForm")
	@CommitAfter
	Object onSuccess() {
		if (piece.getId() == null) {
			session.persist(this.piece);
		} else {
			session.update(this.piece);
		}
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