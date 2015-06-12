package com.infusion.digimenu;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class LikeMenuItemService extends IntentService {

    public static final String EXTRA_MENU_ITEM_ID = "com.infusion.digimenu.extra.MENU_ITEM_ID";
    private static final String API_METHOD_POST_LIKE = "api/like?id=";

    private final HttpClient mHttpClient;

    public LikeMenuItemService() {
        super("LikeMenuItemService");

        mHttpClient = new DefaultHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // =======================================
        // LAB 3
        // =======================================
        // 1.   Retrieve the menu item id from the intent extras
        // 2.   Create an HttpPost object with target url: BuildConfig.MOBILE_SERVICE_URI + API_METHOD_POST_LIKE + menuItemId
        //          i.e. https://infusiondiner.azure-mobile.net/api/like?id=1
        // 3.   Use the HttpClient (i.e. mHttpClient) to execute the HttpPost operation
        //
        // *** HINT: take a look at MenuDataSourceHttpImpl.getMenu method
    }
}