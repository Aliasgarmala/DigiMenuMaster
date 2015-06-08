package com.infusion.digimenu;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class NetworkOpService extends IntentService {
    public static final String EXTRA_MENU_ITEM_ID = "com.infusion.digimenu.extra.MENU_ITEM_ID";

    private static final String SERVICE_URI = "https://digimenu.azure-mobile.net/";
    private static final String APPLICATION_KEY = "DRUHVEpsiDexVdaeqmUspPbamWIyXq56";

    private MobileServiceClient mClient;

    public NetworkOpService() {
        super("NetworkOpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int itemId = intent.getIntExtra(EXTRA_MENU_ITEM_ID, -1);
        postLike(itemId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            mClient = new MobileServiceClient(SERVICE_URI, APPLICATION_KEY, this);
        } catch (MalformedURLException e) {
            Log.e(NetworkOpService.class.getName(), "Failed to instantiate service client.", e);
        }
    }

    private void postLike(final int menuItemId) {
        if (mClient == null) {
            // invalid reference of client - guard
            Log.e(NetworkOpService.class.getName(), String.format("Failed to send like for menu item %d. Invalid null reference of service client.", menuItemId));
            return;
        }

        if (menuItemId < 0) {
            // invalid menu item - guard
            return;
        }

        List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("id", Integer.toString(menuItemId)));

        mClient.invokeApi("like", "POST", parameters, new ApiJsonOperationCallback() {
            @Override
            public void onCompleted(JsonElement jsonObject, Exception exception, ServiceFilterResponse response) {
                if (exception != null) {
                    // failure
                    Log.e(NetworkOpService.class.getName(), String.format("Failed to send like for menu item %d.", menuItemId), exception);
                }
            }
        });
    }
}