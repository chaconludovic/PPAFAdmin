package com.eldoraludo.ppafadministration.components;

import com.eldoraludo.ppafadministration.entities.Piece;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyOwnChart extends AbstractHighCharts {

    @Inject
    private Session session;


    public JSONObject getComponentOptions() {

        Map<String, Integer> articleParDateMap = new LinkedHashMap<String, Integer>();
        List<Piece> list = session.createCriteria(Piece.class).addOrder(Order.asc("date")).list();
        for (Piece piece : list) {
            if (piece.getArticles().size() == 0) {
                continue;
            }
            DateMidnight dateMidnight = new DateMidnight(piece.getDate());
            String monthYear = getMontAndYear(dateMidnight);
            if (articleParDateMap.containsKey(monthYear)) {
                articleParDateMap.put(monthYear, articleParDateMap.get(monthYear) + piece.getArticles().size());
            } else {
                articleParDateMap.put(monthYear, piece.getArticles().size());
            }

        }

        JSONObject opt = new JSONObject();
//        opt.put("text", "Test Override");
        opt.put("x", -20);

        JSONObject donneesDuGraphe = new JSONObject();
        donneesDuGraphe.put("subtitle", opt);
        donneesDuGraphe.put("chart", new JSONObject("renderTo", getClientId()));

        donneesDuGraphe.put("series", getSeries(articleParDateMap));

        donneesDuGraphe.put("yAxis", getYAxis());
        donneesDuGraphe.put("xAxis", getXAxis(articleParDateMap));
        return donneesDuGraphe;

    }

    private String getMontAndYear(DateMidnight dateMidnight) {
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

    private JSONObject getXAxis(Map<String, Integer> map) {
        JSONObject xAxis = new JSONObject();
        JSONArray categorie = new JSONArray();
        for (String mois : map.keySet()) {
            categorie.put(mois);
        }
        xAxis.put("categories", categorie);
        return xAxis;
    }

    private JSONObject getYAxis() {
        JSONObject yaxis = new JSONObject();
        yaxis.put("title", new JSONObject("text", "Nombre de pièces"));
        return yaxis;
    }

    private JSONArray getSeries(Map<String, Integer> map) {
        JSONArray series = new JSONArray();

        JSONObject nameData = new JSONObject();
        JSONArray data = new JSONArray();
        for (String mois : map.keySet()) {
            data.put(map.get(mois));
        }
        nameData.put("name", "Vente");
        nameData.put("data", data);
        series.put(nameData);
        return series;
    }
}
