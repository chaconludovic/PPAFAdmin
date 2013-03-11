package com.eldoraludo.ppafadministration.components;

import com.eldoraludo.ppafadministration.entities.Article;
import com.eldoraludo.ppafadministration.entities.Item;
import com.eldoraludo.ppafadministration.entities.Piece;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.joda.time.DateMidnight;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyOwnChart extends AbstractHighCharts {

    @Inject
    private Session session;

    public JSONObject getComponentOptions() {

        Map<Item, Map<String, Integer>> itemNombreArticlesMap = new LinkedHashMap<Item, Map<String, Integer>>();
        List<Piece> list = session.createCriteria(Piece.class).addOrder(Order.asc("date")).list();
        Map<Item, Map<String, Integer>> itemMontantArticlesMap = getItemMontantArticlesMap(list);

        JSONObject opt = new JSONObject();
        opt.put("x", -20);

        JSONObject donneesDuGraphe = new JSONObject();
        donneesDuGraphe.put("subtitle", opt);
        donneesDuGraphe.put("chart", new JSONObject("renderTo", getClientId()));

        donneesDuGraphe.put("series", getSeries(itemMontantArticlesMap));

        donneesDuGraphe.put("yAxis", getYAxis());
        donneesDuGraphe.put("xAxis", getXAxis(itemMontantArticlesMap));
        return donneesDuGraphe;

    }

    protected Map<Item, Map<String, Integer>> getItemMontantArticlesMap(List<Piece> list) {
        Map<Item, Map<String, Integer>> itemMontantArticlesMap = new LinkedHashMap<Item, Map<String, Integer>>();
        for (Piece piece : list) {
            if (piece.getArticles().size() == 0) {
                continue;
            }
            DateMidnight dateMidnight = new DateMidnight(piece.getDate());
            String monthYear = getMonthAndYear(dateMidnight);
            for (Article article : piece.getArticles()) {
                Item item = article.getItem();
                // montant
                if (!itemMontantArticlesMap.containsKey(item)) {
                    itemMontantArticlesMap.put(item, new LinkedHashMap<String, Integer>());
                }
                Map<String, Integer> moisAnneeMontantMap = itemMontantArticlesMap.get(item);
                if (moisAnneeMontantMap.containsKey(monthYear)) {
                    moisAnneeMontantMap.put(monthYear, moisAnneeMontantMap.get(monthYear) + article.getTotal().intValue());
                } else {
                    moisAnneeMontantMap.put(monthYear, article.getTotal().intValue());
                }
            }
        }
        return itemMontantArticlesMap;
    }

    private String getMonthAndYear(DateMidnight dateMidnight) {
        int year = dateMidnight.getYear();
        int monthOfYear = dateMidnight.getMonthOfYear();
        String month = null;
        if (monthOfYear == 1) {
            month = "janvier";
        }
        if (monthOfYear == 2) {
            month = "février";
        }
        if (monthOfYear == 3) {
            month = "mars";
        }
        if (monthOfYear == 4) {
            month = "avril";
        }
        if (monthOfYear == 5) {
            month = "mai";
        }
        if (monthOfYear == 6) {
            month = "juin";
        }
        if (monthOfYear == 7) {
            month = "juillet";
        }
        if (monthOfYear == 8) {
            month = "aout";
        }
        if (monthOfYear == 9) {
            month = "septembre";
        }
        if (monthOfYear == 10) {
            month = "octobre";
        }
        if (monthOfYear == 11) {
            month = "novembre";
        }
        if (monthOfYear == 12) {
            month = "décembre";
        }
        month += " " + year;
        return month;
    }

    private JSONObject getXAxis(Map<Item, Map<String, Integer>> map) {
        JSONObject xAxis = new JSONObject();
        Set<String> monthYears = new HashSet<String>();
        for (Item item : map.keySet()) {
            monthYears.addAll(map.get(item).keySet());
        }
        JSONArray categorie = new JSONArray();
        for (String monthYear : monthYears) {
            categorie.put(monthYear);
        }
        xAxis.put("categories", categorie);

        return xAxis;
    }

    private JSONObject getYAxis() {
        JSONObject yaxis = new JSONObject();
        yaxis.put("title", new JSONObject("text", "Montant"));
        return yaxis;
    }

    protected JSONArray getSeries(Map<Item, Map<String, Integer>> map) {
        JSONArray series = new JSONArray();
        for (Item item : map.keySet()) {
            JSONObject nameData = new JSONObject();
            JSONArray data = new JSONArray();
            for (String mois : map.get(item).keySet()) {
                data.put(map.get(item).get(mois));
            }
            nameData.put("name", item.getDesignation());
            nameData.put("data", data);
            series.put(nameData);
        }

        return series;
    }
}
