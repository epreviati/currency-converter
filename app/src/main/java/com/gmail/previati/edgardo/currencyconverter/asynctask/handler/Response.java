package com.gmail.previati.edgardo.currencyconverter.asynctask.handler;

/**
 * Created by Edgardo on 12/11/2014.
 */
public interface Response {
    void onSuccess(final String string);

    void onFail();
}