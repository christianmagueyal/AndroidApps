package edu.uco.cmagueyal.p6christianm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DogTownDog on 10/9/2016.
 */

public class JSONWeatherData {

    private static final String TAG = "JSONWeatherData";

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p/>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */



    public static ArrayList<String> getData(String forecastJsonStr)
            throws JSONException {

        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        try {
            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            // Each day's forecast info is an element of the "list" array.
            String cityName = forecastJson.getString("name"); // city name
            ////////
            MainActivity.cityName=cityName;

            ////////

            JSONObject cityCoord = forecastJson.getJSONObject("coord"); // coordinate
            double cityLat = cityCoord.getDouble("lat"); //latitude
            double cityLon = cityCoord.getDouble("lon"); // longitude

            MainActivity.cityLatitude = cityLat;
            MainActivity.cityLongitude = cityLon;

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            ArrayList<String> result = new ArrayList<>();
            result.add(cityName + "(" + cityLon + ", " + cityLat + ")");
                JSONObject windForecast = forecastJson.getJSONObject("wind");
              double windSpeed = windForecast.getDouble("speed");
            JSONArray weatherArray = forecastJson.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);

            String description = weather.getString("description");

                // Temperatures are in a child object called "temp".
                JSONObject mainObject = forecastJson.getJSONObject("main");
                double temp = mainObject.getDouble("temp"); // max temperature
                MainActivity.markerData = cityName + " temp: " + temp + "\u2103";

                result.add(

                        " Temperature(\u2103) = " + temp +
                        "\n\n Description: " + description +
                        "\n\n Wind Speed(ft/s) = " + windSpeed );

            return result;

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }
}


