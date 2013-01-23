package com.eldoraludo.ppafadministration.util;

public enum DateFormat {
    FR_SLASH, EN_SLASH, STANDARD_SLASH;

    public String getFormat() {
        switch (this) {
            case FR_SLASH:
                return "dd/MM/yyyy";
            case EN_SLASH:
                return "MM/dd/yyyy";
            case STANDARD_SLASH:
                return "yyyy/MM/dd";
            default:
                return "dd/MM/yyyy";
        }
    }

    public String getPoiFormat() {
        switch (this) {
            case FR_SLASH:
                return "d/m/yy";
            case EN_SLASH:
                return "m/d/yy";
            case STANDARD_SLASH:
                return "yy/m/d";
            default:
                return "d/m/yy";
        }
    }

    public String getJqueryFormat() {
        switch (this) {
            case FR_SLASH:
                return "dd/mm/yy";
            case EN_SLASH:
                return "mm/dd/yy";
            case STANDARD_SLASH:
                return "yy/mm/dd";
            default:
                return "dd/mm/yy";
        }
    }

    public String getHighchartsFormat() {
        switch (this) {
            case FR_SLASH:
                return "%d/%m/%Y";
            case EN_SLASH:
                return "%m/%d/%Y";
            case STANDARD_SLASH:
                return "%Y/%m/%d";
            default:
                return "%d/%m/%Y";
        }
    }

    public String getReportingFormat() {
        return getFormat();
    }
}
