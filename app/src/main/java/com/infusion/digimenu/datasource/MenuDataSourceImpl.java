package com.infusion.digimenu.datasource;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.infusion.digimenu.R;
import com.infusion.digimenu.model.Location;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ali on 2015-05-31.
 */
public class MenuDataSourceImpl extends MenuDataSource {
    private Context mContext;

    public MenuDataSourceImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void getMenu(Location location) {
        new Thread(new Runnable() {
            public void run() {
                com.infusion.digimenu.model.Menu result;

                try {
                    InputStream inputStream = mContext.getResources().openRawResource(R.raw.menu);
                    if (inputStream == null) {
                        // failure to open input stream - notify
                        throw new Exception("Unable to open local raw menu json file.");
                    }

                    final Gson gson = new Gson();
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    result = gson.fromJson(reader, com.infusion.digimenu.model.Menu.class);

                    // create an artificial delay
                    delaySafely(1500);

                    setChanged();
                    notifyObservers(result);
                } catch (final Exception e) {
                    Log.e(MenuDataSourceImpl.class.getName(), "Failed to retrieve menu.", e);
                }
            }
        }).start();
    }

    private void delaySafely(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
