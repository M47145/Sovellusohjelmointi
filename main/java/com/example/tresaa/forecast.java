package com.example.tresaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class forecast extends AppCompatActivity {
    private String cityName = null;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        cityName=getIntent().getStringExtra("CITY_NAME_KEY");

        TextView forecastCityNameTextView = findViewById(R.id.forecastCityNameTextView);
        forecastCityNameTextView.setText(cityName);

        queue = Volley.newRequestQueue(this);
        getForecastForCity();
    }

    public void getForecastForCity(){
        String url ="https://api.openweathermap.org/data/2.5/forecast?q=tampere&units=metric&appid=d97af94456e7d905f3ce277141789624";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,url, null,this::parseJsonAndUpdateUI,
                error->{

                });
        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(JSONObject s) {
        String weatherForeacastItemString;
        try {
            JSONArray forecastList = s.getJSONArray("list");
            for(int i=0;i<forecastList.length();i++){
                JSONObject weatherItem = forecastList.getJSONObject(i);
                weatherForeacastItemString = weatherItem.getJSONArray("weather").getJSONObject(0).getString("main");
                float temp = (float) weatherItem.getJSONObject("main").getDouble("temp");
                weatherForeacastItemString += " " + temp + "c";
                TextView weatherForecastList = findViewById(R.id.weatherForecastList);
                weatherForecastList.append(weatherForeacastItemString+"\n\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}