package com.eldoraludo.ppafadministration.pages.piece;

import com.eldoraludo.ppafadministration.entities.Article;
import com.eldoraludo.ppafadministration.entities.Piece;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;
import org.joda.time.DateMidnight;

import java.util.Collection;

public class FichePiece {

    @Property
    @ActivationRequestParameter(value = "pieceId")
    private Long pieceId;

    @Inject
    private Session session;

    @Property
    private Article article;

    @Property
    private Piece piece;

    @Persist
    @Property
    private BeanModel<Article> model;

    @Inject
    private Messages messages;

    @Inject
    private BeanModelSource beanModelSource;

    void onActivate() {
        piece = (Piece) session.get(Piece.class, pieceId);
    }

    @SetupRender
    public void setUp() {
        model = beanModelSource.createDisplayModel(Article.class, messages);
        model.add("item").label("Item");
        model.include("item", "quantite", "prixUnitaire", "remise", "total");

    }


    public String getDateDeLaPiece() {
        return new DateMidnight(piece.getDate()).toString("dd/MM/yyyy");
    }


    public Collection<Article> getArticles() {
        return piece.getArticles();
    }


}
