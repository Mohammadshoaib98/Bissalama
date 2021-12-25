package com.example.regester.constants;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;

import java.util.ArrayList;

public class AdapterPassJourneys extends RecyclerView.Adapter<viewHolder_PassJourneys> {
    private static final String TAG = "Field";
    public  ArrayList<Journey> journeysArrayList;
    Context context;

    public AdapterPassJourneys(Context context, ArrayList<Journey> journeysArrayList) {
        this.journeysArrayList = journeysArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder_PassJourneys onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater=LayoutInflater.from(parent.getContext());
       View view=inflater.inflate(R.layout.row_user_journeys,parent,false);
         return new viewHolder_PassJourneys(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder_PassJourneys holder, int position) {

        holder.agencyNamePassJourney.setText(journeysArrayList.get(position).getAgencyName());
        Log.d(TAG, journeysArrayList.get(position).getAgencyName());
        holder.sourceCountryPassJourney.setText(journeysArrayList.get(position).getCitySource());
        Log.d(TAG, journeysArrayList.get(position).getCitySource());
        holder.destinationCountryPassJourney.setText(journeysArrayList.get(position).getCityDestination());
        holder.journeyTimePassJourney.setText(journeysArrayList.get(position).getJourneyTime());
        holder.journeyDatePassJourney.setText(journeysArrayList.get(position).getJourneyDate());
        holder.seatNumberPassJourney.setText(journeysArrayList.get(position).getSeat());
    }

    @Override
    public int getItemCount() {
        return journeysArrayList.size();
    }


}
