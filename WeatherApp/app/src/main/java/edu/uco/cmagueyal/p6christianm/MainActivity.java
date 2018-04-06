package edu.uco.cmagueyal.p6christianm;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static edu.uco.cmagueyal.p6christianm.R.id.newViewList;

/**
 * Created by DogTownDog on 10/9/2016.
 */

public class MainActivity extends Activity {

  //  private ListView listview;
    private TextView listTxtView;
    private EditText city;
    private Button mapButton;
    public static Double cityLatitude = 0.0;
    public static Double cityLongitude = 0.0;
    public static String markerData;
    private StringBuilder weatherBuilder = new StringBuilder("");
    public static String cityName;
    public String tNameCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listTxtView = (TextView) findViewById(newViewList);
        city = (EditText) findViewById(R.id.city);
        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setVisibility(View.INVISIBLE);

        final Button loadButton = (Button) findViewById(R.id.button1);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityString = city.getText().toString();
                tNameCity = cityString;
                new HttpGetTask().execute(cityString);
                mapButton.setVisibility(View.VISIBLE);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                Bundle b = new Bundle();
                b.putDouble("lat", cityLatitude);
                b.putDouble("lon", cityLongitude);
                b.putString("mark", markerData);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }

    private class HttpGetTask extends AsyncTask<String, Void, ArrayList<String>> {

        private static final String TAG = "HttpGetTask";

        // Construct the URL for the OpenWeatherMap query
        // Possible parameters are avaiable at OWM's forecast API page, at
        // http://openweathermap.org/API#forecast
        final String FORECAST_BASE_URL =
                "http://api.openweathermap.org/data/2.5/weather?";
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            InputStream in = null;
            HttpURLConnection httpUrlConnection = null;
            ArrayList<String> resultArray = null;
            try {
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("q", params[0]+",us") // city
                        .appendQueryParameter("units", "metric") // metric unit
                        .appendQueryParameter("mode", "json") // json format as result
                        .appendQueryParameter("APPID", "4a6ffd72b275fb4581f8e4f66d240b49")
                        .build();

                URL url = new URL(builtUri.toString());
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());
                String data = readStream(in);
                resultArray = JSONWeatherData.getData(data);

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException exception) {
                Log.e(TAG, "IOException");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                if (null != httpUrlConnection) {
                    httpUrlConnection.disconnect();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return resultArray;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result == null || result.size() == 0) {
                Toast.makeText(MainActivity.this,
                        "Invalid weather data. Possibly a wrong query",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if(!cityName.equals(tNameCity)){


                Toast.makeText(MainActivity.this,
                        "Invalid  City: Try another"
                        ,
                        Toast.LENGTH_LONG).show();
                return;
            }
            cityName = result.remove(0);
            mapButton.setText("Map of " + cityName);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    result
            );
            for(String i: result){
                weatherBuilder.append(i + "\n");
            }
            listTxtView.setText(weatherBuilder.toString());
            weatherBuilder.delete(0, weatherBuilder.length());
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }
    }
}
