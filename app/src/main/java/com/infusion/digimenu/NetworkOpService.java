package com.infusion.digimenu;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkOpService extends IntentService {
    public static final String EXTRA_MENU_ITEM_ID = "com.infusion.digimenu.extra.MENU_ITEM_ID";
    private static final String API_METHOD_POST_LIKE = "api/like?id=";

    private final HttpClient mHttpClient;

    public NetworkOpService() {
        super("NetworkOpService");
        mHttpClient = new DefaultHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int menuItemId = intent.getIntExtra(EXTRA_MENU_ITEM_ID, -1);
        postLike(menuItemId);
    }

    private void postLike(final int id) {
        if (id < 0) {
            // invalid menu item - guard
            return;
        }

        HttpUriRequest request = new HttpPost(BuildConfig.MOBILE_SERVICE_URI + API_METHOD_POST_LIKE + id);
        request.addHeader("X-ZUMO-APPLICATION", BuildConfig.MOBILE_SERVICE_API_KEY);

        try {
            mHttpClient.execute(request);
        } catch (Exception e) {
            Log.e(NetworkOpService.class.getName(), "Failed to send like for menu item " + id, e);
        }
    }
}