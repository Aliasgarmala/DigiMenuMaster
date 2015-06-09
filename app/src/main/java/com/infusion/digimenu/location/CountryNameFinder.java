package com.infusion.digimenu.location;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CountryNameFinder {
    /**
     * Finds the country name of the location provided. This operation involves time-consuming network operations.
     * Consider calling this function on a separate thread to avoid blocking other threads
     *
     * @param latitude
     * @param longitude
     * @return Country Name if found. Null if not found or error
     */
    public static String getCountryNameByLocation(double latitude, double longitude) {
        JSONObject object = getLocationJsonFromGoogleAPI(latitude, longitude);
        return parseJsonForCountry(object);
    }

    private static JSONObject getLocationJsonFromGoogleAPI(double latitude, double longitude) {
        JSONObject jsonObject = new JSONObject();

        try {
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //We don't want to wait forever. Let's set some timeouts
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            try {
                jsonObject = new JSONObject(responseStrBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private static String parseJsonForCountry(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equalsIgnoreCase("OK")) {
                JSONArray results = object.getJSONArray("results");
                JSONArray address_components = results.getJSONObject(0).getJSONArray("address_components");
                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject component = address_components.getJSONObject(i);
                    String long_name = component.getString("long_name");
                    JSONArray types = component.getJSONArray("types");
                    String type = types.getString(0);
                    if(!TextUtils.isEmpty(long_name) && type.equalsIgnoreCase("country")){
                        return long_name;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
