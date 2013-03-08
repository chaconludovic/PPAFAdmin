package com.eldoraludo.ppafadministration.components;

import com.eldoraludo.ppafadministration.entities.Piece;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.hibernate.Session;

import java.util.List;

public class MyOwnChart extends AbstractHighCharts {

    @Inject
    private Session session;


    public JSONObject getComponentOptions() {

        JSONObject opt = new JSONObject();
        opt.put("text", "Test Override");
        opt.put("x", -20);

        JSONObject donneesDuGraphe = new JSONObject();
        donneesDuGraphe.put("subtitle", opt);
        donneesDuGraphe.put("chart", new JSONObject("renderTo", getClientId()));

        donneesDuGraphe.put("series", getSeries());

        donneesDuGraphe.put("yAxis", getYAxis());
        return donneesDuGraphe;

    }

    private JSONObject getYAxis() {
        JSONObject yaxis = new JSONObject();
        yaxis.put("title", new JSONObject("text", "Nombre de pièces"));
        return yaxis;
    }

    private JSONArray getSeries() {
        JSONArray series = new JSONArray();

        JSONObject nameData = new JSONObject();
        JSONArray data = new JSONArray();

        List<Piece> list = session.createCriteria(Piece.class).list();
        for (Piece piece : list) {
//            data.put();
            piece.getArticles().size();
        }
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        data.put(1.0);
        nameData.put("name", "paris");
        nameData.put("data", data);
        series.put(nameData);
        return series;
    }
}
