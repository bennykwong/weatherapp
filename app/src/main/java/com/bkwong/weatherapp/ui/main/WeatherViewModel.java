package com.bkwong.weatherapp.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<WeatherResponse> weatherInfo;

    public MutableLiveData<WeatherResponse> getWeatherInfo() {
        if (weatherInfo == null) {
            weatherInfo = new MutableLiveData<WeatherResponse>();
        }
        return weatherInfo;
    }

}
