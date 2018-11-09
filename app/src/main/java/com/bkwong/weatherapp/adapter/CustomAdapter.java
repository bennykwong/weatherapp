package com.bkwong.weatherapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bkwong.weatherapp.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    private static ArrayList<Object> dataSet;

    public CustomAdapter(){
        dataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);

        return new RecycleViewHolder(view, dataSet);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
