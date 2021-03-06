package com.eldoraludo.ppafadministration.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

@Entity
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonVisual
    private Long id;

    @Validate("required")
    @ManyToOne
    private Client client;

    @Validate("required")
    private Date date;

    @Validate("required")
    private String numeroPiece;

    @ManyToOne
    private Piece pieceLie;

    private String commentaire;

    @Validate("required")
    private TypePiece type;

    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<Article>();

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public TypePiece getType() {
        return type;
    }

    public void setType(TypePiece type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Piece getPieceLie() {
        return pieceLie;
    }

    public void setPieceLie(Piece pieceLie) {
        this.pieceLie = pieceLie;
    }

    public Double getTotal() {
        Double total = 0.0;
        for (Article article : articles) {
            total += article.getTotal();
        }
        return total;
    }

    public String prettyString() {
        return new StringBuilder(numeroPiece).append(" - ").append(type).toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        Piece that = (Piece) o;

        if (this.getNumeroPiece() == null && that.getNumeroPiece() != null ||
                this.getNumeroPiece() != null && that.getNumeroPiece() == null) {
            return false;
        }

        if (this.getNumeroPiece() == null && that.getNumeroPiece() == null) {
            return true;
        }

        if (!this.getNumeroPiece().equals(that.getNumeroPiece())) {
            return false;
        }

        if (this.getId() == null || that.getId() == null) {
            return this == o;
        }

        return this.getId().equals(that.getId());
    }
}
