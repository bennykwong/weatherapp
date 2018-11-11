package com.bkwong.weatherapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.weatherapp.R;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    private static List<WeatherResponse> dataSet = new ArrayList<>();

    public CustomAdapter(){
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {
        TextView textViewDate = recycleViewHolder.textViewDate;
        TextView textViewTemperature = recycleViewHolder.textViewTemperature;
        TextView textViewSummary = recycleViewHolder.textViewSummary;
        ImageView imageViewWeatherIcon = recycleViewHolder.imageViewWeatherIcon;

        if (recycleViewHolder instanceof RecycleViewHolder) {
            Date date = new Date(dataSet.get(i).getCurrently().getTime() * 1000L);
            SimpleDateFormat sdf=new SimpleDateFormat("EEE',' MM/dd");

            //concatenate max and min temperature
            String temperatureText = Double.toString(dataSet.get(i).getDaily().getData().get(0).getTemperatureMax()) + "/" +
                    Double.toString(dataSet.get(i).getDaily().getData().get(0).getTemperatureMin()) +  (char) 0x00B0 + "F";
            String summaryText = dataSet.get(i).getDaily().getData().get(0).getSummary();

            textViewDate.setText(sdf.format(date));
            textViewTemperature.setText(temperatureText);
            Log.d("benny", "icon:" + dataSet.get(i).getCurrently().getIcon());
            //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
            textViewSummary.setText(summaryText);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void addWeather(WeatherResponse weather) {
        dataSet.add(weather);
        //need to sort the response list because it is not always in order
        Collections.sort(dataSet, new Comparator<WeatherResponse>() {
            @Override
            public int compare(WeatherResponse r1, WeatherResponse r2) {
                return new Long(r2.getCurrently().getTime()).compareTo(new Long(r1.getCurrently().getTime()));
            }
        });
        notifyDataSetChanged();
    }
}
