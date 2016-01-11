package com.gmail.previati.edgardo.currencyconverter.data;

/**
 * Created by Edgardo on 26/10/2014.
 */
public class Conversion {

    public static final String LABEL_CURRENCY_FROM = "currency from";
    public static final String LABEL_CURRENCY_TO = "currency to";

    private Currency mFrom;
    private Currency mTo;

    public Conversion(final Currency from, final Currency to) {
        if (from == null) throw new IllegalArgumentException("Currency.From cannot be null");
        mFrom = from;

        if (to == null) throw new IllegalArgumentException("Currency.From cannot be null");
        mTo = to;
    }

    public String getCurrencyFrom() {
        return mFrom.getCurrencyCode();
    }

    public String getCurrencyTo() {
        return mTo.getCurrencyCode();
    }

    @Override
    public String toString() {
        return String.format(
                "{ %s: %s - %s: %s }",
                LABEL_CURRENCY_FROM,
                getCurrencyFrom(),
                LABEL_CURRENCY_TO,
                getCurrencyTo());
    }
}
