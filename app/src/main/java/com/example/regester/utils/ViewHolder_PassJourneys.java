package com.example.regester.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class ViewHolder_PassJourneys extends RecyclerView.ViewHolder {

    TextView agencyNamePassJourney, journeyDatePassJourney, journeyTimePassJourney, sourceCountryPassJourney, destinationCountryPassJourney,seatNumberPassJourney;

    public ViewHolder_PassJourneys(@NonNull View itemView) {
        super(itemView);

        agencyNamePassJourney = (TextView) itemView.findViewById(R.id.tv_clerk_agency_name);
        sourceCountryPassJourney = (TextView) itemView.findViewById(R.id.tv_source_city_pass_journey);
        destinationCountryPassJourney = (TextView) itemView.findViewById(R.id.tv_destination_city_pass_journey);
        journeyTimePassJourney = (TextView) itemView.findViewById(R.id.tv_journey_time_pass_journey);
        journeyDatePassJourney = (TextView) itemView.findViewById(R.id.tv_journey_date_pass_journey);
        seatNumberPassJourney=(TextView)itemView.findViewById(R.id.tv_seat_number_pass_journey);

    }
}
