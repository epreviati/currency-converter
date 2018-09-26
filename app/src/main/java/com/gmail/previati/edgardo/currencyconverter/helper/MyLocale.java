package com.gmail.previati.edgardo.currencyconverter.helper;

import com.gmail.previati.edgardo.currencyconverter.data.Currencies;

import java.util.Locale;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class MyLocale {

    // region EU countries that have EUR currency

    /**
     * Locale constant for de_AT.
     */
    public static final Locale AUSTRIA = new Locale("de", "AT");

    /**
     * Locale constant for fr_BE.
     */
    public static final Locale BELGIUM = new Locale("fr", "BE");

    /**
     * Locale constant for el_CY.
     */
    public static final Locale CYPRUS = new Locale("el", "CY");

    /**
     * Locale constant for et_EE.
     */
    public static final Locale ESTONIA = new Locale("et", "EE");

    /**
     * Locale constant for fi_FI.
     */
    public static final Locale FINLAND = new Locale("fi", "FI");

    /**
     * Locale constant for fr_FR.
     */
    public static final Locale FRANCE = Locale.FRANCE;

    /**
     * Locale constant for de_DE.
     */
    public static final Locale GERMANY = Locale.GERMANY;

    /**
     * Locale constant for el_GR.
     */
    public static final Locale GREECE = new Locale("el", "GR");

    /**
     * Locale constant for ga_IE.
     */
    public static final Locale IRELAND = new Locale("ga", "IE");

    /**
     * Locale constant for it_IT.
     */
    public static final Locale ITALY = Locale.ITALY;

    /**
     * Locale constant for lv_LV.
     */
    public static final Locale LATVIA = new Locale("lv", "LV");

    /**
     * Locale constant for fr_LU.
     */
    public static final Locale LUXEMBOURG_FR = new Locale("fr", "LU");

    /**
     * Locale constant for de_LU.
     */
    public static final Locale LUXEMBOURG_DE = new Locale("de", "LU");

    /**
     * Locale constant for mt_MT.
     */
    public static final Locale MALTA = new Locale("mt", "MT");

    /**
     * Locale constant for nl_NL.
     */
    public static final Locale NETHERLANDS = new Locale("nl", "NL");

    /**
     * Locale constant for pt_PT.
     */
    public static final Locale PORTUGAL = new Locale("pt", "PT");

    /**
     * Locale constant for sk_SK.
     */
    public static final Locale SLOVAKIA = new Locale("sk", "SK");

    /**
     * Locale constant for sl_SI.
     */
    public static final Locale SLOVENIA = new Locale("sl", "SI");

    /**
     * Locale constant for es_ES.
     */
    public static final Locale SPAIN = new Locale("es", "ES");

    // endregion

    // region EU countries that do not have EUR currency

    /**
     * Locale constant for bg_BG.
     */
    public static final Locale BULGARIA = new Locale("bg", "BG");

    /**
     * Locale constant for hr_HR.
     */
    public static final Locale CROATIA = new Locale("hr", "HR");

    /**
     * Locale constant for da_DK.
     */
    public static final Locale DENMARK = new Locale("da", "DK");

    /**
     * Locale constant for lt_LT.
     */
    public static final Locale LITHUANIA = new Locale("lt", "LT");

    /**
     * Locale constant for pl_PL.
     */
    public static final Locale POLAND = new Locale("pl", "PL");

    /**
     * Locale constant for en_GB.
     */
    public static final Locale UNITED_KINGDOM = Locale.UK;

    /**
     * Locale constant for cs_CZ.
     */
    public static final Locale CZECH_REPUBLIC = new Locale("cs", "CZ");

    /**
     * Locale constant for ro_RO.
     */
    public static final Locale ROMANIA = new Locale("ro", "RO");

    /**
     * Locale constant for sv_SE.
     */
    public static final Locale SWEDEN = new Locale("sv", "SE");

    /**
     * Locale constant for hu_HU.
     */
    public static final Locale HUNGARY = new Locale("hu", "HU");

    // endregion

    // Other countries

    /**
     * Locale constant for en_CA.
     */
    public static final Locale CANADA = Locale.CANADA;

    /**
     * Locale constant for fr_CA.
     */
    public static final Locale CANADA_FR = Locale.CANADA_FRENCH;

    /**
     * Locale constant for zh_CN.
     */
    public static final Locale CHINA = Locale.CHINA;

    /**
     * Locale constant for ja_JP.
     */
    public static final Locale JAPAN = Locale.JAPAN;

    /**
     * Locale constant for en_US.
     */
    public static final Locale UNITED_STATES = Locale.US;

    // endregion

    public static boolean useCurrencyEUR(Locale locale) {
        return locale.equals(AUSTRIA) || locale.equals(BELGIUM)
                || locale.equals(CYPRUS) || locale.equals(ESTONIA)
                || locale.equals(FINLAND) || locale.equals(FRANCE)
                || locale.equals(GERMANY) || locale.equals(GREECE)
                || locale.equals(IRELAND) || locale.equals(ITALY)
                || locale.equals(LATVIA) || locale.equals(LUXEMBOURG_FR)
                || locale.equals(LUXEMBOURG_DE) || locale.equals(MALTA)
                || locale.equals(NETHERLANDS) || locale.equals(PORTUGAL)
                || locale.equals(SLOVAKIA) || locale.equals(SLOVENIA)
                || locale.equals(SPAIN);
    }

    public static boolean notUseEURInEU(Locale locale) {
        return locale.equals(BULGARIA) || locale.equals(CROATIA)
                || locale.equals(DENMARK) || locale.equals(LITHUANIA)
                || locale.equals(POLAND) || locale.equals(UNITED_KINGDOM)
                || locale.equals(CZECH_REPUBLIC) || locale.equals(ROMANIA)
                || locale.equals(SWEDEN) || locale.equals(HUNGARY);
    }

    public static final String getDefaultCurrencyFrom() {
        Locale locale = Locale.getDefault();
        if (useCurrencyEUR(locale)) {
            return Currencies.EUR;
        }

        if (locale.equals(BULGARIA)) {
            return Currencies.BGN;
        }

        if (locale.equals(CROATIA)) {
            return Currencies.HRK;
        }

        if (locale.equals(DENMARK)) {
            return Currencies.DKK;
        }

        if (locale.equals(LITHUANIA)) {
            return Currencies.LTL;
        }

        if (locale.equals(POLAND)) {
            return Currencies.PLN;
        }

        if (locale.equals(UNITED_KINGDOM)) {
            return Currencies.GBP;
        }

        if (locale.equals(CZECH_REPUBLIC)) {
            return Currencies.CZK;
        }

        if (locale.equals(ROMANIA)) {
            return Currencies.RON;
        }

        if (locale.equals(SWEDEN)) {
            return Currencies.SEK;
        }

        if (locale.equals(HUNGARY)) {
            return Currencies.HUF;
        }

        if (locale.equals(CANADA) || locale.equals(CANADA_FR)) {
            return Currencies.CAD;
        }

        if (locale.equals(CHINA)) {
            return Currencies.CNY;
        }

        if (locale.equals(JAPAN)) {
            return Currencies.JPY;
        }

        if (locale.equals(UNITED_STATES)) {
            return Currencies.USD;
        }

        return Currencies.EUR;
    }

    public static final String getDefaultCurrencyTo() {
        Locale locale = Locale.getDefault();
        if (useCurrencyEUR(locale)) {
            return Currencies.GBP;
        }

        if (notUseEURInEU(locale)) {
            return Currencies.EUR;
        }

        if (locale.equals(CANADA) || locale.equals(CANADA_FR)
                || locale.equals(CHINA) || locale.equals(JAPAN)) {
            return Currencies.USD;
        }

        if (locale.equals(UNITED_STATES)) {
            return Currencies.EUR;
        }

        return Currencies.USD;
    }
}
