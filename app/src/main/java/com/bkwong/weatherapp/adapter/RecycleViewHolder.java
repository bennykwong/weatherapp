package com.bkwong.weatherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkwong.weatherapp.R;

public class RecycleViewHolder extends RecyclerView.ViewHolder {

    TextView textViewDate;
    TextView textViewTemperature;
    TextView textViewSummary;
    ImageView imageViewWeatherIcon;

    public RecycleViewHolder(View itemView) {
        super(itemView);
        this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        this.textViewTemperature = (TextView) itemView.findViewById(R.id.textViewTemperature);
        this.textViewSummary = (TextView) itemView.findViewById(R.id.textViewSummary);
        this.imageViewWeatherIcon = (ImageView) itemView.findViewById(R.id.imageViewWeather);
    }
}
