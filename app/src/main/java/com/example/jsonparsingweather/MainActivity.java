package com.example.jsonparsingweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  new MyAsyncTask().execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
        new MyAsyncTask().execute();
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                // create a new url object and get the url's first object
               // url = new URL(urls[0]);
                url = new URL("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
                urlConnection = (HttpURLConnection) url.openConnection();

                // create an input stream
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

               // return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
                return result;
        }

        // result that comes from doinbackground is sent here
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  Log.e("JSON", s);


            try {

                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");

                Log.e("WEATHER", weatherInfo);

                JSONArray weatherArray = new JSONArray(weatherInfo);
                for (int i=0; i<weatherArray.length(); i++) {
                    JSONObject innerWeather = weatherArray.getJSONObject(i);

                    Log.e("id", innerWeather.getString("id"));
                    Log.e("main", innerWeather.getString("main"));
                    Log.e("description", innerWeather.getString("description"));
                    Log.e("icon", innerWeather.getString("icon"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
