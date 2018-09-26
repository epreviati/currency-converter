package com.gmail.previati.edgardo.currencyconverter.data;

import com.gmail.previati.edgardo.currencyconverter.Const;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class Currency {

    private static final String LABEL_CURRENCY_CODE = "currency code";
    private static final String LABEL_DESCRIPTION = "description";
    private static final String LABEL_SYMBOL = "symbol";

    private String mCurrencyCode;
    private String mDescription;
    private String mSymbol;

    public Currency(final String currencyCode) {
        if (currencyCode == null
                || currencyCode.equals(Const.EMPTY)
                || currencyCode.equals(Const.SPACE)
                || currencyCode.length() < 3) {
            throw new IllegalArgumentException("Invalid Currency Code");
        }

        mCurrencyCode = currencyCode.length() > 3
                ? currencyCode
                : currencyCode.toUpperCase();
    }

    public Currency(final String currencyCode, final String description) {
        this(currencyCode);
        mDescription = description;
    }

    public Currency(final String currencyCode, final String description, final String symbol) {
        this(currencyCode, description);
        mSymbol = symbol;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public String getDescription() {
        if (mDescription == null) {
            mDescription = Const.EMPTY;
        }

        return mDescription;
    }

    public String getSymbol() {
        if (mSymbol == null) {
            mSymbol = Const.EMPTY;
        }

        return mSymbol;
    }

    @Override
    public String toString() {
        return String.format(
                "[ %s: %s - %s: %s - %s: %s ]",
                LABEL_CURRENCY_CODE,
                getCurrencyCode(),
                LABEL_DESCRIPTION,
                getDescription(),
                LABEL_SYMBOL,
                getSymbol());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        return getCurrencyCode().equals(((Currency) obj).getCurrencyCode());
    }
}