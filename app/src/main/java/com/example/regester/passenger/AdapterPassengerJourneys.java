package com.example.regester.passenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;
import com.example.regester.models.Journey;

import java.util.ArrayList;

public class AdapterPassengerJourneys extends RecyclerView.Adapter<MyViewHolder_PassengerJourney> {
    public  ArrayList<Journey> arrayListJourneys;
    Context context;

    public AdapterPassengerJourneys(Context context, ArrayList<Journey> arrayListjourneys) {
        this.arrayListJourneys = arrayListjourneys;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder_PassengerJourney onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater=LayoutInflater.from(parent.getContext());
       View view=inflater.inflate(R.layout.row_passenger_journey,parent,false);
         return new MyViewHolder_PassengerJourney(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder_PassengerJourney holder, int position) {

        holder.agencyName.setText(arrayListJourneys.get(position).getAgencyName());
        holder.srcCity.setText(arrayListJourneys.get(position).getSourceCity());
        holder.desCity.setText(arrayListJourneys.get(position).getDestinationCity());
        holder.time.setText(arrayListJourneys.get(position).getJourneyTime());
        holder.date.setText(arrayListJourneys.get(position).getJourneyDate());

        holder.itemView.setOnClickListener(v -> {
           arrayListJourneys.get(position).getSeats();
           /* getBaseContext().startActivity(new Intent(
                    getBaseContext(), ActivityPassengerSeats.class)
                    .putExtra("agencyID", agencyID)
                    .putExtra("journeyID", journeyID));*/
          //  context.startActivity(new Intent(context, ActivityPassengerSeats.class).putCharSequenceArrayListExtra());
        });

    }

    @Override
    public int getItemCount() {
        return arrayListJourneys.size();
    }


}
