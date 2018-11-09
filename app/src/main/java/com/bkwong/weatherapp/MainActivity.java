package com.bkwong.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bkwong.weatherapp.ui.main.MainFragment;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

//        ForecastApi.create(getString(R.string.dark_sky_api_key));
//
//        RequestBuilder weather = new RequestBuilder();
//
//        Request request = new Request();
//        request.setLat("32.00");
//        request.setLng("-81.00");
//        request.setTime("2017-11-12T12:30:00");
////        request.setTime("255589200");
//        request.setUnits(Request.Units.US);
//        request.setLanguage(Request.Language.ENGLISH);
//        request.addExcludeBlock(Request.Block.DAILY);
//
//
//        weather.getWeather(request, new Callback<WeatherResponse>() {
//            @Override
//            public void success(WeatherResponse weatherResponse, Response response) {
//                Log.d("benny", "response " + weatherResponse.toString());
//            }
//
//            @Override
//            public void failure(RetrofitError retrofitError) {
//                Log.d("benny", "Error while calling: " + retrofitError.getUrl());
//            }
//        });

    }
}
