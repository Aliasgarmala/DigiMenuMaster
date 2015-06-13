package com.infusion.digimenu.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class CountryLocatorImpl extends CountryLocator implements LocationListener {

    public static final String COUNTRY_UNKNOWN = "Unknown";
    private final Context mContext;
    private LocationManager mLocationManager;

    public CountryLocatorImpl(Context context) {
        this.mContext = context;

        //Get LocationManager instance
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void locateCountry() {
        //If providers not found, notify the observers. No need for registering for location updates
        if (!(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            setChanged();
            notifyObservers(COUNTRY_UNKNOWN);
            return;
        }
        //Register for receiving location updates
        mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
    }

    private void findCountryByLocation(final Location location) {

        //We are going to query the Google API over wi-fi for this. This could be time consuming.
        //So, let's spin up a thread for this.
        new Thread(new Runnable() {
            @Override
            public void run() {
                String countryName = CountryNameFinder.getCountryNameByLocation(location.getLatitude(), location.getLongitude());
                setChanged();
                notifyObservers(null == countryName ? COUNTRY_UNKNOWN : countryName);
            }
        }).start();

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(this.getClass().getName(), "onLocationChanged");

        //Stop Listening to Location Updates
        mLocationManager.removeUpdates(this);

        //Find the country name for the location received
        findCountryByLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(this.getClass().getName(), "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(this.getClass().getName(), "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(this.getClass().getName(), "onProviderDisabled");
    }
}
