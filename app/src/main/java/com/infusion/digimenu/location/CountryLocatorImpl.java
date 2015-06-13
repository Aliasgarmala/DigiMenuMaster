package com.infusion.digimenu.location;

import android.content.Context;
import android.location.LocationManager;

//TODO: (4) Implement LocationListener Interface
//  In the overridden  onLocationChanged() method, obtain the location and find the country name using the CountryNameFinder class
//  This can be time consuming task so make sure to spawn a thread for it.
//  Notify listeners with the country name.
//  Also handle the situation where country name couldn't be determined (notify listeners with COUNTRY_UNKNOWN)
public class CountryLocatorImpl extends CountryLocator {

    public static final String COUNTRY_UNKNOWN = "Unknown"; //Notify observers with this when country name couldn't be located
    private final Context mContext;
    private LocationManager mLocationManager;

    public CountryLocatorImpl(Context context) {
        this.mContext = context;

        //TODO: (2) Obtain a reference to the Location manager (Hint: getSystemService(Context.LOCATION_SERVICE) )
    }

    //Called by the client of this class to initiate finding the location
    public void locateCountry() {
        //Request Location updates from Location Manager
        //TODO: (3) Request location updates (GPS) from Location Manager
        //Hint: call requestSingleUpdate method with GPS Provider
    }

}
