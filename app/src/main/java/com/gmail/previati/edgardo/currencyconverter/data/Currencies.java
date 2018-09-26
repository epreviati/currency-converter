package com.gmail.previati.edgardo.currencyconverter.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class Currencies {

    public static final String BGN = "BGN";
    public static final String CAD = "CAD";
    public static final String CNY = "CNY";
    public static final String CZK = "CZK";
    public static final String DKK = "DKK";
    public static final String EUR = "EUR";
    public static final String GBP = "GBP";
    public static final String JPY = "JPY";
    public static final String HRK = "HRK";
    public static final String HUF = "HUF";
    public static final String LTL = "LTL";
    public static final String PLN = "PLN";
    public static final String RON = "RON";
    public static final String SEK = "SEK";
    public static final String USD = "USD";

    public static final Comparator<Currency> COMPARATOR = new Comparator<Currency>() {
        @Override
        public int compare(Currency c1, Currency c2) {
            return c1.getCurrencyCode().compareToIgnoreCase(c2.getCurrencyCode());
        }
    };

    private List<Currency> mCurrencies;

    public Currencies() {
        mCurrencies = new ArrayList<>();
        setDefaultCurrencies();
    }

    public boolean addInHead(final Currency currency) {
        List<Currency> supp = new ArrayList<>();
        supp.add(currency);
        supp.addAll(mCurrencies);

        mCurrencies = new ArrayList<>();
        return mCurrencies.addAll(supp);
    }

    public boolean add(final Currency currency) {
        return mCurrencies.add(currency);
    }

    public Currency remove(final int elementAtPosition) {
        if (elementAtPosition < 0 || elementAtPosition > mCurrencies.size() - 1)
            return null;

        return mCurrencies.remove(elementAtPosition);
    }

    public boolean remove(final Currency currency) {
        return mCurrencies.remove(currency);
    }

    public boolean removeAll(final Collection<Currency> currencies) {
        return mCurrencies.removeAll(currencies);
    }

    public List<Currency> getAll() {
        return mCurrencies;
    }

    public Currency getCurrency(final int elementAtPosition) {
        if (elementAtPosition < 0 || elementAtPosition > mCurrencies.size() - 1)
            return null;

        return mCurrencies.get(elementAtPosition);
    }

    public Currency getCurrency(final String currencyCode) {
        if (currencyCode == null || currencyCode.length() == 0) return null;

        for (Currency currencyToCheck : mCurrencies) {
            if (currencyToCheck.getCurrencyCode().equals(currencyCode)) return currencyToCheck;
        }

        return null;
    }

    public int getIndex(final String currencyCode) {
        if (currencyCode == null || currencyCode.length() == 0) return -1;

        for (int i = 0; i < mCurrencies.size(); i++) {
            if (mCurrencies.get(i).getCurrencyCode().equals(currencyCode)) return i;
        }

        return 0;
    }

    @Override
    public String toString() {
        String currencies = "{\n";
        for (Currency currency : mCurrencies) {
            currencies = String.format("%s%s", currencies, currency.toString());
        }
        return String.format("%s%s", currencies, "\n}");
    }

    public void setDefaultCurrencies() {
        add(new Currency("AED", "United Arab Emirates Dirham", "د.إ"));
        add(new Currency("AFN", "Afghanistan Afghani", "؋"));
        add(new Currency("ALL", "Albania Lek", "Lek"));
        add(new Currency("AMD", "Armenia Dram", null));
        add(new Currency("ANG", "Netherlands Antilles Guilder", "ƒ"));
        add(new Currency("AOA", "Angola Kwanza", "Kz"));
        add(new Currency("ARS", "Argentina Peso", "$"));
        add(new Currency("AUD", "Australia Dollar", "$"));
        add(new Currency("AWG", "Aruba Guilder", "ƒ"));
        add(new Currency("AZN", "Azerbaijan New Manat", "ман"));

        add(new Currency("BAM", "Bosnia and Herzegovina Convertible Marka", "KM"));
        add(new Currency("BBD", "Barbados Dollar", "$"));
        add(new Currency("BDT", "Bangladesh Taka", "Tk"));
        add(new Currency(BGN, "Bulgaria Lev", "лв"));
        add(new Currency("BHD", "Bahrain Dinar", "BD"));
        add(new Currency("BIF", "Burundi Franc", "BIF"));
        add(new Currency("BMD", "Bermuda Dollar", "$"));
        add(new Currency("BND", "Brunei Darussalam Dollar", "$"));
        add(new Currency("BOB", "Bolivia Boliviano", "$b"));
        add(new Currency("BRL", "Brazil Real", "R$"));
        add(new Currency("BSD", "Bahamas Dollar", "$"));
        add(new Currency("BTN", "Bhutan Ngultrum", "Nu."));
        add(new Currency("BWP", "Botswana Pula", "P"));
        add(new Currency("BYR", "Belarus Ruble", "Br"));
        add(new Currency("BZD", "Belize Dollar", "BZ$"));

        add(new Currency(CAD, "Canada Dollar", "$"));
        add(new Currency("CDF", "Congo/Kinshasa Franc", "F"));
        add(new Currency("CHF", "Switzerland Franc", "CHF"));
        add(new Currency("CLP", "Chile Peso", "$"));
        add(new Currency(CNY, "China Yuan Renminbi", "¥"));
        add(new Currency("COP", "Colombia Peso", "$"));
        add(new Currency("CRC", "Costa Rica Colon", "₡"));
        add(new Currency("CUP", "Cuba Peso", "₱"));
        add(new Currency("CVE", "Cape Verde Escudo", "$"));
        add(new Currency(CZK, "Czech Republic Koruna", "Kč"));

        add(new Currency("DJF", "Djibouti Franc", "Fdj"));
        add(new Currency(DKK, "Denmark Krone", "kr"));
        add(new Currency("DOP", "Dominican Republic Peso", "RD$"));
        add(new Currency("DZD", "Algeria Dinar", "دج"));

        add(new Currency("EGP", "Egypt Pound", "£"));
        add(new Currency("ERN", "Eritrea Nakfa", "Nfk"));
        add(new Currency("ETB", "Ethiopia Birr", "Br"));
        add(new Currency(EUR, "Euro Member Countries", "€"));

        add(new Currency("FJD", "Fiji Dollar", "$"));
        add(new Currency("FKP", "Falkland Islands Pound", "£"));

        add(new Currency(GBP, "United Kingdom Pound", "£"));
        add(new Currency("GEL", "Georgia Lari", null));
        add(new Currency("GHS", "Ghana Cedi", "GH¢"));
        add(new Currency("GIP", "Gibraltar Pound", "£"));
        add(new Currency("GMD", "Gambia Dalasi", "D"));
        add(new Currency("GNF", "Guinea Franc", "FG"));
        add(new Currency("GTQ", "Guatemala Quetzal", "Q"));
        add(new Currency("GYD", "Guyana Dollar", "$"));

        add(new Currency("HKD", "Hong Kong Dollar", "HK$"));
        add(new Currency("HNL", "Honduras Lempira", "L"));
        add(new Currency(HRK, "Croatia Kuna", "kn"));
        add(new Currency("HTG", "Haiti Gourde", "G"));
        add(new Currency(HUF, "Hungary Forint", "Ft"));

        add(new Currency("IDR", "Indonesia Rupiah", "Rp"));
        add(new Currency("ILS", "Israel Shekel", "₪"));
        add(new Currency("INR", "India Rupee", "₹"));
        add(new Currency("IQD", "Iraq Dinar", "د.ع"));
        add(new Currency("IRR", "Iran Rial", "﷼"));
        add(new Currency("ISK", "Iceland Krona", "kr"));

        add(new Currency("JEP", "Jersey Pound", "£"));
        add(new Currency("JMD", "Jamaica Dollar", "J$"));
        add(new Currency("JOD", "Jordan Dinar", null));
        add(new Currency(JPY, "Japan Yen", "¥"));

        add(new Currency("KES", "Kenya Shilling", "KSh"));
        add(new Currency("KGS", "Kyrgyzstan Som", "лв"));
        add(new Currency("KHR", "Cambodia Riel", null));
        add(new Currency("KMF", "Comoros Franc", null));
        add(new Currency("KPW", "Korea (North) Won", "₩"));
        add(new Currency("KRW", "Korea (South) Won", "₩"));
        add(new Currency("KWD", "Kuwait Dinar", "ك"));
        add(new Currency("KYD", "Cayman Islands Dollar", "$"));
        add(new Currency("KZT", "Kazakhstan Tenge", null));

        add(new Currency("LAK", "Laos Kip", "₭"));
        add(new Currency("LBP", "Lebanon Pound", "ل.ل"));
        add(new Currency("LKR", "Sri Lanka Rupee", "₨"));
        add(new Currency("LRD", "Liberia Dollar", "$"));
        add(new Currency("LSL", "Lesotho Loti", "L"));
        add(new Currency(LTL, "Lithuania Litas", "Lt"));
        add(new Currency("LYD", "Libya Dinar", "LD"));

        add(new Currency("MAD", "Morocco Dirham", "د.م."));
        add(new Currency("MDL", "Moldova Leu", null));
        add(new Currency("MGA", "Madagascar Ariary", "Ar"));
        add(new Currency("MKD", "Macedonia Denar", "ден"));
        add(new Currency("MMK", "Myanmar (Burma) Kyat", "K"));
        add(new Currency("MNT", "Mongolia Tughrik", "₮"));
        add(new Currency("MOP", "Macau Pataca", "MOP$"));
        add(new Currency("MRO", "Mauritania Ouguiya", "UM"));
        add(new Currency("MUR", "Mauritius Rupee", "₨"));
        add(new Currency("MVR", "Maldives Rufiyaa", "Rf"));
        add(new Currency("MWK", "Malawi Kwacha", "MK"));
        add(new Currency("MXN", "Mexico Peso", "$"));
        add(new Currency("MYR", "Malaysia Ringgit", "RM"));
        add(new Currency("MZN", "Mozambique Metical", "MT"));

        add(new Currency("NAD", "Namibia Dollar", "$"));
        add(new Currency("NGN", "Nigeria Naira", "₦"));
        add(new Currency("NIO", "Nicaragua Cordoba", "C$"));
        add(new Currency("NOK", "Norway Krone", "kr"));
        add(new Currency("NPR", "Nepal Rupee", "₨"));
        add(new Currency("NZD", "New Zealand Dollar", "$"));

        add(new Currency("OMR", "Oman Rial", "﷼"));

        add(new Currency("PAB", "Panama Balboa", "B/."));
        add(new Currency("PEN", "Peru Nuevo Sol", "S/."));
        add(new Currency("PGK", "Papua New Guinea Kina", "K"));
        add(new Currency("PHP", "Philippines Peso", "₱"));
        add(new Currency("PKR", "Pakistan Rupee", "₨"));
        add(new Currency(PLN, "Poland Zloty", "zł"));
        add(new Currency("PYG", "Paraguay Guarani", "Gs"));

        add(new Currency("QAR", "Qatar Riyal", "﷼"));

        add(new Currency(RON, "Romania New Leu", "L"));
        add(new Currency("RSD", "Serbia Dinar", "РСД"));
        add(new Currency("RUB", "Russia Ruble", "руб"));
        add(new Currency("RWF", "Rwanda Franc", "RF"));

        add(new Currency("SAR", "Saudi Arabia Riyal", "﷼"));
        add(new Currency("SBD", "Solomon Islands Dollar", "$"));
        add(new Currency("SCR", "Seychelles Rupee", "₨"));
        add(new Currency("SDG", "Sudan Pound", null));
        add(new Currency(SEK, "Sweden Krona", "kr"));
        add(new Currency("SGD", "Singapore Dollar", "$"));
        add(new Currency("SHP", "Saint Helena Pound", "£"));
        add(new Currency("SLL", "Sierra Leone Leone", "Le"));
        add(new Currency("SOS", "Somalia Shilling", "S"));
        add(new Currency("SRD", "Suriname Dollar", "$"));
        add(new Currency("STD", "São Tomé and Príncipe Dobra", "Db"));
        add(new Currency("SVC", "El Salvador Colon", "$"));
        add(new Currency("SYP", "Syria Pound", "£"));
        add(new Currency("SZL", "Swaziland Lilangeni", "L"));

        add(new Currency("THB", "Thailand Baht", "฿"));
        add(new Currency("TJS", "Tajikistan Somoni", null));
        add(new Currency("TMT", "Turkmenistan Manat", "m"));
        add(new Currency("TND", "Tunisia Dinar", "د.ت"));
        add(new Currency("TOP", "Tonga Pa'anga", "T$"));
        add(new Currency("TRY", "Turkey Lira", null));
        add(new Currency("TTD", "Trinidad and Tobago Dollar", "TT$"));
        add(new Currency("TWD", "Taiwan New Dollar", "NT$"));
        add(new Currency("TZS", "Tanzania Shilling", "x/y"));

        add(new Currency("UAH", "Ukraine Hryvnia", "₴"));
        add(new Currency("UGX", "Uganda Shilling", "USh"));
        add(new Currency(USD, "United States Dollar", "$"));
        add(new Currency("UYU", "Uruguay Peso", "$U"));
        add(new Currency("UZS", "Uzbekistan Som", null));

        add(new Currency("VEF", "Venezuela Bolivar", "Bs."));
        add(new Currency("VND", "Viet Nam Dong", "₫"));
        add(new Currency("VUV", "Vanuatu Vatu", "VT"));

        add(new Currency("WST", "Samoa Tala", "$"));

        add(new Currency("XCD", "East Caribbean Dollar", "$"));
        add(new Currency("XDR", "International Monetary Fund", null));
        add(new Currency("XOF", "Communauté Financière Africaine Franc", null));
        add(new Currency("XPF", "Comptoirs Français du Pacifique Franc", null));

        add(new Currency("YER", "Yemen Rial", "﷼"));

        add(new Currency("ZAR", "South Africa Rand", "R"));
        add(new Currency("ZMW", "Zambia Kwacha", "ZK"));
    }
}