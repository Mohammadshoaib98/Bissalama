package com.example.regester.passenger;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class MyViewHolder_PassengerJourney extends RecyclerView.ViewHolder {

    TextView agencyName, date, time, srcCity, desCity;// seatNumber;
    View itemView;

    public MyViewHolder_PassengerJourney(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        agencyName = (TextView) itemView.findViewById(R.id.tv_clerk_agency_name);
        srcCity = (TextView) itemView.findViewById(R.id.tv_source_city_passenger_journey);
        desCity = (TextView) itemView.findViewById(R.id.tv_destination_city_passenger_journey);
        time = (TextView) itemView.findViewById(R.id.tv_journey_time_passenger_journey);
        date = (TextView) itemView.findViewById(R.id.tv_journey_date_passenger_journey);
        //seatNumber =(TextView)itemView.findViewById(R.id.tv_seat_number);

    }
}
