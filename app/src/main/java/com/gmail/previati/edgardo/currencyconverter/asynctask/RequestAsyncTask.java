package com.gmail.previati.edgardo.currencyconverter.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.asynctask.handler.Response;

import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Edgardo on 21/10/2014.
 */
public final class RequestAsyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = RequestAsyncTask.class.getName();

    private Response mHandler;

    public RequestAsyncTask(final Response handler) {
        if (handler == null) throw new NullPointerException("Handler cannot be null");
        mHandler = handler;
    }

    @Override
    public final String doInBackground(final String... urls) {
        if (urls == null) return null;
        if (urls.length == 0) return null;

        return httpGet(urls[0]);
    }

    @Override
    protected void onPostExecute(final String result) {
        if (result == null) mHandler.onFail();
        else mHandler.onSuccess(result);
    }

    private final String httpGet(final String url) {
        String result = null;
        BufferedReader bufferedReader = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(
                    Const.HEADER_CONTENT_TYPE,
                    Const.HEADER_APPLICATION_JSON);
            httpGet.setHeader(
                    Const.HEADER_CONTENT_LENGTH,
                    Const.HEADER_DEFAULT_LENGTH);

            InputStream inputStream = DefaultHttpClientSingleton.instance()
                    .execute(httpGet)
                    .getEntity()
                    .getContent();

            if (inputStream == null) return null;

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) result += line;

        } catch (IOException e) {
            Log.wtf(TAG, e.getLocalizedMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                Log.wtf(TAG, e.getLocalizedMessage(), e);
            }
        }

        return result;
    }
}
