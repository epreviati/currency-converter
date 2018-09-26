package com.gmail.previati.edgardo.currencyconverter.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.R;
import com.gmail.previati.edgardo.currencyconverter.data.Conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public abstract class Util {

    private static final String TAG = Util.class.getName();

    public static final String getStringFromResource(final Context context, final int resourceId) {
        String toReturn = String.format("resource with id [%s] not found", resourceId);
        try {
            toReturn = context.getResources().getString(resourceId);
        } catch (Resources.NotFoundException e) {
            Log.wtf(concat(TAG, ".getStringFromResource(Context, int)"), e.getMessage());
        }

        return toReturn;
    }

    public static final BigDecimal toBigDecimal(final String toConvert) {
        BigDecimal toReturn = new BigDecimal("0");
        if (toConvert == null || toConvert.equals(Const.EMPTY)) {
            return toReturn;
        }

        try {
            toReturn = new BigDecimal(toConvert);
        } catch (NumberFormatException e) {
            Log.wtf(concat(TAG + ".toBigDecimal(String)"), e.getMessage());
        }

        return toReturn;
    }

    public static final String convertAmount(final String amount, final String exchangeQuote) {
        return String.valueOf(toBigDecimal(amount)
                .multiply(toBigDecimal(exchangeQuote))
                .setScale(2, RoundingMode.HALF_DOWN));
    }

    public static final String concat(final String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String toAdd : strings) {
            sb.append(toAdd);
        }

        return sb.toString();
    }

    public static final void savePreferenceValue(final Context context,
                                                 final String key, final Object value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value != null) {
                if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                }
            }

            editor.commit();
        } catch (Exception e) {
            Log.wtf(TAG, e.getMessage());
        }
    }

    public static final <T> T getPreferenceValue(final Context context, final String key,
                                                 final Class<T> clazz, final T defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            if (!sp.getAll().containsKey(key)) {
                return defaultValue;
            }

            return clazz.cast(sp.getAll().get(key));
        } catch (Exception e) {
            Log.wtf(TAG, e.getMessage());
        }

        return defaultValue;
    }

    public static final <T> T getPreferenceValue(final Context context,
                                                 final String key, final Class<T> clazz) {
        return getPreferenceValue(context, key, clazz, null);
    }

    public static final String getSavedCurrencyFrom(Context context) {
        return Util.getPreferenceValue(
                context,
                Const.KEY_PREFERENCE_DEFAULT_CURRENCY_FROM,
                String.class);
    }

    public static final String getSavedCurrencyTo(Context context) {
        return Util.getPreferenceValue(
                context,
                Const.KEY_PREFERENCE_DEFAULT_CURRENCY_TO,
                String.class);
    }

    public static final String getDefaultCurrencyFrom(Context context) {
        String defaultCurrencyFrom = getSavedCurrencyFrom(context);
        if (defaultCurrencyFrom != null && !defaultCurrencyFrom.equals(Const.EMPTY)) {
            return defaultCurrencyFrom;
        }

        return MyLocale.getDefaultCurrencyFrom();
    }

    public static final String getDefaultCurrencyTo(Context context) {
        String defaultCurrencyTo = getSavedCurrencyTo(context);
        if (defaultCurrencyTo != null && !defaultCurrencyTo.equals(Const.EMPTY)) {
            return defaultCurrencyTo;
        }

        return MyLocale.getDefaultCurrencyTo();
    }

    public static final boolean isExchangeQuoteOutOfDate(final Context context,
                                                         final Conversion conversion) {
        String exchangeQuote = getPreferenceValue(
                context,
                concat(
                        conversion.getCurrencyFrom(),
                        conversion.getCurrencyTo(),
                        Const.KEY_PREFERENCE_EXCHANGE_QUOTE
                ), String.class);

        if (exchangeQuote == null) {
            return true;
        }

        String lastUpdate = getPreferenceValue(
                context,
                concat(
                        conversion.getCurrencyFrom(),
                        conversion.getCurrencyTo(),
                        Const.KEY_PREFERENCE_UPDATE_AT
                ), String.class);

        if (lastUpdate == null) {
            return true;
        }

        boolean autoUpdate = Util.getPreferenceValue(
                context,
                Const.KEY_PREFERENCE_REQUEST_OF_AUTO_UPDATE,
                Boolean.class,
                true);

        if (autoUpdate) {
            int minutes = Util.getPreferenceValue(
                    context,
                    Const.KEY_PREFERENCE_MINUTES_TO_UPDATE,
                    Integer.class,
                    Const.MINUTES_TO_UPDATE[0]);

            SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
            Date updateAt = new Date();
            try {
                updateAt = dateFormat.parse(lastUpdate);
                updateAt = addTime(updateAt, Calendar.MINUTE, minutes);
            } catch (ParseException e) {
                Log.wtf(TAG, e.getMessage());
            }

            return new Date().after(updateAt);
        }

        return false;
    }

    public static Date addTime(Date date, int fieldToAdd, int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(fieldToAdd, time);

        return cal.getTime();
    }

    public static int getTimeByMinutes(int minutes) {
        if (minutes < Const.ONE_HOUR_IN_MINUTES) {
            return minutes;
        }

        if (minutes >= Const.ONE_HOUR_IN_MINUTES && minutes < Const.ONE_DAY_IN_MINUTES) {
            return minutes / Const.ONE_HOUR_IN_MINUTES;
        }

        return minutes / Const.ONE_DAY_IN_MINUTES;
    }

    public static String getTimeDescriptionByMinutes(Context context, int minutes) {
        int resourceId;
        if (minutes == Const.ONE_MINUTE) {
            resourceId = R.string.label_minute;
        } else if (minutes < Const.ONE_HOUR_IN_MINUTES) {
            resourceId = R.string.label_minutes;
        } else if (minutes == Const.ONE_HOUR_IN_MINUTES) {
            resourceId = R.string.label_hour;
        } else if (minutes > Const.ONE_HOUR_IN_MINUTES && minutes < Const.ONE_DAY_IN_MINUTES) {
            resourceId = R.string.label_hours;
        } else if (minutes == Const.ONE_DAY_IN_MINUTES) {
            resourceId = R.string.label_day;
        } else {
            resourceId = R.string.label_days;
        }

        return getStringFromResource(context, resourceId);
    }
}