package com.example.regester.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class MyViewHolder_Seats extends RecyclerView.ViewHolder {
//MyViewHolder_seat
    public void setSeatNumber(String seatNumber) {
        this.seatNumber.setText( seatNumber);
    }

    TextView seatNumber;

    public MyViewHolder_Seats(@NonNull View itemView) {
        super(itemView);
        seatNumber = itemView.findViewById(R.id.txt_seat);

    }
}
