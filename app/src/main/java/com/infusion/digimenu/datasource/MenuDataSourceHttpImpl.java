package com.infusion.digimenu.datasource;

import android.util.Log;

import com.google.gson.Gson;
import com.infusion.digimenu.BuildConfig;
import com.infusion.digimenu.model.Menu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by ali on 2015-06-08.
 */
public class MenuDataSourceHttpImpl extends MenuDataSource {
    private static final String API_METHOD_GET_MENU = "api/menu?country=";

    private final HttpClient mHttpClient;
    private final Gson mGson;

    public MenuDataSourceHttpImpl() {
        mHttpClient = new DefaultHttpClient();
        mGson = new Gson();
    }

    @Override
    public Thread getMenuAsync(final String country) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getMenu(country);
            }
        };

        Thread result = new Thread(runnable);
        result.start();

        return result;
    }

    private void getMenu(final String country) {
        // create the HTTP GET request object
        // add Microsoft Azure Mobile Service authentication header value
        HttpUriRequest request = new HttpGet(BuildConfig.MOBILE_SERVICE_URI + API_METHOD_GET_MENU + URLEncoder.encode(country));
        request.addHeader("X-ZUMO-APPLICATION", BuildConfig.MOBILE_SERVICE_API_KEY);

        try {
            // execute the web request
            HttpResponse response = mHttpClient.execute(request);

            // deserialize the menu response
            String payload = processResponse(response);
            Menu result = mGson.fromJson(payload, Menu.class);

            // notify the listeners of response
            setChanged();
            notifyObservers(result);
        } catch (Exception e) {
            Log.e(MenuDataSourceHttpImpl.class.getName(), "Failed to retrieve menu from web service for country " + country, e);
        }
    }

    private String processResponse(HttpResponse response) throws IOException, NullPointerException {
        HttpEntity entity = response.getEntity();
        String result = "";
        String line;

        // flatten out the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        while ((line = reader.readLine()) != null) {
            result += line;
        }

        // close the reader - return
        reader.close();
        return result;
    }
}