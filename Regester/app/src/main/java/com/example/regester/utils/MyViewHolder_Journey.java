package com.example.regester.constants;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.regester.R;


public class MyViewHolder_Journey extends RecyclerView.ViewHolder {


    TextView agencyName, journeyDate, journeyTime, sourceCountry, destinationCountry;
    Button delete, update;

    public MyViewHolder_Journey(@NonNull View itemView) {
        super(itemView);
        agencyName = itemView.findViewById(R.id.tv_agency_name);
        journeyDate = itemView.findViewById(R.id.txt_journey_date);
        journeyTime = itemView.findViewById(R.id.txt_journey_time);
        sourceCountry = itemView.findViewById(R.id.tv_source_city);
        destinationCountry = itemView.findViewById(R.id.tv_des_city);
        update = itemView.findViewById(R.id.button_update);
        delete = itemView.findViewById(R.id.btn_delete);


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

    public void setSourceCountry(String sourceCountry) { this.sourceCountry.setText(sourceCountry); }

    public void setDestinationCountry(String destinationCountry) { this.destinationCountry.setText(destinationCountry); }






}
