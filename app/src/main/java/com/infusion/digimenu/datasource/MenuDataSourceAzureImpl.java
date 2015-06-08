package com.infusion.digimenu.datasource;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.infusion.digimenu.model.Menu;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 2015-06-08.
 */
public class MenuDataSourceAzureImpl extends MenuDataSource {
    private static final String SERVICE_URI = "https://digimenu.azure-mobile.net/";
    private static final String APPLICATION_KEY = "DRUHVEpsiDexVdaeqmUspPbamWIyXq56";

    private final MobileServiceClient mClient;
    private final Gson mGson;

    public MenuDataSourceAzureImpl(Context context) throws MalformedURLException {
        mClient = new MobileServiceClient(SERVICE_URI, APPLICATION_KEY, context);
        mGson = new Gson();
    }

    @Override
    public void getMenu(String country) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("country", country));

        mClient.invokeApi("menu", "GET", parameters, new ApiJsonOperationCallback() {
            @Override
            public void onCompleted(JsonElement jsonObject, Exception exception, ServiceFilterResponse response) {
                if (exception != null) {
                    // service call failed - no point in proceeding
                    Log.e(MenuDataSourceAzureImpl.class.getName(), "An error occurred while getting menu.", exception);
                    return;
                }

                if (jsonObject == null) {
                    // service returned invalid payload - guard
                    Log.d(MenuDataSourceAzureImpl.class.getName(), "Azure menu data source returned null response.");
                    return;
                }

                // deserialize response to menu type
                Menu result = mGson.fromJson(jsonObject, Menu.class);

                setChanged();
                notifyObservers(result);
            }
        });
    }
}