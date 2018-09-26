package com.gmail.previati.edgardo.currencyconverter.asynctask.handler;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.R;
import com.gmail.previati.edgardo.currencyconverter.helper.Util;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class GetExchangeQuoteHandler implements Response {

    private Context mContext;

    private TextView mTextView;

    private String mFrom;
    private String mTo;

    public GetExchangeQuoteHandler(
            final Context context,
            final TextView textView,
            final String from,
            final String to
    ) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;

        if (textView == null) {
            throw new NullPointerException("TextView cannot be null");
        }
        mTextView = textView;

        mFrom = from;
        mTo = to;
    }

    @Override
    public void onSuccess(final String output) {
        try {
            JSONObject jsonObject = new JSONObject(output);
            Double rate = jsonObject.getDouble(mFrom.toUpperCase(Locale.getDefault()) + "_" + mTo.toUpperCase(Locale.getDefault()));
            String exchangeQuote = rate.toString();

            DateFormat df = new SimpleDateFormat(Const.DATE_FORMAT, Locale.getDefault());
            Calendar now = new GregorianCalendar();

            Util.savePreferenceValue(
                    mContext,
                    Util.concat(mFrom, mTo, Const.KEY_PREFERENCE_EXCHANGE_QUOTE),
                    exchangeQuote);

            String updatedAt = df.format(now.getTime());
            Util.savePreferenceValue(
                    mContext,
                    Util.concat(mFrom, mTo, Const.KEY_PREFERENCE_UPDATE_AT),
                    updatedAt);

            mTextView.setText(Util.concat(
                    Util.getStringFromResource(mContext, R.string.label_info_exchange_quote),
                    Const.SPACE,
                    exchangeQuote,
                    Const.NEW_LINE,
                    Const.NEW_LINE,
                    Util.getStringFromResource(mContext, R.string.label_info_updated_at),
                    Const.SPACE,
                    updatedAt));
        } catch (Exception e) {
            onFail();
        }
    }

    @Override
    public void onFail() {
        Toast.makeText(
                mContext,
                Util.getStringFromResource(mContext, R.string.toast_error),
                Toast.LENGTH_SHORT).show();

        mTextView.setText(Util.getStringFromResource(
                mContext,
                R.string.label_info_error));
    }
}
