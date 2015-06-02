package com.infusion.digimenu;

import android.app.IntentService;
import android.content.Intent;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkOpService extends IntentService {
    public static final String EXTRA_MENU_ITEM_ID = "com.infusion.digimenu.extra.MENU_ITEM_ID";
    private static final String HTTP_URI_LIKE = "https://www.digimenu.azuremobile.net/likeMenuItem";

    public NetworkOpService() {
        super("NetworkOpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_LIKE_MENU_ITEM.equals(action)) {
        final int itemId = intent.getIntExtra(EXTRA_MENU_ITEM_ID, -1);
        handleActionLikeMenuItem(itemId);
//            }
//        }
    }

    private void handleActionLikeMenuItem(int menuItemId) {
        if (menuItemId < 0) {
            // invalid menu item - guard
            return;
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(HTTP_URI_LIKE);

        List<NameValuePair> nameValuePair = new ArrayList<>(1);
        nameValuePair.add(new BasicNameValuePair("id", Integer.toString(menuItemId)));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            httpClient.execute(httpPost);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
