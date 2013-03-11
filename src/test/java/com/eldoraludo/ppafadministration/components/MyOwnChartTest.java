package com.eldoraludo.ppafadministration.components;

import com.eldoraludo.ppafadministration.entities.Article;
import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.entities.Piece;
import org.apache.tapestry5.json.JSONArray;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyOwnChartTest {

    @Test
    public void recuperer_les_donnees() {
        MyOwnChart myOwnChart = new MyOwnChart();
        List<Piece> pieces = new ArrayList<Piece>();
        Item item1 = new Item();
        item1.setDesignation("ITEM 1");
        Item item2 = new Item();
        item2.setDesignation("ITEM 2");

        getPiece1(pieces, item1, item2);
        getPiece2(pieces, item1, item2);
        getPiece3(pieces, item1, item2);
        Map<Item, Map<String, Integer>> itemMontantArticlesMap = myOwnChart.getItemMontantArticlesMap(pieces);
        Assert.assertEquals(4, itemMontantArticlesMap.get(item1).get("janvier 2000").intValue());
        Assert.assertEquals(6, itemMontantArticlesMap.get(item1).get("mai 2001").intValue());
        Assert.assertEquals(9, itemMontantArticlesMap.get(item2).get("janvier 2000").intValue());
        Assert.assertEquals(4, itemMontantArticlesMap.get(item2).get("mai 2001").intValue());

    }

    private void getPiece1(List<Piece> pieces, Item item1, Item item2) {
        Piece piece1 = new Piece();
        List<Article> articles = new ArrayList<Article>();
        Article article1 = new Article();
        article1.setPiece(piece1);
        article1.setPrixUnitaire(2.0);
        article1.setQuantite(1.0);
        article1.setRemise(0.0);
        article1.setItem(item1);
        articles.add(article1);

        Article article2 = new Article();
        article2.setPiece(piece1);
        article2.setPrixUnitaire(3.0);
        article2.setQuantite(2.0);
        article2.setRemise(0.0);
        article2.setItem(item2);
        articles.add(article2);
        piece1.setArticles(articles);
        piece1.setDate(DateTime.parse("2000-01-01T12:00:00.000+01:00").toDate());
        pieces.add(piece1);
    }
    private void getPiece2(List<Piece> pieces, Item item1, Item item2) {
        Piece piece2 = new Piece();
        List<Article> articles = new ArrayList<Article>();
        Article article1 = new Article();
        article1.setPiece(piece2);
        article1.setPrixUnitaire(3.0);
        article1.setQuantite(2.0);
        article1.setRemise(0.0);
        article1.setItem(item1);
        articles.add(article1);

        Article article2 = new Article();
        article2.setPiece(piece2);
        article2.setPrixUnitaire(4.0);
        article2.setQuantite(1.0);
        article2.setRemise(0.0);
        article2.setItem(item2);
        articles.add(article2);
        piece2.setArticles(articles);
        piece2.setDate(DateTime.parse("2001-05-05T12:00:00.000+01:00").toDate());
        pieces.add(piece2);
    }

    private void getPiece3(List<Piece> pieces, Item item1, Item item2) {
        Piece piece2 = new Piece();
        List<Article> articles = new ArrayList<Article>();
        Article article1 = new Article();
        article1.setPiece(piece2);
        article1.setPrixUnitaire(2.0);
        article1.setQuantite(1.0);
        article1.setRemise(0.0);
        article1.setItem(item1);
        articles.add(article1);

        Article article2 = new Article();
        article2.setPiece(piece2);
        article2.setPrixUnitaire(3.0);
        article2.setQuantite(1.0);
        article2.setRemise(0.0);
        article2.setItem(item2);
        articles.add(article2);
        piece2.setArticles(articles);
        piece2.setDate(DateTime.parse("2000-01-01T12:00:00.000+01:00").toDate());
        pieces.add(piece2);
    }

    @Test
    public void recuperer_les_Series() {
        MyOwnChart myOwnChart = new MyOwnChart();
        List<Piece> pieces = new ArrayList<Piece>();
        Item item1 = new Item();
        item1.setDesignation("ITEM 1");
        Item item2 = new Item();
        item2.setDesignation("ITEM 2");

        getPiece1(pieces, item1, item2);
        getPiece2(pieces, item1, item2);
        getPiece3(pieces, item1, item2);
        Map<Item, Map<String, Integer>> itemMontantArticlesMap = myOwnChart.getItemMontantArticlesMap(pieces);
        JSONArray series = myOwnChart.getSeries(itemMontantArticlesMap);
          Assert.assertEquals("[\n"
                              + "  {\n"
                              + "    \"name\" : \"ITEM 1\",\n"
                              + "    \"data\" : [\n"
                              + "      4,\n"
                              + "      6\n"
                              + "    ]\n"
                              + "  },\n"
                              + "  {\n"
                              + "    \"name\" : \"ITEM 2\",\n"
                              + "    \"data\" : [\n"
                              + "      9,\n"
                              + "      4\n"
                              + "    ]\n"
                              + "  }\n"
                              + "]", series.toString());
    }

}
