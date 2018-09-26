package com.gmail.previati.edgardo.currencyconverter.action;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.asynctask.RequestAsyncTask;
import com.gmail.previati.edgardo.currencyconverter.asynctask.handler.Response;
import com.gmail.previati.edgardo.currencyconverter.data.Conversion;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class GetExchangeQuote {

    private Response mHandler;

    public GetExchangeQuote(final Response handler) {
        if (handler == null) throw new NullPointerException("Handler cannot be null");
        mHandler = handler;
    }

    public final void execute(final Conversion conversion) {
        String url = Const.URL_CONVERSION
                .replace("{FROM}", conversion.getCurrencyFrom())
                .replace("{TO}", conversion.getCurrencyTo());

        new RequestAsyncTask(mHandler).execute(Const.URL_BASE + url);
    }
}
