package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar bar=findViewById(R.id.determinateBar);
        bar.setVisibility(View.VISIBLE);

        ForecastQuery req= new ForecastQuery();
        req.execute("http://torunski.ca/CST2335_XML.xml");

    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}