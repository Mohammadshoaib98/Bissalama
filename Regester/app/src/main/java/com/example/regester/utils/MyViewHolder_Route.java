package com.example.regester.constants;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class MyViewHolder_Route extends RecyclerView.ViewHolder {
    TextView agencyName,journeyDate,journeyTime,sourceCity,desCity;


    public MyViewHolder_Route(@NonNull View itemView) {
        super(itemView);
        agencyName=itemView.findViewById(R.id.tv_route_agencyName);
        sourceCity=itemView.findViewById(R.id.tv_routeSourceCountry);
        desCity =itemView.findViewById(R.id.tv_routeDestinationCountry);
        journeyTime=itemView.findViewById(R.id.tv_routeJourneyTime);
        journeyDate=itemView.findViewById(R.id.tv_routeJourneyDate);



    }

    public void setAgencyName(String agencyName) {
        this.agencyName.setText(agencyName);
    }


    public void setJourneyDate(String journeyDate) {
        this.journeyDate.setText(journeyDate);
    }


    public void setJourneyTime(String journeyTime) {
        this.journeyTime.setText(journeyTime);
    }


    public void setSourceCity(String sourceCity)     {
        this.sourceCity.setText(sourceCity);
    }


    public void setDesCity(String desCity) {
        this.desCity.setText(desCity);
    }


}
