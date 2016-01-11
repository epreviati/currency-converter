package com.gmail.previati.edgardo.currencyconverter.action;

import android.content.Context;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.asynctask.RequestAsyncTask;
import com.gmail.previati.edgardo.currencyconverter.asynctask.handler.Response;
import com.gmail.previati.edgardo.currencyconverter.data.Conversion;

/**
 * Created by Edgardo on 26/10/2014.
 */
public class GetExchangeQuote {

    private Context mContext;
    private Response mHandler;

    public GetExchangeQuote(final Context context, final Response handler) {
        if (context == null) throw new NullPointerException("Context cannot be null");
        mContext = context;

        if (handler == null) throw new NullPointerException("Handler cannot be null");
        mHandler = handler;
    }

    public final void execute(final Conversion conversion) {
        String url = String.format(
                Const.URL_CONVERSION,
                conversion.getCurrencyFrom(),
                conversion.getCurrencyTo());

        new RequestAsyncTask(mHandler).execute(String.format("%s%s", Const.URL_BASE, url));
    }
}
