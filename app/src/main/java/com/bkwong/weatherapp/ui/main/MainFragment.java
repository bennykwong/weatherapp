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
import android.view.View;
import android.view.ViewGroup;

import com.bkwong.weatherapp.R;
import com.bkwong.weatherapp.adapter.CustomAdapter;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainFragment extends Fragment {

    private String TAG = MainFragment.class.getSimpleName();

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CustomAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState == null) {
            //request weather information for the past 7days/week
            DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-08:00'");
            for (int i=-1; i>=-7; i--) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, i);
                createDarkSkyRequest(dateFormat.format(cal.getTime()));
            }
        }

    }

    private void createDarkSkyRequest(String time) {
        ForecastApi.create(getString(R.string.dark_sky_api_key));
        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat("33.659576");
        request.setLng("-117.778881");
        request.setTime(time);
        request.setUnits(Request.Units.US);
        request.setLanguage(Request.Language.ENGLISH);
        request.addExcludeBlock(Request.Block.HOURLY);
        request.addExcludeBlock(Request.Block.MINUTELY);
        request.addExcludeBlock(Request.Block.ALERTS);
        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                mAdapter.addWeather(weatherResponse);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, "Error while calling: " + retrofitError.toString());
            }
        });
    }

}
