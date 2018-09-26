package com.gmail.previati.edgardo.currencyconverter;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public final class Const {

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static final String URL_BASE = "https://free.currencyconverterapi.com";
    public static final String URL_CONVERSION = "/api/v6/convert?q={FROM}_{TO}&compact=ultra";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_APPLICATION_JSON = "application/json";
    public static final String HEADER_DEFAULT_LENGTH = "0";

    public static final String KEY_PREFERENCE_EXCHANGE_QUOTE = ":quote";
    public static final String KEY_PREFERENCE_UPDATE_AT = ":update";

    public static final String KEY_PREFERENCE_REQUEST_OF_AUTO_UPDATE = "auto:update";
    public static final String KEY_PREFERENCE_MINUTES_TO_UPDATE = "minutes:to:update";

    public static final String KEY_PREFERENCE_DEFAULT_CURRENCY_FROM = "default:currency:from";
    public static final String KEY_PREFERENCE_DEFAULT_CURRENCY_TO = "default:currency:to";

    public static final String KEY_PREFERENCE_HIDE_CURRENCY = ":hide:currency";

    public static final String KEY_PREFERENCE_DISCLAIMERR_ACCEPTED = "disclaimer:accepted";
    public static final String KEY_PREFERENCE_DATE_DISCLAIMERR_ACCEPTED = "disclaimer:accepted:date";
    public static final int VALIDITY_DAYS = 30;

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

    public static final int ONE_MINUTE = 1;
    public static final int ONE_HOUR_IN_MINUTES = 60;
    public static final int ONE_DAY_IN_MINUTES = 1440;
    public static final int ONE_DAY_IN_HOURS = 24;

    public static final int[] MINUTES_TO_UPDATE = new int[]{
            ONE_MINUTE * 5, // 5 minutes
            ONE_HOUR_IN_MINUTES, // 1 hor
            ONE_DAY_IN_MINUTES // 1 day
    };

}