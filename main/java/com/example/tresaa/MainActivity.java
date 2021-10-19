package com.example.tresaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private RequestQueue queue;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.language1:
                Toast.makeText(this,"Suomi valittu",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.language2:
               Toast.makeText(this,"English selected",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.myoptions_menu, menu);
        return true;
    }

    public void getWeatherData(View view) {
        String url ="https://api.openweathermap.org/data/2.5/weather?q=Tampere&units=metric&appid=d97af94456e7d905f3ce277141789624";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, this::parseJsonAndUpdateUI,
        error->{

        });
        queue.add(stringRequest);
    }


    private void parseJsonAndUpdateUI(String response) {
        try {

            JSONObject rootObject = new JSONObject( response );
            float temperature = (float) rootObject.getJSONObject("main").getDouble("temp");
            float windSpeed = (float) rootObject.getJSONObject("wind").getDouble("speed");
            String description = rootObject.getJSONArray("weather").getJSONObject(0).getString("main");

            TextView weatherDescTextView = findViewById(R.id.weatherDescTextView);
            weatherDescTextView.setText(description);

            TextView tempTextView = findViewById(R.id.tempTextView);
            tempTextView.setText(temperature+"C");

            TextView  windSpeedTextView= findViewById(R.id.windSpeedTextView);
            windSpeedTextView.setText(windSpeed+"m/s");

            ImageView descImageView = findViewById(R.id.descImageView);
            switch(description) {

                case "Clear":
                    descImageView.setImageResource(R.drawable.sunny);
                    break;
                case "Snow":
                    descImageView.setImageResource(R.drawable.snowy);
                    break;
                case "Rain":
                    descImageView.setImageResource(R.drawable.rainy);
                    break;
                case "Drizzle":
                    descImageView.setImageResource(R.drawable.drizzle);
                    break;
                case "Thunderstorm":
                    descImageView.setImageResource(R.drawable.thunder);
                    break;
                case "Clouds":
                    descImageView.setImageResource(R.drawable.cloudy);
                    break;
                default:
                    descImageView.setImageResource(R.drawable.sunny);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openForecast(View view){
        Intent openForecast = new Intent(this,forecast.class);
        openForecast.putExtra("CITY_NAME_KEY","Tampere");
        startActivity(openForecast);
    }
}