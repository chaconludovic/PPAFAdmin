package com.eldoraludo.ppafadministration.components;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.SelectModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateMidnight;

import com.eldoraludo.ppafadministration.entities.Article;
import com.eldoraludo.ppafadministration.entities.Client;
import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.entities.Piece;
import com.eldoraludo.ppafadministration.entities.TypePiece;
import com.eldoraludo.ppafadministration.pages.GestionPiece;
import com.eldoraludo.ppafadministration.support.AjaxLoopHolder;

//http://tapestry5-jquery.com/core/
// TODO TO TRY http://tawus.wordpress.com/2011/07/26/tapestry-ajaxformloop/
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
    @Persist
    private Piece pieceLie;
    @Property
    @Persist
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
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @InjectComponent(value = "saisiePieceFormZone")
    private Zone saisiePieceFormZone;
    @Inject
    private Messages messages;
    @InjectComponent
    private Form saisiePieceForm;
    @Property
    @Parameter(allowNull = true)
    private TypePiece typeSauvegarder;
    @InjectComponent
    private Zone typeFactureOuAvoirZone;
    @InjectComponent
    private Zone totalZone;
    @InjectComponent
    private Zone articlesZone;
    @InjectComponent
    @Property
    private ActionLink importerArticlesDepuisPieceLie;
    @Property
    private Boolean modeSaisieType;

    @SetupRender
    public void setupRender() {
        holder = new AjaxLoopHolder<FieldValue>();
        if (this.id != null) {
            this.piece = (Piece) session.load(Piece.class, id);
            this.typeSauvegarder = this.piece.getType();
            if (this.piece != null) {
                int i = 0;
                for (Article article_ : this.piece.getArticles()) {
                    FieldValue value = new FieldValue();
                    value.article = article_;
                    value.order = i++;
                    holder.add(value);
                }
            }
            if (estEnModeSaisieTypePiecePourModification(piece)) {
                modeSaisieType = true;
            } else {
                modeSaisieType = false;
            }

        } else {
            this.piece = new Piece();
            this.piece.setNumeroPiece(DateMidnight.now().toString("YYYYMMdd")
                                      + "_");
            this.piece.setDate(DateMidnight.now().toDate());
            modeSaisieType = true;
        }
    }

    private boolean estEnModeSaisieTypePiecePourModification(Piece piece) {
        TypePiece type = piece.getType();
        return type != null && (type.equals(TypePiece.Livraison) || type.equals(TypePiece.Depot));
    }

    @OnEvent(component = "importerArticlesDepuisPieceLie", value = EventConstants.ACTION)
    public void onActionFromImporterArticlesDepuisPieceLie() {
//        Piece pieceLie = (Piece) session.get(Piece.class, piece.getPieceLie().getId());
        List<Article> pieceLieArticles = session.createCriteria(Article.class).list();
        for (Article pieceLieArticle : pieceLieArticles) {
            if (!pieceLieArticle.getPiece().equals(pieceLie)) {
                continue;
            }
            FieldValue value = new FieldValue();
            value.order = getMaxIndexInHolder() + 1;
            Article article = new Article();
            article.setInfo(pieceLieArticle.getInfo());
            article.setItem(pieceLieArticle.getItem());
            article.setPieceDOrigine(pieceLieArticle.getPiece().prettyString());
            article.setPrixUnitaire(pieceLieArticle.getPrixUnitaire());
            article.setQuantite(pieceLieArticle.getQuantite());
            article.setRemise(pieceLieArticle.getRemise());
            article.setPiece(this.piece);
            value.article = article;
            holder.add(value);
            this.piece.getArticles().add(value.article);
        }
        ajaxResponseRenderer.addRender(articlesZone);
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "type")
    public void onValueChangedFromType() {
        ajaxResponseRenderer.addRender(typeFactureOuAvoirZone);
    }

    @OnEvent(value = EventConstants.VALUE_CHANGED, component = "pieceLie")
    public void onValueChangedFromPieceLie(Piece pieceLie) {
        this.pieceLie = pieceLie;
        ajaxResponseRenderer.addRender(typeFactureOuAvoirZone);
    }

    public boolean isTypeFactureOuAvoir() {
        if (piece != null) {
            if (piece.getType() != null && (piece.getType().equals(TypePiece.Facture) || piece.getType().equals(TypePiece.Avoir))) {
                return true;
            }
        }
        return false;
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "saisiePieceForm")
    void onValidateFromSaisiePieceForm() {

        System.out.println(this.piece.getType());
        System.out.println(this.typeSauvegarder);
        List<Piece> pieces = session.createCriteria(Piece.class).list();
        for (Piece piecePersiste : pieces) {
            if (!piecePersiste.getId().equals(piece.getId()) && piecePersiste.getNumeroPiece().equals(piece.getNumeroPiece())) {
                saisiePieceForm.recordError(messages
                                                    .get("saisiepiece.message.erreur.type.numeroPieceExistant"));
                break;
            }
        }
        if (this.typeSauvegarder != null) {
            if (!this.piece.getType().equals(TypePiece.Facture)
                && this.typeSauvegarder.equals(TypePiece.Facture)
                || !this.piece.getType().equals(TypePiece.Avoir)
                   && this.typeSauvegarder.equals(TypePiece.Avoir)) {
                saisiePieceForm.recordError(messages
                                                    .get("saisiepiece.message.erreur.type.factureavoir"));
            }
        }
        if (this.typeSauvegarder != null) {
            if (this.piece.getType().equals(TypePiece.Livraison)
                && this.typeSauvegarder.equals(TypePiece.Depot)
                || this.piece.getType().equals(TypePiece.Depot)
                   && this.typeSauvegarder.equals(TypePiece.Livraison)) {
                saisiePieceForm.recordError(messages
                                                    .get("saisiepiece.message.erreur.type.livraisondepot"));
            }
        }
        ajaxResponseRenderer.addRender(saisiePieceFormZone);
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "saisiePieceForm")
    @CommitAfter
    Object onSuccess() {
        if (piece.getId() == null) {
            session.persist(this.piece);
        } else {
            session.merge(this.piece);
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

    /*
     @InjectComponent
     private Zone prixUnitaireRemiserZone;
     @OnEvent(component = "quantite", value = UpdateZone.DEFAULT_EVENT)
     public void onChangeFromQuantite(
             @RequestParameter(value = "quantite", allowBlank = true) Double quantite,
             int i) {
         this.piece.getArticles().get(i).setQuantite(quantite);
         ajaxResponseRenderer.addRender("prixUnitaireRemiserZone_" + i, prixUnitaireRemiserZone)
                 .addRender("totalZone", totalZone);
     }

     @OnEvent(component = "remise", value = UpdateZone.DEFAULT_EVENT)
     public void onChangeFromRemise(
             @RequestParameter(value = "remise", allowBlank = true) Double remise,
             int i) {
         this.piece.getArticles().get(i).setRemise(remise);
         ajaxResponseRenderer.addRender("prixUnitaireRemiserZone_" + i, prixUnitaireRemiserZone)
                 .addRender("totalZone", totalZone);
     }

     @OnEvent(component = "prixUnitaire", value = UpdateZone.DEFAULT_EVENT)
     public void onChangeFromPrixUnitaire(
             @RequestParameter(value = "prixUnitaire", allowBlank = true) Double prixUnitaire,
             int i) {
         this.piece.getArticles().get(i).setPrixUnitaire(prixUnitaire);
         ajaxResponseRenderer.addRender("prixUnitaireRemiserZone_" + i, prixUnitaireRemiserZone)
                 .addRender("totalZone", totalZone);
     }*/

    /*public String getPrixUnitaireRemiserZoneId() {
         return "prixUnitaireRemiserZone_" + fieldValue.order;
     }*/

    public Double getTotal() {
        return piece.getTotal();
    }

    public SelectModel getListeItem() {
        List<Item> items = session.createCriteria(Item.class).list();
        List<OptionModel> options = CollectionFactory.newList();
        for (Item item : items) {
            options.add(new OptionModelImpl(item.toString(), item));
        }
        return new SelectModelImpl(null, options);
    }

    public SelectModel getListePieceLie() {
        List<Piece> pieceLies = session.createCriteria(Piece.class).add(Restrictions.or
                (Restrictions.eq("type", TypePiece.Depot), Restrictions.eq("type", TypePiece.Livraison))).addOrder(Order.asc("type")).list();
        List<OptionModel> options = CollectionFactory.newList();
        for (Piece pieceLie : pieceLies) {
            options.add(new OptionModelImpl(pieceLie.prettyString(), pieceLie));
        }
        return new SelectModelImpl(null, options);
    }

    public SelectModel getListeClient() {
        List<Client> clients = session.createCriteria(Client.class).list();
        return selectModelFactory.create(clients, "nom");
    }

    public SelectModel getListeTypePiece() {
        if (estEnModeSaisieTypePiecePourModification(piece)) {
            List<OptionModel> options = new ArrayList<OptionModel>();
            options.add(new OptionModelImpl(TypePiece.Facture.name(), TypePiece.Facture));
            options.add(new OptionModelImpl(TypePiece.Avoir.name(), TypePiece.Avoir));
            return new SelectModelImpl(null, options);
        } else {
            List<OptionModel> options = new ArrayList<OptionModel>();
            options.add(new OptionModelImpl(TypePiece.Depot.name(), TypePiece.Depot));
            options.add(new OptionModelImpl(TypePiece.Livraison.name(), TypePiece.Livraison));
            options.add(new OptionModelImpl(TypePiece.Facture.name(), TypePiece.Facture));
            options.add(new OptionModelImpl(TypePiece.Avoir.name(), TypePiece.Avoir));
            return new SelectModelImpl(null, options);
        }
    }

    public static class FieldValue implements Comparable<FieldValue> {

        @Validate("required")
        public Article article;
        public Integer order;

        // @InjectComponent
        // private Zone articlesZone;

        public int compareTo(FieldValue o) {
            if (this.order == null) {
                return 1;
            }
            if (o.order == null) {
                return -1;
            }
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