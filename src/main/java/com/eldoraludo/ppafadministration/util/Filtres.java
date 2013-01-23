package com.eldoraludo.ppafadministration.util;

import org.apache.commons.lang.StringUtils;

import java.text.Normalizer;

public class Filtres {

    public static boolean matchesAny(String filtre, String... champsAFiltrer) {
        if (StringUtils.isBlank(filtre)) {
            return true;
        }
        for (String champ : champsAFiltrer) {
            if (StringUtils.isBlank(champ)) {
                continue;
            }
            if (normalize(champ).contains(normalize(filtre))) {
                return true;
            }
        }
        return false;
    }

    public static String normalize(String string) {
        return removeDiacriticalMarks(string.trim().toLowerCase());
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
