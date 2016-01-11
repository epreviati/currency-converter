package com.gmail.previati.edgardo.currencyconverter.asynctask;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Edgardo on 21/10/2014.
 */
public class DefaultHttpClientSingleton {

    private static final DefaultHttpClient mDefaultHttpClientSingleton = new DefaultHttpClient();

    protected static final DefaultHttpClient instance() {
        return mDefaultHttpClientSingleton;
    }
}