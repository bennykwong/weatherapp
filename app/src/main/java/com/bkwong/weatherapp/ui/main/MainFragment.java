package com.bkwong.weatherapp.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bkwong.weatherapp.R;
import com.bkwong.weatherapp.adapter.CustomAdapter;
import com.bkwong.weatherapp.models.Place;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment {

    private String TAG = MainFragment.class.getSimpleName();

    private CustomAdapter mAdapter;
    private HashMap<String, Place> placeMap = new HashMap<>();
    private TextView textViewCity;
    private int currentSelectedItem;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        setHasOptionsMenu(true);

        //associate a set of lat/lon to a place
        setupPlaces();

        textViewCity = (TextView) view.findViewById(R.id.city);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CustomAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void setupPlaces() {
        placeMap.put(getResources().getString(R.string.city_irvine), new Place("33.659576", "-117.778881"));
        placeMap.put(getResources().getString(R.string.city_los_angeles), new Place("34.042992", "-118.243646"));
        placeMap.put(getResources().getString(R.string.city_new_york), new Place("40.721232", "-73.995404"));
        placeMap.put(getResources().getString(R.string.city_san_francisco), new Place("37.794866", "-122.401345"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //app startup state
        if(savedInstanceState == null) {
           textViewCity.setText(getResources().getString(R.string.city_weather, getResources().getString(R.string.city_irvine)));
           getWeatherData(placeMap.get(getResources().getString(R.string.city_irvine)));
           currentSelectedItem = R.id.city_irvine;
        }
    }

    private void getWeatherData(Place city) {
        //request weather information for the past 7 days
        DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-08:00'");
        for (int i=-1; i>=-7; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            createDarkSkyRequest(city, dateFormat.format(cal.getTime()));
        }
    }

    private void createDarkSkyRequest(Place city, String time) {
        ForecastApi.create(getString(R.string.dark_sky_api_key));
        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat(city.getLat());
        request.setLng(city.getLon());
        request.setTime(time);
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);

        //dont need the following blocks - improve latency
        request.addExcludeBlock(Request.Block.HOURLY);
        request.addExcludeBlock(Request.Block.MINUTELY);
        request.addExcludeBlock(Request.Block.ALERTS);
        request.addExcludeBlock(Request.Block.FLAGS);

        //start the actual network request
        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                mAdapter.addWeather(weatherResponse);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.e(TAG, "Error while calling: " + retrofitError.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.city_irvine:
                //prevent duplicated request if weather info is already present
                if (currentSelectedItem != id) {
                    mAdapter.clearData();
                    textViewCity.setText(getResources().getString(R.string.city_weather, getResources().getString(R.string.city_irvine)));
                    getWeatherData(placeMap.get(getResources().getString(R.string.city_irvine)));
                    currentSelectedItem = id;
                }
                return true;
            case R.id.city_los_angeles:
                if (currentSelectedItem != id) {
                    mAdapter.clearData();
                    textViewCity.setText(getResources().getString(R.string.city_weather, getResources().getString(R.string.city_los_angeles)));
                    getWeatherData(placeMap.get(getResources().getString(R.string.city_los_angeles)));
                    currentSelectedItem = id;
                }
                return true;
            case R.id.city_san_francisco:
                if (currentSelectedItem != id) {
                    mAdapter.clearData();
                    textViewCity.setText(getResources().getString(R.string.city_weather, getResources().getString(R.string.city_san_francisco)));
                    getWeatherData(placeMap.get(getResources().getString(R.string.city_san_francisco)));
                    currentSelectedItem = id;
                }
                return true;
            case R.id.city_new_york:
                if (currentSelectedItem != id) {
                    mAdapter.clearData();
                    textViewCity.setText(getResources().getString(R.string.city_weather, getResources().getString(R.string.city_new_york)));
                    getWeatherData(placeMap.get(getResources().getString(R.string.city_new_york)));
                    currentSelectedItem = id;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
