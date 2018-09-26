package com.gmail.previati.edgardo.currencyconverter.asynctask.handler;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public interface Response {
    void onSuccess(final String string);

    void onFail();
}